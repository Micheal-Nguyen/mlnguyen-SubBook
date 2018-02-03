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
 * Created by kio22 on 2018-02-01.
 */

public class SubscriptionEditActivity extends AppCompatActivity {


    private EditText nameView;
    private EditText chargeView;
    private EditText dateView;
    private EditText commentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String charge = intent.getStringExtra("charge");
        String comment = intent.getStringExtra("comment");
        String date = intent.getStringExtra("date");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptionedit);
        nameView = (EditText) findViewById(R.id.editName);
        chargeView = (EditText) findViewById(R.id.editCharge);
        dateView = (EditText) findViewById(R.id.editDate);
        commentView = (EditText) findViewById(R.id.editComment);
        nameView.setText(name);
        chargeView.setText(String.format("%.2f",Float.parseFloat(charge)));
        commentView.setText(comment);
        dateView.setText(date);


    }

    public void saveSubE(View view)
    {
        String nameTextString = nameView.getText().toString();
        String chargeTextString = chargeView.getText().toString();
        String dateTextString = dateView.getText().toString();
        String commentTextString = commentView.getText().toString();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        Date dateTextDate;

        if(nameTextString.length()==0){
            nameView.requestFocus();
            nameView.setError("Subscription Name is required");
        }
        else if(nameTextString.length()>=20){
            nameView.requestFocus();
            nameView.setError("Subscription Name over 20 characters");
        }
        else if(chargeTextString.length() == 0) {
            chargeView.requestFocus();
            chargeView.setError("Subscription Monthly charge is required");
        }
        else if(commentTextString.length()>30){
            commentView.requestFocus();
            commentView.setError("Comments over 30 characters");
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
                    dateView.requestFocus();
                    dateView.setError("Incorrect date format");
                    choice = 2;
                }
            }
            switch(choice) {
                case 1:
                    Intent intent = getIntent();
                    Integer index = intent.getIntExtra("index",-1);
                    Intent returnIntent = new Intent();
                    // https://stackoverflow.com/questions/17996221/how-to-receive-multiple-values-using-an-intent
                    // 2018-02-01
                    returnIntent.putExtra("name", nameTextString);
                    returnIntent.putExtra("charge", chargeTextString);
                    returnIntent.putExtra("comment", commentTextString);
                    returnIntent.putExtra("index",index);
                    Log.i("date",dateTextString);
                    returnIntent.putExtra("date", dateTextString);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                case 2:
                    break;
            }
        }

    }

    public void deleteSub (View view)
    {
        Intent intent = getIntent();
        Integer index = intent.getIntExtra("index",-1);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("index",index);
        setResult(2, returnIntent);
        finish();

    }

    public void onCancelE(View view){
        finish();
    }
}
