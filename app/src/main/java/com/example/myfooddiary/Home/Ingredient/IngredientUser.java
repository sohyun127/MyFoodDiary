package com.example.myfooddiary.Home.Ingredient;

public class IngredientUser {

    private String count;
    private String name;

    public IngredientUser() {
    }


    public IngredientUser(String name, String count) {
        this.name=name;
        this.count=count;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }


}
