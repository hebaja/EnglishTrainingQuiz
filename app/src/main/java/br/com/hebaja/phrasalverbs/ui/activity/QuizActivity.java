package br.com.hebaja.phrasalverbs.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.hebaja.phrasalverbs.R;
import br.com.hebaja.phrasalverbs.model.Question;
import br.com.hebaja.phrasalverbs.ui.views.BuilderQuizActivityViews;

import static br.com.hebaja.phrasalverbs.ui.activity.Constants.APPBAR_TITLE;
import static br.com.hebaja.phrasalverbs.ui.activity.Constants.CHOSEN_OPTION_KEY;
import static br.com.hebaja.phrasalverbs.ui.activity.Constants.INVALID_NUMBER;
import static br.com.hebaja.phrasalverbs.ui.activity.Constants.PHRASAL_VERBS_OPTION;
import static br.com.hebaja.phrasalverbs.ui.activity.Constants.PHRASAL_VERBS_QUESTIONS_JSON_KEY;
import static br.com.hebaja.phrasalverbs.ui.activity.Constants.PREPOSITIONS_OPTION;
import static br.com.hebaja.phrasalverbs.ui.activity.Constants.PREPOSITIONS_QUESTIONS_JSON_KEY;
import static br.com.hebaja.phrasalverbs.ui.activity.Constants.QUESTIONS_LIST_FINAL_INDEX;
import static br.com.hebaja.phrasalverbs.ui.activity.Constants.QUESTIONS_LIST_INITIAL_INDEX;
import static br.com.hebaja.phrasalverbs.ui.activity.Constants.STATE_POSITION;
import static br.com.hebaja.phrasalverbs.ui.activity.Constants.STATE_SCORE;

public class QuizActivity extends AppCompatActivity {

    //Create an list of questions to be populated by the method createQuestionsObjects();
    ArrayList<Question> questions = new ArrayList<>();

    List<Question> finalQuestions = new ArrayList<>();

    private int score = 0;

    private int position;
    private BuilderQuizActivityViews builderQuizActivityViews;

    public static Activity mainActivity;
    private int chosenOptionMenuActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(APPBAR_TITLE);
        mainActivity = this;

        Intent intent = getIntent();
        chosenOptionMenuActivity = intent.getIntExtra(CHOSEN_OPTION_KEY, INVALID_NUMBER);

        createQuestionObjectsFromJsonFile();
        builderQuizActivityViews = new BuilderQuizActivityViews(finalQuestions, this);
        builderQuizActivityViews.initializeViews();
        builderQuizActivityViews.setOptionsButtons();
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
        if (chosenOptionMenuActivity == PHRASAL_VERBS_OPTION) {
            inputStream = getAssets().open(PHRASAL_VERBS_QUESTIONS_JSON_KEY);
        }
        if (chosenOptionMenuActivity == PREPOSITIONS_OPTION) {
            inputStream = getAssets().open(PREPOSITIONS_QUESTIONS_JSON_KEY);
        }
        return inputStream;
    }
}