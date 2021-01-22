package br.com.hebaja.englishtrainingquizzes.model;

import br.com.hebaja.englishtrainingquizzes.enums.LevelType;

public class Exercise {

    private Long id;
    private User user;
    private String subject;
    private LevelType level;
    private double score;

    public Exercise(User user, String subject, LevelType level, double score) {
        this.user = user;
        this.subject = subject;
        this.level = level;
        this.score = score;
    }

    public Exercise() {}

    public Long getId() { return id; }

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
