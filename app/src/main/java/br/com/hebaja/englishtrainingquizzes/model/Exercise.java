package br.com.hebaja.englishtrainingquizzes.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import br.com.hebaja.englishtrainingquizzes.enums.LevelType;
import br.com.hebaja.englishtrainingquizzes.enums.LevelTypeConverter;

@Entity
public class Exercise {

    @PrimaryKey
    private Long id;
    private String userUid;
    private String subject;
    @TypeConverters(LevelTypeConverter.class)
    private LevelType level;
    private double score;

    @Ignore
    public Exercise(long id, String subject, LevelType level, double score, String userUid) {
        this.id = id;
        this.subject = subject;
        this.level = level;
        this.score = score;
        this.userUid = userUid;
    }

    public Exercise() {}

    public Long getId() { return id; }

    public String getSubject() {
        return subject;
    }

    public LevelType getLevel() {
        return level;
    }

    public double getScore() {
        return score;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setLevel(LevelType level) {
        this.level = level;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "userUid='" + userUid + '\'' +
                ", subject='" + subject + '\'' +
                ", level=" + level +
                ", score=" + score +
                '}';
    }
}