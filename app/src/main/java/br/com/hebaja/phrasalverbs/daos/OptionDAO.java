package br.com.hebaja.phrasalverbs.daos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.hebaja.phrasalverbs.model.Option;

public class OptionDAO {

    public List<Option> list() {
        List<Option> options = new ArrayList<>(Arrays.asList(
            new Option("Phrasal Verbs"),
            new Option("Preposition")));
        return options;
    }
}
