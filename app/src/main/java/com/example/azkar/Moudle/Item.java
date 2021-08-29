package com.example.azkar.Moudle;

import java.io.Serializable;

public class Item implements Serializable {
    public int id;
    public String text;
    public int favorite;

    public Item(int id,String text,int favorite) {
        this.id = id;
        this.text = text;
        this.favorite = favorite;
    }
}
