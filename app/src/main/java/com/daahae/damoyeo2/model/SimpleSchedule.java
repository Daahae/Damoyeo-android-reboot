package com.daahae.damoyeo2.model;

public class SimpleSchedule {
    private String txtContents;

    public SimpleSchedule() {
        this.txtContents = "";
    }

    public SimpleSchedule(String txtContents) {
        this.txtContents = txtContents;
    }

    public String getTxtContents() {
        return txtContents;
    }

    public void setTxtContents(String txtContents) {
        this.txtContents = txtContents;
    }
}
