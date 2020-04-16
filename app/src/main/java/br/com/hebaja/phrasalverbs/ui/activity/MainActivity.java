package br.com.hebaja.phrasalverbs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import br.com.hebaja.phrasalverbs.R;
import br.com.hebaja.phrasalverbs.model.Question;

public class MainActivity extends AppCompatActivity {

    public static final String STATE_SCORE = "stateScore";
    public static final String STATE_POSITION = "statePosition";
    public static final String APPBAR_TITLE = "Phrasal Verbs Quiz";

    //Create an list of questions to be populated by the method createQuestionsObjects();
    ArrayList<Question> questions = new ArrayList<>();

    private TextView textViewQuestion;
    private TextView scoreView;
    private Button buttonOptionA;
    private Button buttonOptionB;
    private Button buttonOptionC;
    private Button buttonQuit;

    private int score = 0;

    private String optionPositionA;
    private String optionPositionB;
    private String optionPositionC;

    private int position;
    private int positionRestored = 0;
    private String rightAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(APPBAR_TITLE);

        createQuestionObjectsFromJsonFile();

        initializeViews();

        updatePosition();

        updateViewsQuestions();

        setOptionsButtons();

    }

    private void initializeViews() {
        textViewQuestion = findViewById(R.id.id_question);
        scoreView = findViewById(R.id.score_counter);
        buttonOptionA = findViewById(R.id.button_option_a);
        buttonOptionB = findViewById(R.id.button_option_b);
        buttonOptionC = findViewById(R.id.button_option_c);
        buttonQuit = findViewById(R.id.button_quit);
    }

    private void setOptionsButtons() {



        buttonOptionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (optionPositionA.equals(rightAnswer)) {
                    score++;
                    position++;
                    updateScore(score);
                    updateMainActivityOrGoToFinalActivity();
                    Toast.makeText(MainActivity.this, "Right Answer", Toast.LENGTH_SHORT).show();
                } else {
                    position++;
                    updateMainActivityOrGoToFinalActivity();
                    Toast.makeText(MainActivity.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonOptionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (optionPositionB.equals(rightAnswer)) {
                    score++;
                    position++;
                    updateScore(score);
                    updateMainActivityOrGoToFinalActivity();
                    Toast.makeText(MainActivity.this, "Right Answer", Toast.LENGTH_SHORT).show();
                } else {
                    position++;
                    updateMainActivityOrGoToFinalActivity();
                    Toast.makeText(MainActivity.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonOptionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (optionPositionC.equals(rightAnswer)) {
                    score++;
                    position++;
                    updateScore(score);
                    updateMainActivityOrGoToFinalActivity();
                    Toast.makeText(MainActivity.this, "Right Answer", Toast.LENGTH_SHORT).show();
                } else {
                    position++;
                    updateMainActivityOrGoToFinalActivity();
                    Toast.makeText(MainActivity.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void updateMainActivityOrGoToFinalActivity() {
        if(questions.size() > position) {
            updateViewsQuestions();
            updatePosition();
        } else {
            goToFinalSocreActivity();
        }
    }

    private void goToFinalSocreActivity() {
        disableAllButtons();
        startFinalScoreActivity();
        finish();
    }

    private void startFinalScoreActivity() {
        Intent intent = new Intent(MainActivity.this, FinalScoreActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
    }

    private void disableAllButtons() {
        buttonOptionA.setEnabled(false);
        buttonOptionB.setEnabled(false);
        buttonOptionC.setEnabled(false);
        buttonQuit.setEnabled(false);
    }

    private void updateViewsQuestions() {
        textViewQuestion.setText(questions.get(position).getPrompt());
        buttonOptionA.setText(questions.get(position).getOptionA());
        buttonOptionB.setText(questions.get(position).getOptionB());
        buttonOptionC.setText(questions.get(position).getOptionC());

        optionPositionA = "a";
        optionPositionB = "b";
        optionPositionC = "c";

        updateScore(score);
    }

    private void updatePosition() {
        position = position + positionRestored;
        rightAnswer = questions.get(position).getRightOption();
    }

    private void updateScore(int score) {
        scoreView.setText(String.valueOf(score));
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
        updateScore(score);

        position = savedInstanceState.getInt(STATE_POSITION);
        updateViewsQuestions();
    }

    private void createQuestionObjectsFromJsonFile() {
        try {
            InputStream inputStream = getAssets().open("phrasal_verbs_questions.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonQuestionsFile = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(jsonQuestionsFile);

            for(int i = 0; i<jsonArray.length(); i++) {
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
    }
}