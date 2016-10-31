package cn.cnu.httppractice;

/**
 * Created by dell on 2016/10/31.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
