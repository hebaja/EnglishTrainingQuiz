package br.com.hebaja.englishtrainingquizzes;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.hebaja.englishtrainingquizzes.daos.SubjectDAO;
import br.com.hebaja.englishtrainingquizzes.model.Subject;
import br.com.hebaja.englishtrainingquizzes.model.Task;

import static br.com.hebaja.englishtrainingquizzes.Constants.EASY_MODE;
import static br.com.hebaja.englishtrainingquizzes.Constants.HARD_MODE;
import static br.com.hebaja.englishtrainingquizzes.Constants.MEDIUM_MODE;
import static br.com.hebaja.englishtrainingquizzes.Constants.TASKS_LIST_FINAL_INDEX;
import static br.com.hebaja.englishtrainingquizzes.Constants.TASKS_LIST_INITIAL_INDEX;

public class TaskObjectsGenerator {

    ArrayList<Task> tasks = new ArrayList<>();
    List<Task> finalTasks = new ArrayList<>();
    private List<Subject> subjectsList;
    private final int chosenSubject;
    private final Context context;
    private final int levelKey;

    public TaskObjectsGenerator(int levelKey, int chosenSubject, Context context) {
        this.chosenSubject = chosenSubject;
        this.context = context;
        this.levelKey = levelKey;
    }

    public List<Task> generateTaskObjectsFromJsonFile() {
        switch (levelKey) {
            case EASY_MODE:
                subjectsList = new SubjectDAO().easyList();
                break;
            case MEDIUM_MODE:
                subjectsList = new SubjectDAO().mediumList();
                break;
            case HARD_MODE:
                subjectsList = new SubjectDAO().hardList();
                break;
        }

        try {
            InputStream inputStream = checkMenuChosenSubject();
            if (inputStream == null) throw new AssertionError();
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonQuestionsFile = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(jsonQuestionsFile);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                JSONObject tasksObj = (JSONObject) obj.get("task");

                String prompt = (String) tasksObj.get("prompt");
                String optionA = (String) tasksObj.get("optionA");
                String optionB = (String) tasksObj.get("optionB");
                String optionC = (String) tasksObj.get("optionC");
                String rightOption = (String) tasksObj.get("rightOption");

                Task task = new Task();
                task.setPrompt(prompt);
                task.setOptionA(optionA);
                task.setOptionB(optionB);
                task.setOptionC(optionC);
                task.setRightOption(rightOption);

                tasks.add(task);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        Collections.shuffle(tasks);
        finalTasks = tasks.subList(TASKS_LIST_INITIAL_INDEX, TASKS_LIST_FINAL_INDEX);
        return finalTasks;
    }

    private InputStream checkMenuChosenSubject() throws IOException {
        InputStream inputStream = null;
        for (int i = 0; i < subjectsList.size(); i++) {
            if (chosenSubject == subjectsList.get(i).getCounterOrder()) {
                inputStream = context.getAssets().open(subjectsList.get(i).getFileName());
                break;
            }
        }
        return inputStream;
    }
}