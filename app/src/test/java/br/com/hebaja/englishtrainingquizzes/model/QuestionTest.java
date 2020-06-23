package br.com.hebaja.englishtrainingquizzes.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionTest {

    @Test
    public void should_ReturnQuestionObject_WhenQuestionObjectIsSet() {
        Question question = new Question();

        question.setPrompt("Prompt");
        String promptReturned = question.getPrompt();
        question.setOptionA("OptionA");
        String optionAReturned = question.getOptionA();
        question.setOptionB("OptionB");
        String optionBReturned = question.getOptionB();
        question.setOptionC("OptionC");
        String optionCReturned = question.getOptionC();
        question.setRightOption("a");
        String rightOptionReturned = question.getRightOption();

        assertEquals(promptReturned, "Prompt");
        assertEquals(optionAReturned, "OptionA");
        assertEquals(optionBReturned, "OptionB");
        assertEquals(optionCReturned, "OptionC");
        assertEquals(rightOptionReturned, "a");
    }
}