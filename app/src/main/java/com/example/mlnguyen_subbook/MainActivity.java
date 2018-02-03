package com.example.mlnguyen_subbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
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

        // setOnItemClickListener taken from https://www.youtube.com/watch?v=wSCIuIbS-nk
        // 2018-1-31
        // Handles when SubList(ListView) clicked, moving to SubscriptionEditActivity allowing user to edit or delete the selected subscription
        SubList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("ListView",Integer.toString(i));
                Log.i("testing",subscriptionList.get(i).toString());
                String name = subscriptionList.get(i).getName();
                String charge = Float.toString(subscriptionList.get(i).getCharge());
                String comment = subscriptionList.get(i).getComment();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
                String date = df.format(subscriptionList.get(i).getDate());

                Intent intent = new Intent(view.getContext(),SubscriptionEditActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("charge",charge);
                intent.putExtra("comment",comment);
                intent.putExtra("date",date);
                intent.putExtra("index",i);
                startActivityForResult(intent, 1015);

            }
        });
        Button clearButton = (Button) findViewById(R.id.clear);
        clearButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                subscriptionList.clear();
                totalCharge = 0.00f;
                adapter.notifyDataSetChanged();
                chargeView.setText(String.format("$%.2f",totalCharge));
                saveInFile();

            }
        });
        Button exitButton = (Button) findViewById(R.id.exit);
        exitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                System.exit(0);

            }
        });


        //Starts totalCharge which will be $0.00 and updates chargeView(textView)
        chargeView = (TextView) findViewById(R.id.chargeView);
        chargeView.setText(String.format("$%.2f", totalCharge));

    }
    // Handles when Add Subscription button pressed moving to SubscriptionAddActivity allowing user to add a new subscription
    public void addButton(View view)
    {

        Intent intent = new Intent(this, SubscriptionAddActivity.class);
        startActivityForResult(intent, 1014);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        // Depending on requestCode retrieved
        switch(requestCode) {
            //requestCode for SubscriptionAddActivity
            case (1014) : {
                if (resultCode == Activity.RESULT_OK) {

                    String name = data.getStringExtra("name");
                    String charge = data.getStringExtra("charge");
                    Float chargeFloat = Float.parseFloat(charge);
                    String comment = data.getStringExtra("comment");

                    Date date =  new Date();
                    try {
                        date = df.parse(data.getStringExtra("date"));
                    }catch (Exception e){

                    }
                    Subscription subscription = new Subscription(name,date,chargeFloat,comment);
                    //Log.i("test",subscription.toString());
                    subscriptionList.add(subscription);
                    totalCharge += chargeFloat;
                    //Log.i("charges", Float.toString(totalCharge));
                    chargeView.setText(String.format("$%.2f",totalCharge));
                    adapter.notifyDataSetChanged();
                    saveInFile();

                }
                break;
            }
            // requestCode for SubscriptionEditActivity
            case (1015) : {
                //Checks resultCode Result_OK = Subscription modified, changes the subscription in the subscriptionList(arrayList) and updates the SubList(ListView)
                if (resultCode == Activity.RESULT_OK){
                    String name = data.getStringExtra("name");
                    String charge = data.getStringExtra("charge");
                    Float chargeFloat = Float.parseFloat(charge);
                    String comment = data.getStringExtra("comment");
                    Integer index = data.getIntExtra("index",-1);
                    Date date = new Date();
                    try {
                        date = df.parse(data.getStringExtra("date"));
                    }catch (Exception e){

                    }
                    Subscription subscription = new Subscription(name,date,chargeFloat,comment);
                    totalCharge -= subscriptionList.get(index).getCharge();
                    subscriptionList.set(index,subscription);
                    totalCharge += chargeFloat;
                    chargeView.setText(String.format("$%.2f",totalCharge));
                    adapter.notifyDataSetChanged();
                    saveInFile();


                }
                //Checks resultCode 2 = Subscription deleted, removes the subscription from the subscriptionList(arrayList) and updates SubList(ListView)
                else if (resultCode == 2){
                    int index = data.getIntExtra("index",-1);
                    totalCharge -= subscriptionList.get(index).getCharge();
                    chargeView.setText(String.format("$%.2f",totalCharge));
                    subscriptionList.remove(index);
                    adapter.notifyDataSetChanged();
                    saveInFile();

                }
            }
        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        Log.i("LifeCycle --->", "onStart is called");
        loadFromFile();
        adapter = new ArrayAdapter<Subscription>(this, R.layout.list_item, subscriptionList);

        SubList.setAdapter(adapter);
    }

    private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            totalCharge = 0.00f;
            // Taken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2018-01-25
            Type listType = new TypeToken<ArrayList<Subscription>>() {
            }.getType();
            subscriptionList = gson.fromJson(in, listType);
            for(int i=0; i<subscriptionList.size();i++){
                totalCharge += subscriptionList.get(i).getCharge();

            }
            chargeView.setText(String.format("$%.2f",totalCharge));


        } catch (FileNotFoundException e) {
            subscriptionList = new ArrayList<Subscription>();
            totalCharge = 0.00f;
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(subscriptionList, out);
            out.flush();


        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}