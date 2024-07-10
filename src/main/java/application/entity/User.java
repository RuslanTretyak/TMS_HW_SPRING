package application.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class User {
    @NotEmpty
    private String login;
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @Min(1)
    private int age;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}