package cn.cnu.serializable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void click(View view){
        Person person=new Person("john",10);
        Intent intent=new Intent(this,Main2Activity.class);
        intent.putExtra("data",person);
        startActivity(intent);
    }
    public void click2(View view){
//        Book book=new Book(111,"china");
//        Intent intent=new Intent(this,Main2Activity.class);
//        intent.putExtra("book",book);
//        startActivity(intent);
    }
}
