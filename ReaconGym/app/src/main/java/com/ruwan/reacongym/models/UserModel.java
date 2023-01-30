package com.ruwan.reacongym.models;

public class UserModel {
    private int id;
    private String email;
    private String password;
    private String first_name;
    private String last_name;
    private String gender;
    private int age;
    private float weight;
    private float height;

    public UserModel(int id) {
        this.id = id;
    }

    public UserModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserModel(int id, int age, float weight, float height) {
        this.id = id;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    public UserModel(String email, String password, String first_name, String last_name, String gender) {
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
    }

    public UserModel(int id, String email, String password, String first_name, String last_name, String gender, int age, float weight, float height) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public float getWeight() {
        return weight;
    }

    public float getHeight() {
        return height;
    }
}
