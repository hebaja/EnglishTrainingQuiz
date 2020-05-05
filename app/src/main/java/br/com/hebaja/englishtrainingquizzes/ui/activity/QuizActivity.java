package br.com.hebaja.englishtrainingquizzes.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.hebaja.englishtrainingquizzes.R;
import br.com.hebaja.englishtrainingquizzes.daos.OptionDAO;
import br.com.hebaja.englishtrainingquizzes.model.Option;
import br.com.hebaja.englishtrainingquizzes.model.Question;
import br.com.hebaja.englishtrainingquizzes.ui.dialog.QuitAppDialog;
import br.com.hebaja.englishtrainingquizzes.ui.views.BuilderQuizActivityViews;

import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.APPBAR_TITLE;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.CHOSEN_OPTION_KEY;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.CHOSEN_OPTION_TRY_AGAIN_KEY;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.INVALID_NUMBER;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.MODAL_VERBS_OPTIONS;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.MODAL_VERBS_QUESTIONS_JSON_KEY;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.PHRASAL_VERBS_OPTION;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.PHRASAL_VERBS_QUESTIONS_JSON_KEY;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.PREPOSITIONS_OPTION;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.PREPOSITIONS_QUESTIONS_JSON_KEY;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.QUESTIONS_LIST_FINAL_INDEX;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.QUESTIONS_LIST_INITIAL_INDEX;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.STATE_POSITION;
import static br.com.hebaja.englishtrainingquizzes.ui.activity.Constants.STATE_SCORE;

public class QuizActivity extends AppCompatActivity {

    //Create an list of questions to be populated by the method createQuestionsObjects();
    ArrayList<Question> questions = new ArrayList<>();

    List<Question> finalQuestions = new ArrayList<>();

    private int score = 0;

    private int position;
    private BuilderQuizActivityViews builderQuizActivityViews;

    public static Activity mainActivity;
    private int chosenOptionMenuActivity;
    private List<Option> optionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(APPBAR_TITLE);
        mainActivity = this;

        Intent intent = getIntent();
        if(intent.hasExtra(CHOSEN_OPTION_KEY)) {
            chosenOptionMenuActivity = intent.getIntExtra(CHOSEN_OPTION_KEY, INVALID_NUMBER);
        } else {
            chosenOptionMenuActivity = intent.getIntExtra(CHOSEN_OPTION_TRY_AGAIN_KEY, INVALID_NUMBER);
        }

        optionsList = new OptionDAO().list();

        createQuestionObjectsFromJsonFile();
        builderQuizActivityViews = new BuilderQuizActivityViews(finalQuestions, this, chosenOptionMenuActivity);
        builderQuizActivityViews.initializeViews();
        builderQuizActivityViews.setOptionsButtons(getSupportFragmentManager());
        builderQuizActivityViews.updatePosition();
        builderQuizActivityViews.updateViewsQuestions();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(STATE_SCORE, score);
        outState.putInt(STATE_POSITION, position);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        score = savedInstanceState.getInt(STATE_SCORE);
        builderQuizActivityViews.updateScore(score);

        position = savedInstanceState.getInt(STATE_POSITION);
        builderQuizActivityViews.updateViewsQuestions();
    }

    private void createQuestionObjectsFromJsonFile() {
        try {
            InputStream inputStream = checkMenuChosenOption();
            assert inputStream != null;
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonQuestionsFile = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(jsonQuestionsFile);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                JSONObject questionsObj = (JSONObject) obj.get("question");

                String prompt = (String) questionsObj.get("prompt");
                String optionA = (String) questionsObj.get("optionA");
                String optionB = (String) questionsObj.get("optionB");
                String optionC = (String) questionsObj.get("optionC");
                String rightOption = (String) questionsObj.get("rightOption");

                Question question = new Question();
                question.setPrompt(prompt);
                question.setOptionA(optionA);
                question.setOptionB(optionB);
                question.setOptionC(optionC);
                question.setRightOption(rightOption);

                questions.add(question);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        Collections.shuffle(questions);
        finalQuestions = questions.subList(QUESTIONS_LIST_INITIAL_INDEX, QUESTIONS_LIST_FINAL_INDEX);
    }

    private InputStream checkMenuChosenOption() throws IOException {
        InputStream inputStream = null;
        for(int i = 0; i < optionsList.size(); i++ ) {
            if(chosenOptionMenuActivity == optionsList.get(i).getCounterOrder()) {
                inputStream = getAssets().open(optionsList.get(i).getFileName());
            }
        }
        return inputStream;
    }
}