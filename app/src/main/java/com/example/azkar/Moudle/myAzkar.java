package com.example.azkar.Moudle;

import java.io.Serializable;

public class myAzkar implements Serializable {
    public int id;
    public String title;
    public String text;
    public String description;
    public String endText;
    public int count;
    public int countminus;

    public myAzkar(int id, String title, String text, String description, String endText, int countminus, int count) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.description = description;
        this.endText = endText;
        this.count = count;
        this.countminus = countminus;
    }

    public myAzkar(String title, String text, String description, String endText, int countminus, int count) {
        this.title = title;
        this.text = text;
        this.description = description;
        this.endText = endText;
        this.count = count;
        this.countminus = countminus;
    }
}
