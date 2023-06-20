package com.example.myfooddiary.Home;

public class User {

    private String id;
    private String pw;
    private String nickName;
    private String gender;
    private String birth;
    private String height;
    private String weight;
    private int kcal;

    public User(){

    }

    public User(String id, String pw, String nickName, String gender, String birth, String height, String weight,int kcal){
        this.id = id;
        this.pw = pw;
        this.nickName = nickName;
        this.gender = gender;
        this.birth = birth;
        this.height = height;
        this.weight = weight;
        this.kcal = kcal;
    }

    public String getId(){return  id;}
    public String getPw(){return pw;}
    public String getGender(){return gender;}
    public String getBirth(){return birth;}
    public String getHeight(){return height;}
    public String getNickName(){return nickName;}
    public String getWeight(){return weight;}

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
