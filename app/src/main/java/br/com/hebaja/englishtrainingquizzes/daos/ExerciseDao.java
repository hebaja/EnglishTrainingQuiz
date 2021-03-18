package br.com.hebaja.englishtrainingquizzes.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import br.com.hebaja.englishtrainingquizzes.model.Exercise;
import br.com.hebaja.englishtrainingquizzes.model.User;
import br.com.hebaja.englishtrainingquizzes.model.UserAndExercises;

@Dao
public interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(Exercise exercise);

    @Query("select * from Exercise where userUid = :userUid")
    List<Exercise> getExercisesFromUser(String userUid);

    @Query("delete from Exercise where userUid = :userUid")
    void removeExercises(String userUid);
}
