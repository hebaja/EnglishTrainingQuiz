package br.com.hebaja.englishtrainingquizzes.daos;

import android.content.Context;
import android.content.res.AssetManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.hebaja.englishtrainingquizzes.model.Option;

public class OptionDAO {

    private int order = 0;

    public List<Option> easyList() {
        List<Option> options = new ArrayList<>(Arrays.asList(
            new Option("Comparative and superlative", "easy_comparative_superlative.json", order++),
            new Option("First conditional", "easy_first_conditional.json", order++),
            new Option("Present simple and continuous", "easy_present_simple_continuous.json", order++),
            new Option("Present simple and perfect", "easy_present_simple_perfect.json", order++),
            new Option("Reported speech", "easy_reported_speech.json", order++),
            new Option("Reported speech test", "easy_reported_speech_test.json", order++)));

        return options;
    }

    public List<Option> mediumList() {
        List<Option> options = new ArrayList<>(Arrays.asList(
            new Option("Active and Passive voice", "medium_active_to_passive.json", order++),
            new Option("Comparative and Superlative", "medium_comparative_superlative.json", order++),
            new Option("Modals", "medium_modal_verbs.json", order++),
            new Option("Phrasal verbs", "medium_phrasal_verbs.json", order++),
            new Option("Prepositions", "medium_prepositions.json", order++),
            new Option("Reported Speech", "medium_reported_speech.json", order++),
            new Option("Second conditional", "medium_second_conditional.json", order++),
            new Option("Third conditional", "medium_third_conditional.json", order++)));
        return options;
    }

    public List<Option> hardList() {
        List<Option> options = new ArrayList<>(Arrays.asList(
            new Option("Binomials", "hard_binomials.json", order++),
            new Option("Collocations", "hard_collocations.json", order++),
            new Option("Idiomatic expressions", "hard_idiomatic_expressions.json", order++),
            new Option("Phrasal verbs", "hard_phrasal_verbs.json", order++)));
        return options;
    }
}
