package br.com.hebaja.englishtrainingquizzes.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.async.GetExercisesTask;
import br.com.hebaja.englishtrainingquizzes.async.RemoveExercisesTask;
import br.com.hebaja.englishtrainingquizzes.daos.ExerciseDao;
import br.com.hebaja.englishtrainingquizzes.database.EtqDatabase;
import br.com.hebaja.englishtrainingquizzes.model.Average;
import br.com.hebaja.englishtrainingquizzes.retrofit.BaseRetrofit;
import br.com.hebaja.englishtrainingquizzes.retrofit.service.AveragesService;
import br.com.hebaja.englishtrainingquizzes.ui.recyclerview.adapter.AveragesListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

import static br.com.hebaja.englishtrainingquizzes.utils.Constants.SHARED_PREFERENCES_KEY;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.UID_RECEIVED;
import static br.com.hebaja.englishtrainingquizzes.utils.Constants.USERNAME_RECEIVED;

public class BuilderAverages {

    public static final String AVERAGES_LOAD_ERROR_MESSAGE = "Could not load averages. Check your internet connection.";
    public static final String EXERCISES_SAVE_ERROR_MESSAGE = "It was not possible to update recent exercises.";
    public static final String EXERCISES_UPDATE_SUCCESS_MESSAGE = "Recent exercises were successfully updated.";
    public static final String RETRY_ACTION_LABEL = "Retry";

    private final Context context;
    private final FragmentActivity activity;

