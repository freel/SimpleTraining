package com.freel.simpletraining;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper.connect();
        Map<String, String> map = new HashMap<String, String>();
        map = dbHelper.getLastUserData();

        EditText height = (EditText) findViewById(R.id.heightEdit);
        EditText weight = (EditText) findViewById(R.id.weightEdit);
        height.setText(map.get("height"));
        weight.setText(map.get("weight"));

    }

    public void onStartTrainingClick(View view){
        Intent intent = new Intent(MainActivity.this, TrainingActivity.class);

        saveUserData();
        dbHelper.disconnect();
        startActivity(intent);

    }

    private void saveUserData(){
        EditText height = (EditText) findViewById(R.id.heightEdit);
        EditText weight = (EditText) findViewById(R.id.weightEdit);
        dbHelper.saveUserData(Integer.valueOf(height.getText().toString()),Integer.valueOf(weight.getText().toString()));
    }


}
