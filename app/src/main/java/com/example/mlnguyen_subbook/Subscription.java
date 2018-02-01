package com.example.mlnguyen_subbook;

import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import java.io.Serializable;
import java.util.Date;
import java.util.jar.Attributes;

/**
 * Created by kio22 on 2018-01-31.
 */

public class Subscription implements Serializable {


    private String name;
    private Date date;
    private float charge;
    private String comment;

    Subscription(String name, Float charge) {
        this.name = name;
        date = new Date();
        this.charge = charge;
        comment = new String("");
    }

    Subscription(String name, Date date, float charge, String comment) {
        Log.i("test", "hlelo");
        this.name = name;
        this.date = date;
        this.charge = charge;
        this.comment = comment;

    }

    Subscription(String name, Date date, Float charge) {
        this.name = name;
        this.date = date;
        this.charge = charge;
        comment = new String("");

    }

    Subscription(String name, Float charge, String comment) {
        this.name = name;
        date = new Date();
        this.charge = charge;
        this.comment = comment;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name) throws SubscriptionTooLongException{
        if(name.length() <= 20){
            this.name = name;
        }
        else{
            throw new SubscriptionTooLongException();
        }
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public float getCharge() {
        return charge;
    }

    public void setCharge(float charge){
        this.charge = charge;
    }

    public String getComment(){
        return comment;
    }

    public void setComment() throws SubscriptionTooLongException{
        if(comment.length()<=30){
            this.comment = comment;
        }
        else{
            throw new SubscriptionTooLongException();
        }
    }

    public String toString(){
        Log.i("test",date.toString() + "|" + name + "|" + Float.toString(charge) + "|" + comment);
        return date.toString() + "|" + name + "|" + Float.toString(charge) + "|" + comment;
    }

}