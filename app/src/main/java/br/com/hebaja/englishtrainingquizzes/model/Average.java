package br.com.hebaja.englishtrainingquizzes.model;

import br.com.hebaja.englishtrainingquizzes.enums.LevelType;

public class Average {

    private final String name;
    private final String subject;
    private final LevelType level;
    private final double average;

    public Average(String name, String subject, LevelType level, double average) {
        this.name = name;
        this.subject = subject;
        this.level = level;
        this.average = average;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public LevelType getLevel() {
        return level;
    }

    public double getAverage() {
        return average;
    }
}
