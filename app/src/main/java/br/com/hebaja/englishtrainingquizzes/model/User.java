package br.com.hebaja.englishtrainingquizzes.model;

public class User {

    private Long id;
    private String uid;
    private String username;
    private String email;
    private String password;
//    private String password;
//    private Boolean enabled;
//    private Authority authority;

    public User() {}

    public User(String email) {
        this.email = email;
    }
//
//    public User(String username, String email, String password) {
//        this.username = username;
//        this.email = email;
//        this.password = password;
//    }

    public User(Long id, String uid, String username, String email) {
        this.id = id;
        this.uid = uid;
        this.username = username;
        this.email = email;
    }


    public User(String uid, String username, String email) {
        this.uid = uid;
        this.username = username;
        this.email = email;
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }



    public Long getId() { return id;}

    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

//    public String getPassword() {
//        return password;
//    }

//    public List<Exercise> getExercises() {
//        return exercises;
//    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getPassword() {
        return password;
    }

//    public void setPassword(String password) {
//        this.password = password;
//    }

//    public void setExercises(List<Exercise> exercises) {
//        this.exercises = exercises;
//    }

//    private boolean isNew = true;
//
//    private List<Exercise> exercises;

}
