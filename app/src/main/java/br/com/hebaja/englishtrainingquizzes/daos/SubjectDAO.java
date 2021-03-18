package br.com.hebaja.englishtrainingquizzes.daos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.hebaja.englishtrainingquizzes.model.Subject;

public class SubjectDAO {

    private int order = 0;

    public List<Subject> easyList() {
        List<Subject> subjects = new ArrayList<>(Arrays.asList(
                new Subject("Comparative and superlative", "easy_comparative_superlative.json", order++),
                new Subject("First conditional", "easy_first_conditional.json", order++),
                new Subject("Present simple and continuous", "easy_present_simple_continuous.json", order++),
                new Subject("Present simple and perfect", "easy_present_simple_perfect.json", order++),
                new Subject("Reported speech", "easy_reported_speech.json", order++),
                new Subject("Simple past", "easy_simple_past.json", order++),
                new Subject("Simple present", "easy_simple_present.json", order++),
                new Subject("Verb to be", "easy_verb_to_be.json", order++)));
        return subjects;
    }

    public List<Subject> mediumList() {
        List<Subject> subjects = new ArrayList<>(Arrays.asList(
                new Subject("Active and Passive voice", "medium_active_to_passive.json", order++),
                new Subject("Comparative and Superlative", "medium_comparative_superlative.json", order++),
                new Subject("Modals", "medium_modal_verbs.json", order++),
                new Subject("Phrasal verbs", "medium_phrasal_verbs.json", order++),
                new Subject("Prepositions", "medium_prepositions.json", order++),
                new Subject("Reported Speech", "medium_reported_speech.json", order++),
                new Subject("Second conditional", "medium_second_conditional.json", order++),
                new Subject("Third conditional", "medium_third_conditional.json", order++),
                new Subject("Adverbs", "medium_adverbs.json", order++)));
        return subjects;
    }

    public List<Subject> hardList() {
        List<Subject> subjects = new ArrayList<>(Arrays.asList(
                new Subject("Articles and determiners", "hard_articles_determiners.json", order++),
                new Subject("Binomials", "hard_binomials.json", order++),
                new Subject("Collocations", "hard_collocations.json", order++),
                new Subject("Idiomatic expressions", "hard_idiomatic_expressions.json", order++),
                new Subject("Linking words", "hard_linking_words.json", order++),
                new Subject("Phrasal verbs", "hard_phrasal_verbs.json", order++),
                new Subject("Similes", "hard_similes.json", order++),
                new Subject("Word Formation", "hard_word_formation.json", order++)));
        return subjects;
    }
}