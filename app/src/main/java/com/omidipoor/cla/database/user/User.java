package com.omidipoor.cla.database.user;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "user")
public class User {

    public User() {
    }

    public User(String firstName, String lastName, int age, Double lastLong, Double lastLat) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.lastLong = lastLong;
        this.lastLat = lastLat;
    }

    @PrimaryKey(autoGenerate = true)
    public long userId;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @ColumnInfo(name = "age")
    public int age;

    @ColumnInfo(name = "last_long")
    public Double lastLong;

    @ColumnInfo(name = "last_lat")
    public Double lastLat;

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @ColumnInfo(name = "image_url")
    public String image_url;



    public long getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Double getLastLong() {
        return lastLong;
    }

    public void setLastLong(Double lastLong) {
        this.lastLong = lastLong;
    }

    public Double getLastLat() {
        return lastLat;
    }

    public void setLastLat(Double lastLat) {
        this.lastLat = lastLat;
    }


}