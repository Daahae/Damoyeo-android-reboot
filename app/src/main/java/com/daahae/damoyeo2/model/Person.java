package com.daahae.damoyeo2.model;

import java.util.ArrayList;

public class Person{
    private static ArrayList<Person> instance = new ArrayList<Person>();
    private int id;
    private String nickname;
    private String name;
    private String address;
    private Position addressPosition; // 주소의 좌표

    public Person(String nickname, String name, String address, Position addressPosition) {
        this.nickname = nickname;
        this.name = name;
        this.address = address;
        this.addressPosition = addressPosition;
    }

    public static synchronized ArrayList<Person> getInstance(){
        return instance;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Position getAddressPosition() {
        return addressPosition;
    }

    public void setAddressPosition(Position addressPosition) {
        this.addressPosition = addressPosition;
    }
}
