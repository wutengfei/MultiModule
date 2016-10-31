package com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class M2Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m2);
    }
    public void button(View v){
        Intent intent=new Intent(this,M3Activity.class);
        startActivity(intent);
    }
}
