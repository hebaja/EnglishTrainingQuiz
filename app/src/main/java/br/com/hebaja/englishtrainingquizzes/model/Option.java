package br.com.hebaja.englishtrainingquizzes.model;

public class Option {

    private final String prompt;
    private final String fileName;
    private final int counterOrder;

    public Option(String prompt, String fileName, int counterOrder) {
        this.prompt = prompt;
        this.fileName = fileName;
        this.counterOrder = counterOrder;
    }

    public String getPrompt() {
        return prompt;
    }
    public String getFileName() { return  fileName; }
    public int getCounterOrder() { return counterOrder; }
}
