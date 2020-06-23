package br.com.hebaja.englishtrainingquizzes.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class OptionTest {

    @Test
    public void should_ReturnOptionObject_WhenOptionObjectIsSet() {
        Option optionCreated = new Option("Phrasal Verbs", "phrasal_verbs.json", 0);

        String promptReturned = optionCreated.getPrompt();
        String fileNameReturned = optionCreated.getFileName();
        int counterOrderReturned = optionCreated.getCounterOrder();

        assertEquals(promptReturned, "Phrasal Verbs");
        assertEquals(fileNameReturned, "phrasal_verbs.json");
        assertEquals(counterOrderReturned, 0);
    }

}