    public BuilderAverages(FragmentActivity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    public void build(TextView usernameTextView, RecyclerView averagesListRecyclerView, ProgressBar progressBar, View view) {
        AveragesService service = new BaseRetrofit().getAveragesService();
        final ExerciseDao dao = EtqDatabase.getInstance(context).getExercisesDao();
        GoogleSignInAccount currentGoogleAccount = GoogleSignIn.getLastSignedInAccount(context);
        Profile currentFacebookAccount = Profile.getCurrentProfile();

        if (currentGoogleAccount != null) {
            String uid = currentGoogleAccount.getId();
            usernameTextView.setText(currentGoogleAccount.getGivenName());
            getExercisesFromDatabase(averagesListRecyclerView, progressBar, service, dao, uid, view);
        } else if (currentFacebookAccount != null) {
            String uid = currentFacebookAccount.getId();
            usernameTextView.setText(currentFacebookAccount.getFirstName());
            getExercisesFromDatabase(averagesListRecyclerView, progressBar, service, dao, uid, view);
        } else {
            final SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
            final String username = preferences.getString(USERNAME_RECEIVED, null);
            final String uid = preferences.getString(UID_RECEIVED, null);
            usernameTextView.setText(username);
            getExercisesFromDatabase(averagesListRecyclerView, progressBar, service, dao, uid, view);
        }
    }

    private void getExercisesFromDatabase(RecyclerView averagesList, ProgressBar progressBar, AveragesService service, ExerciseDao dao, String uid, View view) {
        progressBar.setVisibility(View.VISIBLE);
        new GetExercisesTask(dao, uid, exercises -> {
            if (exercises.isEmpty()) {
                progressBar.setVisibility(View.GONE);
                getAveragesFromServer(averagesList, service.getAveragesByUid(uid), progressBar, view);
            } else {
                final Call<Void> call = new BaseRetrofit().getSaveExerciseService().register(exercises);
                call.enqueue(new Callback<Void>() {
                    @Override
                    @EverythingIsNonNull
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200) {
                            Snackbar.make(view, EXERCISES_UPDATE_SUCCESS_MESSAGE, Snackbar.LENGTH_LONG).show();
                            new RemoveExercisesTask(dao, uid).execute();
                        } else if (response.code() == 400) {
                            configureErrorSnackbar(
                                    view,
                                    activity,
                                    EXERCISES_SAVE_ERROR_MESSAGE,
                                    new BuilderAverages.RegisterUnsavedExercises(call, progressBar, view, dao, uid, averagesList));
                        }
                        progressBar.setVisibility(View.GONE);
                        getAveragesFromServer(averagesList, service.getAveragesByUid(uid), progressBar, view);
                    }

                    @Override
                    @EverythingIsNonNull
                    public void onFailure(Call<Void> call, Throwable t) {
                        configureErrorSnackbar(
                                view,
                                activity,
                                EXERCISES_SAVE_ERROR_MESSAGE,
                                new BuilderAverages.RegisterUnsavedExercises(call, progressBar, view, dao, uid, averagesList));

                        progressBar.setVisibility(View.GONE);
                        getAveragesFromServer(averagesList, service.getAveragesByUid(uid), progressBar, view);
                    }
                });
            }
        }).execute();
    }

    private void getAveragesFromServer(RecyclerView averagesList, Call<List<Average>> call, ProgressBar progressBar, View view) {
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<List<Average>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<List<Average>> call, Response<List<Average>> response) {
                progressBar.setVisibility(View.GONE);
                configureRecyclerView(response, averagesList, view);
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<List<Average>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                configureErrorSnackbar(view,
                        activity,
                        AVERAGES_LOAD_ERROR_MESSAGE,
                        new BuilderAverages.AveragesRetryListener(call, activity, view, progressBar, averagesList));
            }
        });
    }

    private void configureRecyclerView(Response<List<Average>> response, RecyclerView averagesList, View view) {
        if (response.isSuccessful()) {
            List<Average> averages = response.body();
            if (averages != null) {
                averagesList.setAdapter(new AveragesListAdapter(averages, context));
            }
        } else {
            Snackbar.make(view, AVERAGES_LOAD_ERROR_MESSAGE, Snackbar.LENGTH_LONG).show();
        }
    }

    private void configureErrorSnackbar(View view,
                                        FragmentActivity activity,
                                        String message,
                                        RetryListener listener) {
        final BaseTransientBottomBar.Behavior behavior = new BaseTransientBottomBar.Behavior();
        behavior.setSwipeDirection(BaseTransientBottomBar.Behavior.SWIPE_DIRECTION_ANY);
        Snackbar snackbar = Snackbar.make(
                view.findViewById(R.id.averages_coordinator_layout),
                message,
                Snackbar.LENGTH_INDEFINITE);
        snackbar.setBehavior(behavior);
        snackbar.setAction(RETRY_ACTION_LABEL,
                listener);
        snackbar.setActionTextColor(ContextCompat.getColor(activity,
                R.color.colorPrimary));
        snackbar.show();
    }

    abstract static class RetryListener implements View.OnClickListener {
    }

    class AveragesRetryListener extends RetryListener {

        private final Call<List<Average>> call;
        private final FragmentActivity activity;
        private final View view;
        private final ProgressBar progressBar;
        private final RecyclerView averagesList;

        public AveragesRetryListener(Call<List<Average>> call, FragmentActivity activity, View view, ProgressBar progressBar, RecyclerView averagesList) {
            this.call = call;
            this.activity = activity;
            this.view = view;
            this.progressBar = progressBar;
            this.averagesList = averagesList;
        }

        @Override
        public void onClick(View v) {
            progressBar.setVisibility(View.VISIBLE);
            call.clone().enqueue(new Callback<List<Average>>() {
                @Override
                @EverythingIsNonNull
                public void onResponse(Call<List<Average>> call, Response<List<Average>> response) {
                    configureRecyclerView(response, averagesList, view);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                @EverythingIsNonNull
                public void onFailure(Call<List<Average>> call, Throwable t) {
                    configureErrorSnackbar(view,
                            activity,
                            AVERAGES_LOAD_ERROR_MESSAGE,
                            new BuilderAverages.AveragesRetryListener(call, activity, view, progressBar, averagesList));
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    class RegisterUnsavedExercises extends RetryListener {

        private final Call<Void> call;
        private final ProgressBar progressBar;
        private final View view;
        private final ExerciseDao dao;
        private final String uid;
        private final RecyclerView averagesList;

        public RegisterUnsavedExercises(Call<Void> call, ProgressBar progressBar, View view, ExerciseDao dao, String uid, RecyclerView averagesList) {
            this.call = call;
            this.progressBar = progressBar;
            this.view = view;
            this.dao = dao;
            this.uid = uid;
            this.averagesList = averagesList;
        }

        @Override
        public void onClick(View v) {
            AveragesService service = new BaseRetrofit().getAveragesService();
            progressBar.setVisibility(View.VISIBLE);
            call.clone().enqueue(new Callback<Void>() {
                @Override
                @EverythingIsNonNull
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 200) {
                        Snackbar.make(view, EXERCISES_UPDATE_SUCCESS_MESSAGE, Snackbar.LENGTH_LONG).show();
                        new RemoveExercisesTask(dao, uid).execute();
                    } else if (response.code() == 400) {
                        configureErrorSnackbar(
                                view,
                                activity,
                                EXERCISES_SAVE_ERROR_MESSAGE,
                                new BuilderAverages.RegisterUnsavedExercises(call, progressBar, view, dao, uid, averagesList));
                    }
                    getAveragesFromServer(averagesList, service.getAveragesByUid(uid), progressBar, view);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                @EverythingIsNonNull
                public void onFailure(Call<Void> call, Throwable t) {
                    configureErrorSnackbar(
                            view,
                            activity,
                            EXERCISES_SAVE_ERROR_MESSAGE,
                            new BuilderAverages.RegisterUnsavedExercises(call, progressBar, view, dao, uid, averagesList));

                    getAveragesFromServer(averagesList, service.getAveragesByUid(uid), progressBar, view);
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }
}
