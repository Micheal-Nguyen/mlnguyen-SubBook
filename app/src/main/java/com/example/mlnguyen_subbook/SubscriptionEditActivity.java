package com.example.mlnguyen_subbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
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

        //Retrieves the (EditText) and sets them to the subscription that was clicked in the listView on main activity
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

    //Save button
    public void saveSubE(View view)
    {
        String nameTextString = nameView.getText().toString();
        String chargeTextString = chargeView.getText().toString();
        String dateTextString = dateView.getText().toString();
        String commentTextString = commentView.getText().toString();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateTextDate;

        //Validation, checks and puts out an error if anything wrong according to specs and such.
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
                dateTextDate = new Date();
                dateTextString = df.format(dateTextDate);
            }
            else{
                //Checks if the date exists and is before current date
                try {
                    df.setLenient(false);
                    dateTextDate = df.parse(dateTextString);
                    if(dateTextDate.after(new Date())){
                        dateView.requestFocus();
                        dateView.setError("Subscription date is in the future");
                        choice = 2;
                    }

                } catch (Exception e){
                    dateView.requestFocus();
                    dateView.setError("Incorrect date format");
                    choice = 2;
                }
            }

            //If date doesn't exist or in future, case 2 else case 1.
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
                    returnIntent.putExtra("date", dateTextString);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                case 2:
                    break;
            }
        }

    }

    //delete button, deletes subscription
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
