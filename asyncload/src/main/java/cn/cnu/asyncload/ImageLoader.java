package cn.cnu.asyncload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;


public class ImageLoader {

    private LruCache<String, Bitmap> cache;//用于缓存图片
    private ListView listView;
    private Set<NewsAsyncTask> mTask;//管理AsyncTask

    public ImageLoader(ListView listView) {//初始化一些数据
        this.listView = listView;
        this.mTask = new HashSet<>();
        int maxMemry = (int) Runtime.getRuntime().maxMemory();//获取当前应用可用的最大内存
        int cacheSize = maxMemry / 4;//以最大的四分之一作为可用的缓存大小
        this.cache = new LruCache<String, Bitmap>(cacheSize) {//初始化LruCache
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();//每次存入缓存的大小，即bitmap的大小
            }
        };

    }

    /**
     * 将内容保存到LruCache
     *
     * @param url
     * @param bitmap
     */
    public void addBitmapToCache(String url, Bitmap bitmap) {
        if (getBitmapFromCache(url) == null) {//如果没有保存的话就保存。
            cache.put(url, bitmap);
        }
    }

    /**
     * 从LruCache中获取bitmap
     *
     * @param url
     * @return Bitmap
     */
    public Bitmap getBitmapFromCache(String url) {
        return this.cache.get(url);
    }


    /**
     * 将图片url转化为bitmap
     *
     * @param urlString
     * @return Bitmap
     */
    public Bitmap getBitmapFromUrl(String urlString) {
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            connection.disconnect();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void showImageByAsyncTask(ImageView imageView, String url) {//改进之后，获取图片的控制权由原来getview改成了滚动状态。
        Bitmap bitmap = getBitmapFromCache(url);//从缓存中获取图片
        if (bitmap == null) {//如果没有就设置默认的图片
            imageView.setImageResource(R.mipmap.ic_launcher);
        } else {//如果有就设置当前的图片
            imageView.setImageBitmap(bitmap);
        }

    }

    private class NewsAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private String url;

        public NewsAsyncTask(String url) {
            this.url = url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = getBitmapFromUrl(url);//获取网络图片
            if (bitmap != null) {
                addBitmapToCache(url, bitmap); //将不在缓存的图片加载的缓存中去
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = (ImageView) listView.findViewWithTag(url);
            if (imageView != null && bitmap != null) {//判断这个url所对应的imageview是否对应，对应的话才设置图片，
                imageView.setImageBitmap(bitmap);
            }

        }
    }

    /**
     * 加载从start到end的所有图片
     *
     * @param start
     * @param end
     */
    public void loadImage(int start, int end) {
        for (int i = start; i < end; i++) {//拿到数组中的图片对应的url
            String url = NewsAdapter.URLS[i];
            Bitmap bitmap = getBitmapFromCache(url);
            if (bitmap == null) {//没有就要去下载
                NewsAsyncTask newsAsyncTask = new NewsAsyncTask(url);
                newsAsyncTask.execute(url);
                mTask.add(newsAsyncTask);
            } else {
                ImageView imageView = (ImageView) listView.findViewWithTag(url);//通过findViewWithTag找到imageview，这个tag就是imageview的url
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    public void cancelAllTask() {
        if (mTask != null) {
            for (NewsAsyncTask task : mTask) {
                task.cancel(false);
            }
        }
    }

}