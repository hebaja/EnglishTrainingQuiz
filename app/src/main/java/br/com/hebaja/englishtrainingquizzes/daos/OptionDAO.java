package br.com.hebaja.englishtrainingquizzes.daos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.hebaja.englishtrainingquizzes.model.Option;

public class OptionDAO {

    private int order = 0;

    public List<Option> list() {
        List<Option> options = new ArrayList<>(Arrays.asList(
            new Option("Phrasal Verbs", "phrasal_verbs_questions.json", order++),
            new Option("Preposition", "prepositions_questions.json", order++),
            new Option("Modals", "modal_verbs_questions.json", order++)));
        return options;
    }
}
