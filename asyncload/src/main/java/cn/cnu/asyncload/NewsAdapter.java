package cn.cnu.asyncload;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
 
import java.util.List;
 
 
public class NewsAdapter extends BaseAdapter implements AbsListView.OnScrollListener{
    private List<NewsBean.DataBean> newsBeans;
    private LayoutInflater mInflater;
    private ImageLoader imageLoader;
    private int mStart,mEnd;
    public static String[] URLS;//存放要加载的图片的url
    private boolean isFirst = false;//控制第一次进入listview的时候加载数据
    public NewsAdapter(Context context, List<NewsBean.DataBean> newsBeans, ListView listView) {//初始化数据
        this.newsBeans = newsBeans;
        this.mInflater = LayoutInflater.from(context);
        imageLoader = new ImageLoader(listView);// 确保只有一个LruCache
        
        URLS = new String[newsBeans.size()];
        for (int i=0;i<newsBeans.size();i++){//获取newsBeans中的图片的url
            URLS[i] = newsBeans.get(i).getPicSmall();
            
        }
        isFirst = true;
        listView.setOnScrollListener(this);
    }
 
    @Override
    public int getCount() {
        return newsBeans.size();
    }
 
    @Override
    public Object getItem(int position) {
        return newsBeans.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;//利用viewholder进行优化，这一点也是写类似listview的adapter的getView方法的一个模板。
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_layout,null);
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.textView1);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.textView2);
            convertView.setTag(viewHolder);
        }else{//如果convertView不为空就不用再次去加载布局了，因为加载布局耗时很长，造成listview的卡顿
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivIcon.setImageResource(R.mipmap.ic_launcher);
        String url = newsBeans.get(position).getPicSmall();
        viewHolder.ivIcon.setTag(url);//将url作为tag
        imageLoader.showImageByAsyncTask(viewHolder.ivIcon,url);
        viewHolder.tvTitle.setText(newsBeans.get(position).getName());
        viewHolder.tvContent.setText(newsBeans.get(position).getDescription());
 
        return convertView;
    }
 
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {//滑动状态改变时调用
        if(scrollState==SCROLL_STATE_IDLE){//停止滚动，加载可见项
            imageLoader.loadImage(mStart,mEnd);
        }else{//停止加载
            imageLoader.cancelAllTask();
        }
    }
 
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {//整个滑动的时候都会调用
        mStart = firstVisibleItem;//第一个可见元素
        mEnd =firstVisibleItem+visibleItemCount;//最后一个可见元素=第一个可见元素+可见元素的数量
        if(isFirst && visibleItemCount > 0){//第一次加载的时候调用，显示图片
            imageLoader.loadImage(mStart,mEnd);
            isFirst=false;
        }
    }
 
    class ViewHolder{
        public TextView tvTitle;
        public TextView tvContent;
        public ImageView ivIcon;
    }
}