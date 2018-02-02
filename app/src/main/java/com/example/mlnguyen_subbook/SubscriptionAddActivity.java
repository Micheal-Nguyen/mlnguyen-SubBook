package com.example.mlnguyen_subbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        Intent returnIntent = new Intent();
        //https://stackoverflow.com/questions/17996221/how-to-receive-multiple-values-using-an-intent
        returnIntent.putExtra("name",nameTextString.toString());
        returnIntent.putExtra("charge",chargeTextString.toString());
        returnIntent.putExtra("comment",commentTextString.toString());
        returnIntent.putExtra("date",dateTextDate.toString());
        setResult(RESULT_OK,returnIntent);
        finish();

    }


    public void onCancel(View view){
        finish();
    }
}
