package com.example.mlnguyen_subbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
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


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptionedit);
        nameView = (EditText) findViewById(R.id.editName);
        chargeView = (EditText) findViewById(R.id.editCharge);
        dateView = (EditText) findViewById(R.id.editDate);
        commentView = (EditText) findViewById(R.id.editComment);
        nameView.setText(name);
        chargeView.setText(String.format("%.2f",Float.parseFloat(charge)));
        commentView.setText(comment);


    }

    public void saveSubE(View view)
    {
        String nameTextString = nameView.getText().toString();
        String chargeTextString = chargeView.getText().toString();
        String dateTextString = dateView.getText().toString();
        DateFormat df = new SimpleDateFormat("mm-dd-yyyy");
        Date dateTextDate = new Date();
        try {
            Date dateTextTest = df.parse(dateTextString);
        }
        catch (Exception e){

        }
        Intent intent = getIntent();
        Integer index = intent.getIntExtra("index",-1);
        String commentTextString = commentView.getText().toString();
        Intent returnIntent = new Intent();
        //https://stackoverflow.com/questions/17996221/how-to-receive-multiple-values-using-an-intent
        returnIntent.putExtra("name",nameTextString.toString());
        returnIntent.putExtra("charge",chargeTextString.toString());
        returnIntent.putExtra("comment",commentTextString.toString());
        returnIntent.putExtra("date",dateTextDate.toString());
        returnIntent.putExtra("index",index);
        //returnIntent.putExtra("index", index);
        setResult(RESULT_OK,returnIntent);
        finish();

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
