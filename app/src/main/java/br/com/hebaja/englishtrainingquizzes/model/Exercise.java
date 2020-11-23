package br.com.hebaja.englishtrainingquizzes.model;

import br.com.hebaja.englishtrainingquizzes.enums.LevelType;

public class Exercise {

    private User user;
    private String subject;
    private LevelType level;
    private double score;

    public User getUser() {
        return user;
    }

    public String getSubject() {
        return subject;
    }

    public LevelType getLevel() {
        return level;
    }

    public double getScore() {
        return score;
    }
}
