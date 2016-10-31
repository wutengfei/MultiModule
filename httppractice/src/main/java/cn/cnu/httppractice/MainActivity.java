package cn.cnu.httppractice;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final int SHOW_RESPONSE = 0;
    private String address = "http://www.baidu.com";
    private TextView responseText;
    private Handler handler = new Handler() {
        public void  handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
                    // 在这里进行UI操作，将结果显示到界面上
                    responseText.setText(response);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        responseText = (TextView) findViewById(R.id.textView);
    }

    public void send_request(View v) {
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                // 在这里根据返回内容执行具体的逻辑
                Message message = new Message();
                message.what = SHOW_RESPONSE;
                // 将服务器返回的结果存放到Message中
                message.obj = response;
                handler.sendMessage(message);
            }

            @Override
            public void onError(Exception e) {
                // 在这里对异常情况进行处理
                e.printStackTrace();
            }
        });
    }
}
