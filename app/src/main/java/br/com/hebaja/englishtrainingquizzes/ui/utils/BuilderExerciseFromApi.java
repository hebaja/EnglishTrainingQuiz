package br.com.hebaja.englishtrainingquizzes.ui.utils;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.async.SaveFileChecksumTask;
import br.com.hebaja.englishtrainingquizzes.async.SearchFileChecksumTask;
import br.com.hebaja.englishtrainingquizzes.daos.FileChecksumDao;
import br.com.hebaja.englishtrainingquizzes.database.EtqDatabase;
import br.com.hebaja.englishtrainingquizzes.model.FileChecksum;
import br.com.hebaja.englishtrainingquizzes.model.Task;
import br.com.hebaja.englishtrainingquizzes.retrofit.BaseRetrofit;
import br.com.hebaja.englishtrainingquizzes.retrofit.service.ChecksumService;
import br.com.hebaja.englishtrainingquizzes.retrofit.service.JsonArrayExerciseService;
import br.com.hebaja.englishtrainingquizzes.ui.viewmodel.TasksViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class BuilderExerciseFromApi {

    public static final String DEFAULT_CONNECTION_ERROR = "It wasn't possible to load exercise. Please try again.";
    public static final String CUSTOM_CONNECTION_ERROR = "It wasn't possible to load exercises. Please check your internet connection.";

    @NotNull
    private final View view;
    private FileChecksumDao dao;
    private final String fileName;
    private final FragmentActivity activity;
    private final int chosenLevel;
    private final int chosenSubject;
    private final String subjectPrompt;
    private final LifecycleOwner viewLifecycleOwner;
    private BuilderQuizFragmentViews builderQuizFragmentViews;
    private JsonArray storedJsonArrayExercise;
    private ProgressBar progressBar;
    private TasksViewModel tasksViewModel;

    public BuilderExerciseFromApi(@NotNull View view,
                                  String fileName,
                                  FragmentActivity activity,
                                  int chosenLevel,
                                  int chosenSubject,
                                  String subjectPrompt,
                                  LifecycleOwner viewLifecycleOwner) {
        this.view = view;
        this.fileName = fileName;
        this.activity = activity;
        this.chosenLevel = chosenLevel;
        this.chosenSubject = chosenSubject;
        this.subjectPrompt = subjectPrompt;
        this.viewLifecycleOwner = viewLifecycleOwner;
    }

    public void fetchExerciseFromApi() {
        progressBar = view.findViewById(R.id.quiz_progress_bar);
        builderQuizFragmentViews = new BuilderQuizFragmentViews(
                view,
                activity,
                viewLifecycleOwner,
                subjectPrompt,
                fileName,
                chosenLevel);
        builderQuizFragmentViews.initializeViews();
        builderQuizFragmentViews.hideViews();

        dao = EtqDatabase.getInstance(activity).getFileChecksumDao();

        tasksViewModel = new ViewModelProvider(activity).get(TasksViewModel.class);
        tasksViewModel.get().observe(viewLifecycleOwner, tasks -> {

            if(tasks.isEmpty()) {
                progressBar.setVisibility(View.VISIBLE);
                new SearchFileChecksumTask(dao, fileName, fileChecksum -> {
                    if (fileChecksum == null) {
                        requestExerciseFromServerAndBuildQuiz(builderQuizFragmentViews);
                    } else {
                        fetchExercise(builderQuizFragmentViews);
                    }
                }).execute();

            } else {

                configureBuilder(builderQuizFragmentViews, tasks);
            }
        });
    }

    private void configureErrorSnackbar(BuilderQuizFragmentViews builderQuizFragmentViews, @NonNull View view, BuilderExerciseFromApi.RetryListener retryListener) {
        progressBar.setVisibility(View.GONE);
        final BaseTransientBottomBar.Behavior behavior = new BaseTransientBottomBar.Behavior();
        behavior.setSwipeDirection(BaseTransientBottomBar.Behavior.SWIPE_DIRECTION_ANY);

        builderQuizFragmentViews.setOnlyBackToMainMenuButton();
        final Snackbar snackbar = Snackbar.make(
                view.findViewById(R.id.quiz_fragment_coordinator_layout),
                CUSTOM_CONNECTION_ERROR,
                Snackbar.LENGTH_INDEFINITE);
        snackbar.setBehavior(behavior);
        snackbar.setAction("Retry", retryListener);
        snackbar.setActionTextColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        snackbar.show();
    }

    private void requestExerciseFromServerAndBuildQuiz(BuilderQuizFragmentViews builderQuizFragmentViews) {

        final JsonArrayExerciseService service = new BaseRetrofit().getJsonArrayExerciseService();
        final Call<JsonArray> call = service.getJsonArrayExercise(fileName);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.isSuccessful()) {
                    final JsonArray jsonArrayExercise = response.body();
                    doOnJsonArrayResponse(jsonArrayExercise, builderQuizFragmentViews);
                } else {
                    configureErrorSnackbar(builderQuizFragmentViews, view, new BuilderExerciseFromApi.RetryJsonArrayExerciseListener(call, builderQuizFragmentViews));
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<JsonArray> call, Throwable t) {
                configureErrorSnackbar(builderQuizFragmentViews, view, new BuilderExerciseFromApi.RetryJsonArrayExerciseListener(call, builderQuizFragmentViews));
            }
        });
    }

    private void fetchExercise(BuilderQuizFragmentViews builderQuizFragmentViews) {
        convertStringToJsonArray();
        final List<Task> finalTasks = convertJsonArrayToList(storedJsonArrayExercise);
        tasksViewModel.set(finalTasks);
        configureBuilder(builderQuizFragmentViews, finalTasks);
    }

    private void doOnJsonArrayResponse(JsonArray jsonArrayExercise, BuilderQuizFragmentViews builderQuizFragmentViews) {
        if (jsonArrayExercise != null) {
                try (FileOutputStream fileOutputStream = activity.openFileOutput(fileName, Context.MODE_PRIVATE)) {
                    fileOutputStream.write(jsonArrayExercise.toString().getBytes());
                    final ChecksumService service = new BaseRetrofit().getChecksumService();
                    final Call<FileChecksum> call = service.getFileChecksum(fileName);
                    call.enqueue(new Callback<FileChecksum>() {
                        @Override
                        @EverythingIsNonNull
                        public void onResponse(Call<FileChecksum> call, Response<FileChecksum> response) {
                            if(response.isSuccessful()) {
                                final FileChecksum fileChecksum = response.body();
                                if(fileChecksum != null) {
                                    new SaveFileChecksumTask(fileChecksum, dao).execute();
                                }
                            }
                        }

                        @Override
                        @EverythingIsNonNull
                        public void onFailure(Call<FileChecksum> call, Throwable t) {

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            fetchExercise(builderQuizFragmentViews);
        }
    }

    private void convertStringToJsonArray() {
        try {
            InputStreamReader inputStreamReader = configureReader();
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                populateStringBuilder(stringBuilder, reader);
            } catch (IOException e) {
                e.printStackTrace();
                Snackbar.make(view.findViewById(R.id.quiz_fragment_coordinator_layout), DEFAULT_CONNECTION_ERROR, Snackbar.LENGTH_LONG).show();
            } finally {
                String storedFile = stringBuilder.toString();
                parseStringToJsonArray(storedFile);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Snackbar.make(view.findViewById(R.id.quiz_fragment_coordinator_layout), DEFAULT_CONNECTION_ERROR, Snackbar.LENGTH_LONG).show();
        }
    }

    private void configureBuilder(BuilderQuizFragmentViews builderQuizFragmentViews, List<Task> finalTasks) {
        builderQuizFragmentViews.showViews();
        builderQuizFragmentViews.setButtons(chosenLevel, chosenSubject);
        builderQuizFragmentViews.configureTasks(finalTasks);
        builderQuizFragmentViews.updateTaskViews();
        progressBar.setVisibility(View.GONE);
    }

    private List<Task> convertJsonArrayToList(JsonArray jsonExercise) {
        List<Task> fullTasksLists = new ArrayList<>();
        if (jsonExercise != null) {
            for (JsonElement jsonElement : jsonExercise) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                final Task task = new Gson().fromJson(jsonObject, Task.class);
                fullTasksLists.add(task);
            }
        }
        Collections.shuffle(fullTasksLists);
        return fullTasksLists.subList(0, 10);
    }

    private void parseStringToJsonArray(String storedFile) {
        final JsonParser parser = new JsonParser();
        final JsonElement parsedElement = parser.parse(storedFile);
        storedJsonArrayExercise = parsedElement.getAsJsonArray();
    }

    @NotNull
    private InputStreamReader configureReader() throws FileNotFoundException {
        FileInputStream fileInputStream = activity.openFileInput(fileName);
        return new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
    }

    private void populateStringBuilder(StringBuilder stringBuilder, BufferedReader reader) throws IOException {
        String line = reader.readLine();
        while (line != null) {
            stringBuilder.append(line).append("\n");
            line = reader.readLine();
        }
    }

    abstract static class RetryListener implements View.OnClickListener {}

    class RetryJsonArrayExerciseListener extends RetryListener {

        private final Call<JsonArray> receivedCall;
        private final BuilderQuizFragmentViews builderQuizFragmentViews;

        public RetryJsonArrayExerciseListener(Call<JsonArray> call, BuilderQuizFragmentViews builderQuizFragmentViews) {
            this.receivedCall = call;
            this.builderQuizFragmentViews = builderQuizFragmentViews;
        }

        @Override
        public void onClick(View v) {
            progressBar.setVisibility(View.VISIBLE);
            receivedCall.clone().enqueue(new Callback<JsonArray>() {
                @Override
                @EverythingIsNonNull
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    if (response.isSuccessful()) {
                        final JsonArray jsonArrayExercise = response.body();
                        doOnJsonArrayResponse(jsonArrayExercise, builderQuizFragmentViews);
                    } else {
                        configureErrorSnackbar(builderQuizFragmentViews, view, new RetryJsonArrayExerciseListener(call, builderQuizFragmentViews));
                    }
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                @EverythingIsNonNull
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    configureErrorSnackbar(builderQuizFragmentViews, view, new RetryJsonArrayExerciseListener(call, builderQuizFragmentViews));
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }
}
