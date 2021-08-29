package com.example.azkar.Moudle;

import java.io.Serializable;
import java.security.PublicKey;

public class AZKARMoudle implements Serializable {
    public int id;
    public String title;
    public String text;
    public String description;
    public String endText;
    public int count;
    public int countminus;
    public int From;

    public AZKARMoudle(int id, String title, String text, String description, String endText, int countminus, int count, int From) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.description = description;
        this.endText = endText;
        this.count = count;
        this.countminus = countminus;
        this.From = From;
    }
    public AZKARMoudle(String title, String text, String description, String endText, int countminus, int count , int From) {
        this.title = title;
        this.text = text;
        this.description = description;
        this.endText = endText;
        this.count = count;
        this.countminus = countminus;
        this.From = From;

    }
}
