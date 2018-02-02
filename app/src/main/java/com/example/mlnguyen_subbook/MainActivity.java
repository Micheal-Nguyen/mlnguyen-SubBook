package com.example.mlnguyen_subbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity  {

    private static final String FILENAME = "file.sav";
    private ArrayList<Subscription> subscriptionList;
    private ArrayAdapter<Subscription> adapter;
    private ListView SubList;
    private TextView chargeView;
    private Float totalCharge = 0.00f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SubList = findViewById(R.id.SubList);
        subscriptionList = new ArrayList<Subscription>();
        adapter = new ArrayAdapter<Subscription>(this, R.layout.list_item, subscriptionList);
        SubList.setAdapter(adapter);

        //https://www.youtube.com/watch?v=wSCIuIbS-nk
        SubList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("ListView",Integer.toString(i));
                Log.i("testing",subscriptionList.get(i).toString());
                String name = subscriptionList.get(i).getName();
                String charge = Float.toString(subscriptionList.get(i).getCharge());
                String comment = subscriptionList.get(i).getComment();
                totalCharge -= subscriptionList.get(i).getCharge();
                chargeView.setText(String.format("%.2f",totalCharge));
                Intent intent = new Intent(view.getContext(),SubscriptionEditActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("charge",charge);
                intent.putExtra("comment",comment);
                intent.putExtra("index",i);
                startActivityForResult(intent, 1015);

            }
        });



        chargeView = (TextView) findViewById(R.id.chargeView);
        chargeView.setText(String.format("$%.2f", totalCharge));

    }
    public void sendMessage(View view)
    {

        Intent intent = new Intent(this, SubscriptionAddActivity.class);
        startActivityForResult(intent, 1014);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (1014) : {
                if (resultCode == Activity.RESULT_OK) {

                    String name = data.getStringExtra("name");
                    String charge = data.getStringExtra("charge");
                    Float chargeFloat = Float.parseFloat(charge);
                    String comment = data.getStringExtra("comment");
                    Date date = new Date();
                    Subscription subscription = new Subscription(name,date,chargeFloat,comment);
                    //Log.i("test",subscription.toString());
                    subscriptionList.add(subscription);
                    totalCharge += chargeFloat;
                    //Log.i("charges", Float.toString(totalCharge));
                    chargeView.setText(String.format("$%.2f",totalCharge));
                    adapter.notifyDataSetChanged();

                }
                break;
            }
            case (1015) : {
                if (resultCode == Activity.RESULT_OK){
                    String name = data.getStringExtra("name");
                    String charge = data.getStringExtra("charge");
                    Float chargeFloat = Float.parseFloat(charge);
                    String comment = data.getStringExtra("comment");
                    Integer index = data.getIntExtra("index",-1);
                    Date date = new Date();
                    Subscription subscription = new Subscription(name,date,chargeFloat,comment);
                    subscriptionList.set(index,subscription);
                    totalCharge += chargeFloat;
                    chargeView.setText(String.format("$%.2f",totalCharge));
                    adapter.notifyDataSetChanged();


                }
                else if (resultCode == 2){
                    Integer index = data.getIntExtra("index",-1);
                    Log.i("testing",index.toString());
                    subscriptionList.remove(index);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}