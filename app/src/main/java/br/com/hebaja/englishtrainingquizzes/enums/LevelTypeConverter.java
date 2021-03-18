package br.com.hebaja.englishtrainingquizzes.enums;

import androidx.room.TypeConverter;

public class LevelTypeConverter {

    @TypeConverter
    public static LevelType toLevelType(String level) {
        if(level.equals(LevelType.EASY.name())) {
            return LevelType.EASY;
        } else if(level.equals(LevelType.MEDIUM.name())) {
            return LevelType.MEDIUM;
        } else if(level.equals(LevelType.HARD.name())) {
            return LevelType.HARD;
        } else {
            throw new IllegalArgumentException("Could not recognize level");
        }
    }

    @TypeConverter
    public static String toString(LevelType level) {
        if(level.equals(LevelType.EASY)) {
            return "EASY";
        } else if(level.equals(LevelType.MEDIUM)) {
            return "MEDIUM";
        } else if(level.equals(LevelType.HARD)) {
            return "HARD";
        } else {
            throw new IllegalArgumentException("Could not recognize level");
        }
    }

}
