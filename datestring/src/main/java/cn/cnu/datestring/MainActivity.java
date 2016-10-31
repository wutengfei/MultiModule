package cn.cnu.datestring;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Date date=new Date();
        System.out.println(date.toString());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        String dateString=simpleDateFormat.format(date);
        System.out.println(dateString);
        Date date2=new Date();
        try {
            date2=simpleDateFormat.parse(date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date2);

    }

}
