package br.com.hebaja.englishtrainingquizzes.model;

import br.com.hebaja.englishtrainingquizzes.enums.Roles;

public class Authority {

    private String username;
    private Roles authority;

    public Authority(String username, Roles authority) {
        this.username = username;
        this.authority = authority;
    }

    public Authority() {};

}
