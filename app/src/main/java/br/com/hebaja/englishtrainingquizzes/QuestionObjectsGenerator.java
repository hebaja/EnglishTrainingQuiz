package br.com.hebaja.englishtrainingquizzes;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.hebaja.englishtrainingquizzes.daos.OptionDAO;
import br.com.hebaja.englishtrainingquizzes.model.Option;
import br.com.hebaja.englishtrainingquizzes.model.Question;

import static br.com.hebaja.englishtrainingquizzes.Constants.EASY_MODE;
import static br.com.hebaja.englishtrainingquizzes.Constants.HARD_MODE;
import static br.com.hebaja.englishtrainingquizzes.Constants.MEDIUM_MODE;
import static br.com.hebaja.englishtrainingquizzes.Constants.QUESTIONS_LIST_FINAL_INDEX;
import static br.com.hebaja.englishtrainingquizzes.Constants.QUESTIONS_LIST_INITIAL_INDEX;

public class QuestionObjectsGenerator {

    ArrayList<Question> questions = new ArrayList<>();
    List<Question> finalQuestions = new ArrayList<>();
    private List<Option> optionsList;
    private final int chosenOptionMenuActivity;
    private final Context context;
    private final int levelKey;

    public QuestionObjectsGenerator(int levelKey, int chosenOptionMenuActivity, Context context) {
        this.chosenOptionMenuActivity = chosenOptionMenuActivity;
        this.context = context;
        this.levelKey = levelKey;
    };

    public List<Question> generateQuestionObjectsFromJsonFile() {
        switch (levelKey) {
            case EASY_MODE:
                optionsList = new OptionDAO().easyList();
                break;
            case MEDIUM_MODE:
                optionsList = new OptionDAO().mediumList();
                break;
            case HARD_MODE:
                optionsList = new OptionDAO().hardList();
                break;
        }

        try {
            InputStream inputStream = checkMenuChosenOption();
            if (inputStream == null) throw new AssertionError();
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

        return finalQuestions;
    }

    private InputStream checkMenuChosenOption() throws IOException {
        InputStream inputStream = null;
        for(int i = 0; i < optionsList.size(); i++ ) {
            if(chosenOptionMenuActivity == optionsList.get(i).getCounterOrder()) {
                inputStream = context.getAssets().open(optionsList.get(i).getFileName());
                break;
            }
        }
        return inputStream;
    }

}
