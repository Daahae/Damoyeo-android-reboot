package com.daahae.damoyeo2.model;

import java.util.ArrayList;

public class Messages {

    public static ArrayList<Messages> messages = new ArrayList<>();
    public static int MESSAGE_OTHER = 1;
    public static int MESSAGE_ME = 2;

    private int nMessageType=1;
    private String contents;
    private String name;

    public Messages() {
        nMessageType = 1;
        contents = null;
        name = null;
    }

    public int getnMessageType() {

        return nMessageType;
    }

    public void setnMessageType(int nMessageType) {
        this.nMessageType = nMessageType;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Messages(int nMessageType, String name, String contents) {
        this.name = name;
        this.nMessageType = nMessageType;
        this.contents = contents;
    }
}
