package br.com.hebaja.englishtrainingquizzes.model;

public class Task {

    private String prompt;
    private String optionA;
    private String optionB;
    private String optionC;
    private String rightOption;

    public String getPrompt() {
            return prompt;
        }

    public void setPrompt(String prompt) {
            this.prompt = prompt;
        }

    public String getOptionA() {
            return optionA;
        }

    public void setOptionA(String optionA) {
            this.optionA = optionA;
        }

    public String getOptionB() {
            return optionB;
        }

    public void setOptionB(String optionB) {
            this.optionB = optionB;
        }

    public String getOptionC() {
            return optionC;
        }

    public void setOptionC(String optionC) {
            this.optionC = optionC;
        }

    public String getRightOption() {
        return rightOption;
    }

    public void setRightOption(String rightOption) {
        this.rightOption = rightOption;
    }
}
