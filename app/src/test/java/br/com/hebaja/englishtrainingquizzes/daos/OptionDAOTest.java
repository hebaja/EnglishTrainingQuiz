package br.com.hebaja.englishtrainingquizzes.daos;

import org.junit.Test;

import java.util.List;

import br.com.hebaja.englishtrainingquizzes.model.Option;

import static org.junit.Assert.*;

public class OptionDAOTest {

    @Test
    public void should_ReturnListOfOptions() {
        List<Option> listReturned = new OptionDAO().list();

        String optionPositionZeroPrompt = listReturned.get(0).getPrompt();
        String optionPositionNinePrompt = listReturned.get(9).getPrompt();

        assertEquals(optionPositionZeroPrompt, "Phrasal Verbs");
        assertEquals(optionPositionNinePrompt, "Third Conditional");
    }
}