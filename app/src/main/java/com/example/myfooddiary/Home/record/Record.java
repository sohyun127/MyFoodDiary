package com.example.myfooddiary.Home.record;

//기록 data 클래스
public class Record {

    private String time;
    private String food;
    private String kcal;
    private String url;

    public Record(){

    }

    public Record(String time,String food,String kcal,String url) {
        this.time = time;
        this.food = food;
        this.kcal = kcal;
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getKcal() {
        return kcal;
    }

    public void setKcal(String kcal) {
        this.kcal = kcal;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }
}
