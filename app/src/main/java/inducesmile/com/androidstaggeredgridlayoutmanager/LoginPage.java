package inducesmile.com.androidstaggeredgridlayoutmanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentActivity;
import android.view.View;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import inducesmile.com.androidstaggeredgridlayoutmanager.Verification.HttpCall;


public class LoginPage extends FragmentActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        */
        //Window w = getWindow(); // in Activity's onCreate() for instance
        //w.setStatusBarColor(Color.parseColor("#5499c7"));

        setContentView(R.layout.login_page);

        //==========================================================================================
        String username = "tangkas01";
        String password = "qwe123";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String reqDateTime = sdf.format(new Date());
        System.out.println("dateTime lol: "+reqDateTime);
        //String reqDateTime = currentDateandTime;

        String appSignature = "123456";

        String securityToken = convertPassMd5(username + password + reqDateTime +appSignature);
        System.out.println("MD5 lol: "+securityToken);

        HttpCall.postLoginData(username, password, reqDateTime, securityToken);








    }

    private static String convertPassMd5(String pass) {
        String password = null;
        MessageDigest mdEnc;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(pass.getBytes(), 0, pass.length());
            pass = new BigInteger(1, mdEnc.digest()).toString(16);
            while (pass.length() < 32) {
                pass = "0" + pass;
            }
            password = pass;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
    }

public void loginButton(View view){


    Intent intent = new Intent(LoginPage.this, MainActivity.class);
    startActivity(intent);



}



    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

}

//System.out.println("properties lol: "+feature.getProperties());
//System.out.println("linestring style lol: "+feature.getLineStringStyle());
//=========================================================================================
        /*
        //to determine time required for this operation
        long startnow;
        long endnow;

        startnow = android.os.SystemClock.uptimeMillis();
        //------------------------------------------------------------------------------
        endnow = android.os.SystemClock.uptimeMillis();
        System.out.println("Time required: "+ (endnow- startnow) + " ms");
        */
//=========================================================================================