package com.example.mlnguyen_subbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private ArrayList<Subscription> subscriptionList;
    private ArrayAdapter<Subscription> adapter;
    private ListView SubList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SubList = findViewById(R.id.SubList);
        subscriptionList = new ArrayList<Subscription>();
        adapter = new ArrayAdapter<Subscription>(this, R.layout.list_item, subscriptionList);
        SubList.setAdapter(adapter);

    }



    public void sendMessage(View view)
    {

        Intent intent = new Intent(this, SubscriptionEditActivity.class);
        startActivityForResult(intent, 1014);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Subscription subscription;
        subscription = (Subscription) getIntent().getSerializableExtra("return");
        subscriptionList.add(subscription);
        adapter.notifyDataSetChanged();



    }
}
