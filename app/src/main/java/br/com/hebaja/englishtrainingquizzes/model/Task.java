package br.com.hebaja.englishtrainingquizzes.model;

import java.util.List;

public class Task {

    private String prompt;
    private List<Option> options;
    private int rightOption;
    public String getPrompt() {
            return prompt;
        }

    public void setPrompt(String prompt) {
            this.prompt = prompt;
        }

    public int getRightOption() {
        return rightOption;
    }

    public void setRightOption(int rightOption) {
        this.rightOption = rightOption;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public static class Option {

        private String prompt;

        private Option(String prompt) {
            this.prompt = prompt;
        }

        public String getPrompt() { return prompt; }

        public void setPrompt(String option0) {
            this.prompt = option0;
        }
    }
}
