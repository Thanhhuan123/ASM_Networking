package com.example.assignandroidnetworking.modal;

public class Notify {
    private String title;
    private String time;

    public Notify() {
    }
    public Notify( String title, String time) {
        this.title = title;
        this.time = time;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



    @Override
    public String toString() {
        return "Notify{" +
                ", title='" + title + '\'' +
                '}';
    }
}
