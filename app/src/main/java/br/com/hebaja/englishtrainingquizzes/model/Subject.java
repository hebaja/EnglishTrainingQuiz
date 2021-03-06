package br.com.hebaja.englishtrainingquizzes.model;

public class Subject implements Comparable<Subject> {

    private final String prompt;
    private final String fileName;
    private final int counterOrder;

    public Subject(String prompt, String fileName, int counterOrder) {
        this.prompt = prompt;
        this.fileName = fileName;
        this.counterOrder = counterOrder;
    }

    public String getPrompt() {
        return prompt;
    }
    public String getFileName() { return  fileName; }
    public int getCounterOrder() { return counterOrder; }

    @Override
    public int compareTo(Subject subject) {
        return this.prompt.compareTo(subject.getPrompt());
    }
}
