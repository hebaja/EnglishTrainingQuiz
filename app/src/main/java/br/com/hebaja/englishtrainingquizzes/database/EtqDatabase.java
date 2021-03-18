package br.com.hebaja.englishtrainingquizzes.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.com.hebaja.englishtrainingquizzes.daos.ExerciseDao;
import br.com.hebaja.englishtrainingquizzes.daos.FileChecksumDao;
import br.com.hebaja.englishtrainingquizzes.model.Exercise;
import br.com.hebaja.englishtrainingquizzes.model.FileChecksum;

@Database(entities = {FileChecksum.class, Exercise.class}, version = 9, exportSchema = false)
public abstract class EtqDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "etq.db";

    public abstract FileChecksumDao getFileChecksumDao();
    public abstract ExerciseDao getExercisesDao();

    public static EtqDatabase getInstance(Context context) {
        return Room
                .databaseBuilder(context, EtqDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }
}