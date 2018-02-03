package com.example.mlnguyen_subbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kio22 on 2018-01-30.
 */

public class SubscriptionAddActivity extends AppCompatActivity {

    private EditText nameText;
    private EditText chargeText;
    private EditText dateText;
    private EditText commentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptionadd);
        nameText = findViewById(R.id.editName);
        chargeText = findViewById(R.id.editCharge);
        dateText = findViewById(R.id.editDate);
        commentText = findViewById(R.id.editComment);

    }

    public void saveSub(View view)
    {
        String nameTextString = nameText.getText().toString();
        String chargeTextString = chargeText.getText().toString();
        String dateTextString = dateText.getText().toString();
        String commentTextString = commentText.getText().toString();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        Date dateTextDate;

        if(nameTextString.length()==0){
            nameText.requestFocus();
            nameText.setError("Subscription Name is required");
        }
        else if(nameTextString.length()>=20){
            nameText.requestFocus();
            nameText.setError("Subscription Name over 20 characters");
        }
        else if(chargeTextString.length() == 0) {
            chargeText.requestFocus();
            chargeText.setError("Subscription Monthly charge is required");
        }
        else if(commentTextString.length()>30){
            commentText.requestFocus();
            commentText.setError("Comments over 30 characters");
        }
        else{
            int choice = 1;
            if(dateTextString.length()==0) {
                Log.i("test",dateTextString);
                dateTextDate = new Date();
                dateTextString = dateTextDate.toString();
            }
            else{
                try {
                    Date d = df.parse(dateTextString);
                    Calendar cal = Calendar.getInstance();
                    cal.setLenient(false);
                    cal.setTime(d);
                    cal.getTime();

                } catch (Exception e){
                    dateText.requestFocus();
                    dateText.setError("Incorrect date format");
                    choice = 2;
                }
            }
            switch(choice) {
                case 1:
                Intent returnIntent = new Intent();
                // taken from https://stackoverflow.com/questions/17996221/how-to-receive-multiple-values-using-an-intent
                // 2018-1-2
                returnIntent.putExtra("name", nameTextString);
                returnIntent.putExtra("charge", chargeTextString);
                returnIntent.putExtra("comment", commentTextString);
                Log.i("date",dateTextString);
                returnIntent.putExtra("date", dateTextString);
                setResult(RESULT_OK, returnIntent);
                finish();
                case 2:
                    break;
            }
      }

    }


    public void onCancel(View view){
        finish();
    }
}
