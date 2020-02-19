package com.money.manger.model;

public class MyListData{
    private String item_name;
    private int amt;
    private String date;
    private int id;
    private byte[] img;

    public MyListData(int id, String item_name, int amt, String date, byte[] img) {
        this.item_name = item_name;
        this.amt = amt;
        this.id = id;
        this.date = date;
        this.img = img;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImg() {
        return img;
    }
    public void setId(byte[] img) {
        this.img = img;
    }

    public String getItem() {
        return item_name;
    }
    public void setItem(String item_name) {
        this.item_name = item_name;
    }

    public int getAmt() {
        return amt;
    }
    public void setAmt(int amt) {
        this.amt = amt;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}