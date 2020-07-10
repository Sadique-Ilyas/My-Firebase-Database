package com.example.myfirebasedatabasecodinginflow;

public class MyProfile
{
    private String Key, Name, Surname, City;

    public MyProfile(){}

    public MyProfile(String name, String surname, String city) {
        Name = name;
        Surname = surname;
        City = city;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
}
