package com.example.mlnguyen_subbook;




import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by kio22 on 2018-01-31.
 */

public class Subscription {


    private String name;
    private Date date;
    private Float charge;
    private String comment;

    //Sets the private variables of Subscription
    Subscription(String name, Date date, float charge, String comment) {
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

    public Float getCharge() {
        return charge;
    }

    public void setCharge(Float charge){
        this.charge = charge;
    }

    public String getComment(){
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    //Converts the attributes of subscription to a string when needed.
    public String toString(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        return String.format("%-17s",df.format(date))  + String.format("%-33s",name) + String.format("$%.2f",charge) + "\nComments: " + comment;
    }

}
