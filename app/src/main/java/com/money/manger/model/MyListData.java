package com.money.manger.model;

public class MyListData{
    private String item_name;
    private String amt;
    private int id;

    public MyListData(int id, String item_name, String amt) {
        this.item_name = item_name;
        this.amt = amt;
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item_name;
    }
    public void setItem(String item_name) {
        this.item_name = item_name;
    }

    public String getAmt() {
        return amt;
    }
    public void setAmt(String amt) {
        this.amt = amt;
    }
}