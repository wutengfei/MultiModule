package com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class M3Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m3);
    }
    public void button(View v){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void quit(View v){
        ActivityController.finishAll();
    }
}