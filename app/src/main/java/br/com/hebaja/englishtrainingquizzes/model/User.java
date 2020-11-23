package br.com.hebaja.englishtrainingquizzes.model;

import java.util.List;

public class User {

    private String username;
    private String email;
    private String password;
    private Boolean enabled;
    private Authority authority;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    private boolean isNew = true;

    private List<Exercise> exercises;

}
