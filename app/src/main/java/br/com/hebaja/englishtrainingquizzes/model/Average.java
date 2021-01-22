package br.com.hebaja.englishtrainingquizzes.model;

import br.com.hebaja.englishtrainingquizzes.enums.LevelType;

public class Average {

    private String name;
    private String subject;
    private LevelType level;
    //	private double average;
    private double average;

//	public Average(String name, String subject, LevelType level, double average) {
//		this.name = name;
//		this.subject = subject;
//		this.level = level;
//		this.average = average;
//	}

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

//	public double getAverage() {
//		return average;
//	}

    public double getAverage() {
        return average;
    }
}
