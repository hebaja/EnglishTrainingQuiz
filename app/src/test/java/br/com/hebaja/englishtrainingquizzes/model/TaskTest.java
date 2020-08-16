package br.com.hebaja.englishtrainingquizzes.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class TaskTest {

    @Test
    public void should_ReturnQuestionObject_WhenQuestionObjectIsSet() {
        Task task = new Task();

        task.setPrompt("Prompt");
        String promptReturned = task.getPrompt();
        task.setOptionA("OptionA");
        String optionAReturned = task.getOptionA();
        task.setOptionB("OptionB");
        String optionBReturned = task.getOptionB();
        task.setOptionC("OptionC");
        String optionCReturned = task.getOptionC();
        task.setRightOption("a");
        String rightOptionReturned = task.getRightOption();

        assertEquals(promptReturned, "Prompt");
        assertEquals(optionAReturned, "OptionA");
        assertEquals(optionBReturned, "OptionB");
        assertEquals(optionCReturned, "OptionC");
        assertEquals(rightOptionReturned, "a");
    }
}