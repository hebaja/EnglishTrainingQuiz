package br.com.hebaja.englishtrainingquizzes.daos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.hebaja.englishtrainingquizzes.model.Option;

public class OptionDAO {

    private int order = 0;

    public List<Option> list() {
        List<Option> options = new ArrayList<>(Arrays.asList(
            new Option("Phrasal Verbs", "phrasal_verbs.json", order++),
            new Option("Preposition", "prepositions.json", order++),
            new Option("Modals", "modal_verbs.json", order++),
            new Option("Active and Passive voice", "active_to_passive.json", order++),
            new Option("Comparative and Superlative", "comparative_superlative.json", order++),
            new Option("Reported Speech", "reported_speech.json", order++),
            new Option("Noun Clauses", "noun_clauses.json", order++),
            new Option("First Conditional", "first_conditional.json", order++),
            new Option("Second Conditional", "second_conditional.json", order++),
            new Option("Third Conditional", "third_conditional.json", order++)));
        return options;
    }
}
