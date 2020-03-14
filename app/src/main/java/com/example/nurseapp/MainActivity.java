package com.example.nurseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText e1,e2,e3,e4;
    String d1,d2,d3,d4, del;
    Button submit;

    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1=(EditText)findViewById(R.id.i1);
        e2=(EditText)findViewById(R.id.i2);
        e4=(EditText)findViewById(R.id.i4);
        e3=(EditText)findViewById(R.id.i3);
        submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d1=e1.getText().toString();
                d2=e2.getText().toString();
                d3=e3.getText().toString();
                d4=e4.getText().toString();
                MyDBHandler handler = new MyDBHandler(MainActivity.this);
                handler.addemp(d1,d2,d3,d4);
                e1.setText("");
                e2.setText("");
                e3.setText("");
                e4.setText("");
            }
        });
    }

    public void loademp(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this);
        //t.setText(dbHandler.load());
    }

   public void goSearch(View view) {
       Intent i = new Intent(getApplicationContext(), SearchActivity.class);
       startActivity(i);
   }


}