package com.example.mlnguyen_subbook;


import android.util.Log;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by kio22 on 2018-01-31.
 */

public class Subscription implements Serializable {


    private String name;
    private Date date;
    private Float charge;
    private String comment;




    Subscription(String name, Date date, float charge, String comment) {
        Log.i("test", "hlelo");
        this.name = name;
        this.date = date;
        this.charge = charge;
        this.comment = comment;

    }



    public String getName()
    {
        return name;
    }

    public void setName(String name){
        this.name = name;

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

    public void setComment() {
        this.comment = comment;
    }

    public String toString(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        Log.i("test",date.toString() + "|" + name + "|" + Float.toString(charge) + "|" + comment);
        return String.format("%-19s",df.format(date)) + ""  + String.format("%-36s",name) + String.format("$%.2f",charge) + "\nComments: " + comment;
    }

}
