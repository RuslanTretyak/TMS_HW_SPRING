package application.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Student {
    @NotEmpty(message = "field 'name' must not be empty")
    @Size(min = 2, message = "field 'name' value must contain at least 2 characters")
    private String name;
    @NotEmpty(message = "field 'surname' must not be empty")
    @Size(min = 2, message = "field 'surname' value must contain at least 2 characters")
    private String surname;
    @NotEmpty(message = "field 'group' must not be empty")
    private String group;
    @Min(value = 1, message = "value must be at least 1")
    private int age;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getGroup() {
        return group;
    }

    public int getAge() {
        return age;
    }

    public int getId() {
        return id;
    }

}
