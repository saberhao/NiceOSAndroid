package com.coder.nosandroid.niceosandroid.drawableLayoutdemo;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.graphics.Palette;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.MaterialMenuIcon;
import com.coder.nosandroid.niceosandroid.R;
import com.coder.nosandroid.niceosandroid.Utilities.BitmapUtil;

/**
 * Created by saberhao on 2016/1/19.
 */
public class DrawableActivity extends FragmentActivity{
    private DrawerLayout mDrawerLayout;
    private ListView mMenuListView;
    private String[] mMenuTitles;
    /** Material Design style */
    private MaterialMenuIcon mMaterialMenuIcon;
    /** left menu open & close */
    private boolean isDirection_left = false;
    private View currentView;

    final int DISPLAY_SPRING_IMAGE = 0;
    final int DISPLAY_SUMMER_IMAGE = 1;
    final int DISPLAY_AUTUMN_IMAGE = 2;
    final int DISPLAY_WINTER_IMAGE = 3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Holo_Light_DarkActionBar);
        setContentView(R.layout.activity_drawablelayout);
        setTitle("DrawableDemo");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mMenuListView = (ListView) findViewById(R.id.left_drawer);
        this.currentView = mMenuListView;
        mMenuTitles = getResources().getStringArray(R.array.season_array);
        //left drawable
        mMenuListView.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item,mMenuTitles));
        mMenuListView.setOnItemClickListener(new DrawerItemClickListener());

        //set the actionbar enable
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        //set MaterialMenu Icon color
        mMaterialMenuIcon = new MaterialMenuIcon(this,Color.WHITE, MaterialMenuDrawable.Stroke.REGULAR);

        mDrawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                currentView = drawerView;
                if (drawerView == mMenuListView) {
                    mMaterialMenuIcon.setTransformationOffset(MaterialMenuDrawable.AnimationState.BURGER_ARROW, isDirection_left ? 2 - slideOffset : slideOffset);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                if (drawerView == mMenuListView) {
                    isDirection_left = true;
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if (drawerView == mMenuListView) {
                    isDirection_left = false;
                }
            }
        });

        if (savedInstanceState == null) {
            selectItem(0);
        }

    }


    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            selectItem(position);
        }
    }

    /**
     * content fragment switch
     *
     * @param position
     */
    @SuppressWarnings("deprecation")
    private void selectItem(int position) {
        Fragment fragment = new drawableFragment();
        Bundle args = new Bundle();
        Bitmap bitmap = null;
        switch (position) {
            case 0:
                args.putInt("key", DISPLAY_SPRING_IMAGE);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.spring);
                break;
            case 1:
                args.putInt("key", DISPLAY_SUMMER_IMAGE);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.summer);
                break;
            case 2:
                args.putInt("key", DISPLAY_AUTUMN_IMAGE);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.autumn);
                break;
            case 3:
                args.putInt("key", DISPLAY_WINTER_IMAGE);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.winter);
                break;
            default:
                break;
        }
        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment).commit();

        mMenuListView.setItemChecked(position, true);
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch swatch = palette.getDarkVibrantSwatch();
                if(swatch != null)
                {
                    int color = swatch.getRgb();

                    Drawable actionbarDB = BitmapUtil.BitmapToDrawable(BitmapUtil.GetPurColorIV(colorBurn(color)));
                    getActionBar().setBackgroundDrawable(actionbarDB);
                    mMenuListView.setBackgroundColor(color);
                }
            }
        });
        setTitle(mMenuTitles[position]);
        // close the left drawables
        mDrawerLayout.closeDrawer(mMenuListView);
    }

    @SuppressLint("NewApi")
    @Override
    public void onPostCreate(Bundle savedInstanceState,
                             PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mMaterialMenuIcon.syncState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMaterialMenuIcon.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                if (currentView == mMenuListView) {
                    if (!isDirection_left) { // 左边栏菜单关闭时，打开
                        mDrawerLayout.openDrawer(mMenuListView);
                    } else {// 左边栏菜单打开时，关闭
                        mDrawerLayout.closeDrawer(mMenuListView);
                    }
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 颜色加深处理
     *
     * @param RGBValues
     *            RGB的值，由alpha（透明度）、red（红）、green（绿）、blue（蓝）构成，
     *            Android中我们一般使用它的16进制，
     *            例如："#FFAABBCC",最左边到最右每两个字母就是代表alpha（透明度）、
     *            red（红）、green（绿）、blue（蓝）。每种颜色值占一个字节(8位)，值域0~255
     *            所以下面使用移位的方法可以得到每种颜色的值，然后每种颜色值减小一下，在合成RGB颜色，颜色就会看起来深一些了
     * @return
     */
    private int colorBurn(int RGBValues) {
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.rgb(red, green, blue);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}
