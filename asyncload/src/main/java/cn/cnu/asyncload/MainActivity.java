package cn.cnu.asyncload;

import android.os.Bundle;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lv_main);
        String URL1 = "http://www.imooc.com/api/teacher?type=4&num=30";
        new MyAsyncTask().execute(URL1);
    }

    class MyAsyncTask extends AsyncTask<String, Void, List<NewsBean.DataBean>> {

        @Override
        protected List<NewsBean.DataBean> doInBackground(String... params) {
            return getJsonData(params[0]);
        }

        @Override
        protected void onPostExecute(List<NewsBean.DataBean> beanDatas) {
            super.onPostExecute(beanDatas);
            NewsAdapter adapter = new NewsAdapter(MainActivity.this, beanDatas, listView);
            listView.setAdapter(adapter);
        }
    }

    /**
     * 通过URL获取json字符串
     *
     * @param url
     * @return List<NewsBean.DataBean>
     */
    private List<NewsBean.DataBean> getJsonData(String url) {
        String jsonString = null;
        try {
            jsonString = readStream(new URL(url).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        if(jsonString==null||"".equals(jsonString))return null;//如果jsonString返回有问题，就不解析了。
        NewsBean newsBean = gson.fromJson(jsonString, NewsBean.class);//需要将Gosn的包导入进project中。
        return newsBean.getData();
    }

    /**
     * 怎样将一个url（也可以说是一个InputStream）转换为一个json字符串信息。
     *
     * @param is
     * @return String
     */
    private String readStream(InputStream is) {
        String result = "";
        InputStreamReader isr;
        String line;
        try {
            isr = new InputStreamReader(is, "utf-8");//字节流转化为字符流
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}