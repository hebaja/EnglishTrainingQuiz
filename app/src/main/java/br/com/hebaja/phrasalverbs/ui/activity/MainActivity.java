package br.com.hebaja.phrasalverbs.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import br.com.hebaja.phrasalverbs.R;
import br.com.hebaja.phrasalverbs.model.Question;
import br.com.hebaja.phrasalverbs.model.RightAnswer;

public class MainActivity extends AppCompatActivity {

    public static final String STATE_SCORE = "stateScore";
    public static final String STATE_POSITION = "statePosition";
    public static final String APPBAR_TITLE = "Phrasal Verbs Quiz";

    //Create an list of questions to be populated by the method createQuestionsObjects();
    ArrayList<Question> questions = new ArrayList<>();
    ArrayList<RightAnswer> rightAnswers = new ArrayList<>();

    //Create scanners for text files in assets folder
    Scanner scannerQuestionsOptions;
    Scanner scannerRightAnswers;

    private TextView textViewQuestion;
    private TextView scoreView;
    private Button buttonOptionA;
    private Button buttonOptionB;
    private Button buttonOptionC;
    private Button buttonQuit;

    private RightAnswer rightAnswerForTest;
    String positionRightAnswer;

    private int score = 0;

    private String optionPositionA;
    private String optionPositionB;
    private String optionPositionC;

    private int position;
    private int positionRestored = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(APPBAR_TITLE);


        try {
            getAssetsFromFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }

        createQuestionsObjects();

        createRightAnswersObjects();

        initializeViews();

        updatePosition();

        updateViewsQuestions();

        updateRightAnswerObjects();

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
                if (optionPositionA.equals(positionRightAnswer)) {
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
                if (optionPositionB.equals(positionRightAnswer)) {
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
                if (optionPositionC.equals(positionRightAnswer)) {
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
        try {
            updateViewsQuestions();
            updatePosition();
            updateRightAnswerObjects();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            Intent intent = new Intent(MainActivity.this, FinalScoreActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);
            finish();

        }
    }

    private void createQuestionsObjects() {

            while(scannerQuestionsOptions.hasNextLine()) {
                String lineQuestionOptions = scannerQuestionsOptions.nextLine();
                Scanner formattedLine = new Scanner(lineQuestionOptions);
                formattedLine.useDelimiter(",");

                Question question = new Question();

                question.setPrompt(formattedLine.next());
                question.setOptionA(formattedLine.next());
                question.setOptionB(formattedLine.next());
                question.setOptionC(formattedLine.next());

                questions.add(question);

            }
        }

        private void createRightAnswersObjects() {
            while(scannerRightAnswers.hasNextLine()) {
                String lineRightAnswers = scannerRightAnswers.nextLine();
                Scanner separatedLine = new Scanner(lineRightAnswers);

                RightAnswer rightAnswerObject = new RightAnswer();

                rightAnswerObject.setRightAnswer(separatedLine.next());
                rightAnswers.add(rightAnswerObject);

            }

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

        private void updateRightAnswerObjects() {
                rightAnswerForTest = rightAnswers.get(position);
                positionRightAnswer = rightAnswerForTest.getRightAnswer();
        }

        private void updatePosition() {
            position = position + positionRestored;
            Log.i("position", "updatePosition: " + position);
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
            updateRightAnswerObjects();
        }

    private void getAssetsFromFiles() throws IOException {
            AssetManager assetManager = getAssets();

            InputStream questionsFileFromAsset = assetManager.open("questions.csv");
            scannerQuestionsOptions = new Scanner(questionsFileFromAsset);

            InputStream rightAnswersFileFromAsset = assetManager.open("questions_answers.txt");
            scannerRightAnswers = new Scanner(rightAnswersFileFromAsset);
        }
}