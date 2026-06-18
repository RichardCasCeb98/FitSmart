package com.miempresa.fitsmart;

public class Profile {

    private int age;
    private double weight;
    private double height;
    private String level;
    private String goal;

    public Profile(int age, double weight, double height, String level, String goal) {

        this.age = age;
        this.weight = weight;
        this.height = height;
        this.level = level;
        this.goal = goal;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public String getLevel() {
        return level;
    }

    public String getGoal() {
        return goal;
    }
}
