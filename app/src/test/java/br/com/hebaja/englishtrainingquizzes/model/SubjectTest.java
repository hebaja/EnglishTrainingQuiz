package br.com.hebaja.englishtrainingquizzes.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class SubjectTest {

    @Test
    public void should_ReturnOptionObject_WhenOptionObjectIsSet() {
        Subject subjectCreated = new Subject("Phrasal Verbs", "phrasal_verbs.json", 0);

        String promptReturned = subjectCreated.getPrompt();
        String fileNameReturned = subjectCreated.getFileName();
        int counterOrderReturned = subjectCreated.getCounterOrder();

        assertEquals(promptReturned, "Phrasal Verbs");
        assertEquals(fileNameReturned, "phrasal_verbs.json");
        assertEquals(counterOrderReturned, 0);
    }

}