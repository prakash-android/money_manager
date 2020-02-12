package com.money.manger.model;

public class MyListData{
    private String item_name;
    private String amt;

    public MyListData(String item_name, String amt) {
        this.item_name = item_name;
        this.amt = amt;
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