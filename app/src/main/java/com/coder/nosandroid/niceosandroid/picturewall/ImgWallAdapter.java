package com.coder.nosandroid.niceosandroid.picturewall;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import androidx.collection.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.coder.nosandroid.niceosandroid.R;
import com.coder.nosandroid.niceosandroid.Utilities.ImageDataSet;
import com.coder.nosandroid.niceosandroid.Utilities.ImgInfo;
import com.coder.nosandroid.niceosandroid.Utilities.LogUtils;

public class ImgWallAdapter extends BaseAdapter implements AbsListView.OnScrollListener {

    private Context context;
    private ImageDataSet<ImgInfo> imginfos;
    private ImgInfo imginfo;
    //缓存当前图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。
    private LruCache<String, Bitmap> mMemoryCache;
    private GridView mImgWall;
    private Set<BitmapWorkerTask> taskCollection;
    private boolean isScroll = false;
    private boolean isFirstLoad = true;
    private final String TAG = "ImgWallAdapter";
    private int mFirstVisibleItem;
    private int mVisibleItemCount;



    public ImgWallAdapter(Context context, ImageDataSet<ImgInfo> imginfos, GridView ImgWall) {
        this.context = context;
        this.imginfos = imginfos;
        mImgWall = ImgWall;
        taskCollection = new HashSet<BitmapWorkerTask>();
        // 获取应用程序最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 2;
        // 设置图片缓存大小为程序最大可用内存的1/2
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
        mImgWall.setOnScrollListener(this);
    }

    public int getCount() {
        return (null != imginfos)?imginfos.getCount() : 0;
    }


    public Object getItem(int position) {
        return position;
    }


    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        imginfo = imginfos.getItemAt(position);
        View view;
        ImageView photo;
        if(convertView == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.pic_layout, null);
        }else{
            view = convertView;
        }
        photo = (ImageView)view.findViewById(R.id.photo);
        photo.setTag(position);
        return view;
    }


    public Bitmap LoadImage(ImgInfo data) {
        if (data == null) {
            return null;
        }

        Bitmap bitmap = null;
        String url = String.valueOf(data);
        bitmap = getBitmapFromMemoryCache(url);

        if (bitmap == null) {
            Uri uri = data.getImageUri();
            bitmap = getBitmapFromMemoryCache(uri,context);
            //将Bitmap 加入内存缓存
            addBitmapToMemoryCache(url, bitmap);
        }
        return bitmap;
    }

    private Bitmap getBitmapFromMemoryCache(Uri uri,Context context) {
        // TODO Auto-generated method stub
        ContentResolver cr = context.getContentResolver();
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(cr.openInputStream(uri), null, options);
            options.inSampleSize = 4;//图片宽高都为原来的二分之一，即图片为原来的四分之一
            options.inJustDecodeBounds = false;
            options.inDither = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri), null, options);
            return bitmap;

        }catch (FileNotFoundException exp)
        {
            exp.printStackTrace();
            return null;
        }
    }


    /**
     * 从LruCache中获取一张图片，如果不存在就返回null。
     */
    public Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    /**
     * 添加Bitmap到内存缓存
     */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null && bitmap != null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    private void cancelAllTasks() {
        if (taskCollection != null) {
            for (BitmapWorkerTask task : taskCollection) {
                task.cancel(false);
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        int position,visibleCnt;
        if( scrollState == SCROLL_STATE_IDLE) {
            LogUtils.d(TAG,"onScrollStateChanged","GridView is in idle,position is " + mFirstVisibleItem);
            for(position = mFirstVisibleItem,visibleCnt = mVisibleItemCount; visibleCnt > 0; visibleCnt--,position++) {
                ImageView photo = (ImageView) mImgWall.findViewWithTag(position);
                loadBitmaps(position,photo);
            }
        } else {
            LogUtils.d(TAG,"onScrollStateChanged","GridView is Scroll and cancel All Tasks");
            cancelAllTasks();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int position;
        int visibleCnt;
        mFirstVisibleItem = firstVisibleItem;
        mVisibleItemCount = visibleItemCount;

        if(isFirstLoad) {
            for(position = mFirstVisibleItem,visibleCnt = mVisibleItemCount; visibleCnt > 0; visibleCnt--,position++) {
                ImageView photo = (ImageView) mImgWall.findViewWithTag(position);
                loadBitmaps(position,photo);
            }
            if(mVisibleItemCount != 0)
                isFirstLoad = false;
        }
    }


    public class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {

        int positionTag;
        @Override
        protected Bitmap doInBackground(Integer... params) {
            // TODO Auto-generated method stub
            positionTag = params[0];
            ImgInfo imgInfo = imginfos.getItemAt(positionTag);
            // 从SD卡获取bitmap
            Bitmap bitmap = LoadImage(imgInfo);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            // 根据Tag找到相应的ImageView控件，将下载好的图片显示出来。
            ImageView photo = (ImageView) mImgWall.findViewWithTag(positionTag);

            if (photo != null && bitmap != null) {
                photo.setImageBitmap(bitmap);
            }
            //移除任务
            taskCollection.remove(this);
        }

    }


    private void loadBitmaps(int position, ImageView imageView) {
        ImgInfo data = imginfos.getItemAt(position);
        Bitmap bitmap = getBitmapFromMemoryCache(String.valueOf(data));
        if (bitmap == null) {
            imageView.setImageResource(R.drawable.empty_photo);
            BitmapWorkerTask task = new BitmapWorkerTask();
            taskCollection.add(task);
            task.execute(position);
        } else {
            if (imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

}
