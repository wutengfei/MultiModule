package cn.cnu.parsejson;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;

    private TextView txt1;
    private String baseUrl = "http://172.19.203.88:8080/package";
    //  private String baseUrl = "http://www.baidu.com";

    private String str = "";
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            //  返回的字符串 直接是个数组
            // [{"devid":"1234567800","latitude":"29.4963","longitude":"116.189","postime":"2014-06-10 12:13:00"},
            // {"devid":"1234567832","latitude":"29.4943","longitude":"1161.129","postime":"2014-06-11 12:13:00"}]
            if (msg.what == 1) {
                Log.v("zms", "使用httpclient,返回的json");
                try {
                    JSONArray jsonArray = new JSONArray(String.valueOf(msg.obj));
                    txt1.setText(" ");
                    str = "httpclient:";
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
                        str = str + "第" + i + "个,devid:" + jsonObject2.getString("devid") + "维度:" + jsonObject2.getString("latitude") + "\n";
                        txt1.setText(str);
                        Log.v("zms", str);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else if (msg.what == 2) {
                Log.v("zms", "使用httpurlconnection返回的json");
                try {
                    JSONArray jsonArray = new JSONArray(String.valueOf(msg.obj));
                    txt1.setText(" ");
                    str = "urlconnect:";
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
                        str = str + "第" + i + "个,devid:" + jsonObject2.getString("devid") + "维度:" + jsonObject2.getString("latitude") + "\n";
                        txt1.setText(str);
                        Log.v("zms", str);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            super.handleMessage(msg);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt1 = (TextView) findViewById(R.id.textView1);


        //最简单 的 直接一个对象
        btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt1.setText("");
                str = "";
                try {
                    // {"username":"zms",age:23,"addr","from china"};
                    String json = "{\"username\":\"zms\",\"age\":43,\"addr\":\"江西省高安市村前镇\"}";
                    JSONObject jsonObject2 = new JSONObject(json);
                    str = "名字:" + jsonObject2.getString("username") + "年龄:" + jsonObject2.getString("age") + jsonObject2.getString("addr") + "\n";
                    txt1.setText(str);
                    Log.v("zms", str);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

        //对象里有数组
        btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt1.setText("");
                str = "";
                try {
                    //{"username":"zms",age:11,"jicheng":[{"zhengshu":"PMP","date":"2011年"},{"zhengshu":"信息系统项目管理师","date","2012年"}],"addr":"江西"}
                    // 这种也属于 对象里有数组   {"json":[{"username":"zms","date":"2011年"},{"username":"ivy","date","2012年"}]}
                    String json = "{\"username\":\"张木生\",age:11,\"jicheng\":[{\"zhengshu\":\"PMP\",\"date\":\"2011年\"},{\"zhengshu\":\"信息系统项目管理师\",\"date\":\"2012年\"}],\"addr\":\"江西\"}";

                    JSONObject jsonObject2 = new JSONObject(json);
                    str = "名字:" + jsonObject2.getString("username");
                    str = str + "工龄:" + jsonObject2.getString("age") + "证书:";
                    JSONArray jsonArray = jsonObject2.getJSONArray("jicheng");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObjectSon = (JSONObject) jsonArray.opt(i);
                        str = str + jsonObjectSon.getString("zhengshu") + "年份：" + jsonObjectSon.getString("date");
                    }
                    str = str + "籍贯" + jsonObject2.getString("addr");
                    txt1.setText(str);
                    Log.v("zms", str);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

        //直接一个数组
        btn4 = (Button) findViewById(R.id.button4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("zms", "响应4");
                txt1.setText("");
                str = "";
                //  返回的字符串 直接是个数组
                // [{"devid":"1234567800","latitude":"29.4963","longitude":"116.189"},{"devid":"1234567832","latitude":"29.4943","longitude":"1161.129"}]
                String json = "[{\"devid\":\"1234567800\",\"latitude\":\"29.4963\",\"longitude\":\"116.189\"},{\"devid\":\"1234567832\",\"latitude\":\"29.4943\",\"longitude\":\"1161.129\"}]";
                try {
                    JSONArray jsonArray = new JSONArray(json);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
                        str = str + "第" + i + "个,devid:" + jsonObject2.getString("devid") + "维度:" + jsonObject2.getString("latitude") + "经度:" + jsonObject2.getString("longitude") + "\n";

                    }
                    txt1.setText(str);
                    Log.v("zms", str);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }
        });

        //数组里有数组
        btn5 = (Button) findViewById(R.id.button5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt1.setText("");
                str = "";
                //  返回的字符串 直接是个数组
   /*   [
      {"devid":"1234567800","gps":[{"time":"2014-11-12","latitude":"29.4963","longitude":"116.189"},{"time":"2014-11-12","latitude":"29.4963","longitude":"116.189" }],"devname":"赣01"},
       {"devid":"1234567800","gps":[{"time":"2014-11-12","latitude":"29.4963","longitude":"116.189"},{"time":"2014-11-12","latitude":"29.4963","longitude":"116.189" }],"devname":"赣92"},
       {"devid":"1234567800","gps":[{"time":"2014-11-12","latitude":"29.4963","longitude":"116.189"},{"time":"2014-11-12","latitude":"29.4963","longitude":"116.189" }],"devname":"赣43"},
       ], */
                String json = "[" +
                        "{\"devid\":\"1234567800\",\"gps\":[{\"time\":\"2014-11-12\",\"latitude\":\"29.4963\",\"longitude\":\"116.189\"},{\"time\":\"2014-11-12\",\"latitude\":\"29.4963\",\"longitude\":\"116.189\" }],\"devname\":\"赣01\"}," +
                        " {\"devid\":\"1234567800\",\"gps\":[{\"time\":\"2014-11-12\",\"latitude\":\"29.4963\",\"longitude\":\"116.189\"},{\"time\":\"2014-11-12\",\"latitude\":\"29.4963\",\"longitude\":\"116.189\" }],\"devname\":\"赣92\"}," +
                        " {\"devid\":\"1234567800\",\"gps\":[{\"time\":\"2014-11-12\",\"latitude\":\"29.4963\",\"longitude\":\"116.189\"},{\"time\":\"2014-11-12\",\"latitude\":\"29.4963\",\"longitude\":\"116.189\" }],\"devname\":\"赣43\"}" +
                        "]";
                try {
                    JSONArray jsonArray = new JSONArray(json);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String tpstr = "";
                        JSONObject jsonObject5 = (JSONObject) jsonArray.opt(i);
                        str = str + "第" + i + "条船，设备号devid:" + jsonObject5.getString("devid");
                        JSONArray jsonArray5 = jsonObject5.getJSONArray("gps");
                        for (int j = 0; j < jsonArray5.length(); j++) {
                            JSONObject jsonOb5Son = (JSONObject) jsonArray5.opt(j);
                            tpstr = "采集时间:" + jsonOb5Son.getString("time") + "维度" + jsonOb5Son.getString("latitude") + "经度:" + jsonOb5Son.getString("longitude");

                        }
                        str = str + tpstr;


                    }
                    txt1.setText(str);
                    Log.v("zms", str);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }
        });

        //httpclient访问网络返回json
        btn6 = (Button) findViewById(R.id.button6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt1.setText("");
       /*从安卓3.0以后，就不允许在主线程中直接访问网络，必须在子线程中访问
       访问后要修改主线程的UI，需要使用handler通信*/
                accessThread1 mythread1 = new accessThread1();
                mythread1.start();
            }
        });

        //httpurlconnection访问网络返回json
        btn7 = (Button) findViewById(R.id.button7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt1.setText("");
       /*从安卓3.0以后，就不允许在主线程中直接访问网络，必须在子线程中访问
       访问后要修改主线程的UI，需要使用handler通信*/
                accessThread2 mythread2 = new accessThread2();
                mythread2.start();
            }
        });

    }

    private class accessThread1 extends Thread {

        @Override
        public void run() {

            Log.v("zms", "线程accessThread开始");
            Message msg1 = handler.obtainMessage();
            msg1.what = 1;
            msg1.obj = util.getHttpJsonByhttpclient(baseUrl);
            handler.sendMessage(msg1);
            super.run();
        }

    }

    private class accessThread2 extends Thread {

        @Override
        public void run() {

            Log.v("zms", "线程accessThread2开始");

            Message msg2 = handler.obtainMessage();
            msg2.what = 2;
            msg2.obj = util.getHttpJsonByurlconnection(baseUrl);
            handler.sendMessage(msg2);
            super.run();
        }

    }

}