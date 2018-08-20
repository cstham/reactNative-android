package inducesmile.com.androidstaggeredgridlayoutmanager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jacksonandroidnetworking.JacksonParserFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import inducesmile.com.androidstaggeredgridlayoutmanager.Verification.HttpCall;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends FragmentActivity {

    //private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    //public static List<FrameObjects> gaggeredList;
    //==================================================================================================
    //CONTEXT CHECK
    public static Context mContext;
    public static Context currentObj;
    //==================================================================================================
    //tab layout variables
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private String[] mTitles = {"HOME", "FAVOURITES", "CAMERA", "MESSAGES", "CONTACTS"};
    /*
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect,
            R.mipmap.tab_contact_unselect, R.mipmap.tab_more_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_speech_select,
            R.mipmap.tab_contact_select, R.mipmap.tab_more_select};
*/
    private int[] mIconUnselectIds = {
            R.mipmap.i_home, R.mipmap.i_love,
            R.mipmap.i_camera, R.mipmap.i_chat, R.mipmap.i_ppl};

    private int[] mIconSelectIds = {
            R.mipmap.i_home, R.mipmap.i_love,
            R.mipmap.i_camera, R.mipmap.i_chat, R.mipmap.i_ppl};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private View mDecorView;
    private ViewPager mViewPager;

    private CommonTabLayout mTabLayout_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        calculateActionBarHeight();
        setContentView(R.layout.activity_main_with_tabs);

        mContext = getApplicationContext();
        currentObj = this;
        //==========================================================================================
        //android networking libraries initialization

        //AndroidNetworking.initialize(getApplicationContext());

        //use jackson parser instead of gson (by default = gson)
        //AndroidNetworking.setParserFactory(new JacksonParserFactory());

        //==========================================================================================
/*
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        recyclerView.setNestedScrollingEnabled(true);  //pls check this!!!

        recyclerView.setHasFixedSize(true);
        //recyclerView.setItemAnimator(null);

        gaggeredList = getListItemData();

        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        SolventRecyclerViewAdapter rcAdapter = new SolventRecyclerViewAdapter(MainActivity.this, gaggeredList);
        recyclerView.setAdapter(rcAdapter);
        */
        //==========================================================================================
        //TAB LAYOUT
        mFragments.add(MainFragment.getInstance(mTitles[0]));               //home
        mFragments.add(SimpleCardFragment.getInstance(mTitles[1]));         //favourites
        mFragments.add(SimpleCardFragment.getInstance(mTitles[2]));         //camera
        mFragments.add(SimpleCardFragment.getInstance(mTitles[3]));         //messages
        mFragments.add(SimpleCardFragment.getInstance(mTitles[4]));         //contacts


        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        mDecorView = getWindow().getDecorView();
        mViewPager = ViewFindUtils.find(mDecorView, R.id.vp_2);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        mTabLayout_3 = ViewFindUtils.find(mDecorView, R.id.tl_3);


        tab_behaviour();
        //==========================================================================================







//=================================================================================================
    }

    private void tab_behaviour() {
        mTabLayout_3.setTabData(mTabEntities);
        mTabLayout_3.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);

                //this link is very important to preserve screen state during tab transitions
                mViewPager.setOffscreenPageLimit(4);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {

                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position==1){
                    //AppBarLayout appBarLayout = (AppBarLayout)findViewById(R.id.appBarLayout);
                    //appBarLayout.setExpanded(false, true);
                }



            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout_3.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //use this to decide which page to show
        mViewPager.setCurrentItem(0);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }


        private void calculateActionBarHeight(){
            // Calculate ActionBar height
            TypedValue tv = new TypedValue();
            if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
            {
                int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());

                System.out.println("action bar height lol:    " + actionBarHeight);
            }

        }






}
/*

        //orientation 0 = scroll horizontally, 1 = scroll vertically
        //if 0, objects added top-down, if 1, objects added left-right
        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        //==========================================================================================
        for(int i = 0; i < 10; i++){

            listViewItems.add(new ItemObjects("Gourmet Salted Egg Yolk Potato Crisp x 3 x 125g", R.drawable.item_001, "RM 118.00"));

        }
*/