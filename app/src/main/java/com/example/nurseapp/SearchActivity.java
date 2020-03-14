package com.example.nurseapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Types;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {
    public static final float NORMAL_BLOOD = new Float(12.8);
    public static final int NORMAL_RATE_MIN = 60;
    public static final int NORMAL_RATE_MAX = 100;

    EditText e1,e2,e3,e4;
    String d1,d2,d3,d4, del;

    Button searchButton;
    Button editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        e1=(EditText)findViewById(R.id.i1);
        e2=(EditText)findViewById(R.id.i2);
        e4=(EditText)findViewById(R.id.i4);
        e3=(EditText)findViewById(R.id.i3);

        searchButton=(Button)findViewById(R.id.search_button);
        editButton=(Button)findViewById(R.id.edit_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d1=e1.getText().toString();
                d2=e2.getText().toString();
                d3=e3.getText().toString();
                d4=e4.getText().toString();
                MyDBHandler handler=new MyDBHandler(SearchActivity.this);

                HashMap<String,String> map = handler.load(d1,d2);

                if (map.isEmpty()) {
                    Toast.makeText(SearchActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SearchActivity.this, "Record Founded", Toast.LENGTH_SHORT).show();
                    e1.setText(map.get(MyDBHandler.NAME));
                    e1.setEnabled(false);

                    e2.setText(map.get(MyDBHandler.SURENAME));
                    e2.setEnabled(false);

                    e3.setText(map.get(MyDBHandler.BLOOD));
                    e3.setVisibility(View.VISIBLE);

                    e4.setText(map.get(MyDBHandler.HEART));
                    e4.setVisibility(View.VISIBLE);

                    if (Float.parseFloat(map.get(MyDBHandler.BLOOD) ) > NORMAL_BLOOD) {
                        e3.setBackgroundColor(getResources().getColor(R.color.colorWarning));
                    }

                    if (Integer.parseInt(map.get(MyDBHandler.HEART) ) < NORMAL_RATE_MIN || Integer.parseInt(map.get(MyDBHandler.HEART) ) > NORMAL_RATE_MAX) {
                        e4.setBackgroundColor(getResources().getColor(R.color.colorWarning));
                    }

                    searchButton.setVisibility(View.GONE);
                    editButton.setVisibility(View.VISIBLE);
                }
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d1=e1.getText().toString();
                d2=e2.getText().toString();
                d3=e3.getText().toString();
                d4=e4.getText().toString();
                MyDBHandler handler=new MyDBHandler(SearchActivity.this);
                handler.edit(d1,d2,d3,d4);
                e1.setText("");
                e2.setText("");
                e3.setText("");
                e4.setText("");

                e1.setEnabled(true);
                e2.setEnabled(true);

                e3.setVisibility(View.GONE);
                e4.setVisibility(View.GONE);

                searchButton.setVisibility(View.VISIBLE);
                editButton.setVisibility(View.GONE);
            }
        });

    }

    public  void delete(View view) {
        d1=e1.getText().toString();
        d2=e2.getText().toString();

        MyDBHandler handler=new MyDBHandler(this);

        handler.deleteUser(d1, d2);

        finish();
    }
}