package com.example.mlnguyen_subbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kio22 on 2018-01-30.
 */

public class SubscriptionEditActivity extends AppCompatActivity {

    private EditText nameText;
    private EditText chargeText;
    private EditText dateText;
    private EditText commentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptionedit);
        nameText = findViewById(R.id.editName);
        chargeText = findViewById(R.id.editCharge);
        dateText = findViewById(R.id.editDate);
        commentText = findViewById(R.id.editComment);

    }

    public void saveSub(View view)
    {
        String nameTextString = nameText.getText().toString();
        String chargeTextString = chargeText.getText().toString();
        Float chargeTextFloat = Float.parseFloat(chargeTextString);
        String dateTextString = dateText.getText().toString();
        DateFormat df = new SimpleDateFormat("mm-dd-yyyy");
        Date dateTextDate = new Date();
        try {
            Date dateTextTest = df.parse(dateTextString);
        }
        catch (Exception e){

        }

        String commentTextString = commentText.getText().toString();

        Subscription subscription = new Subscription(nameTextString,dateTextDate,chargeTextFloat,commentTextString);
        setResult(RESULT_OK);
        Intent intent = new Intent();
        intent.putExtra("return", subscription);
        setResult(Activity.RESULT_OK, intent);
        finish();

    }


    public void onCancel(View view){
        finish();
    }
}
