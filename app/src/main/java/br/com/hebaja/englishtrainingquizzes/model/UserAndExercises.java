package br.com.hebaja.englishtrainingquizzes.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserAndExercises {

    @Embedded
    private User user;
    @Relation(
            parentColumn = "uid",
            entityColumn = "userUid"
    )
    private List<Exercise> exercises;

    public void setUser(User user) {
        this.user = user;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
