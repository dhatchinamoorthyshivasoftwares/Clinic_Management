package com.example.clinicmanagement;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class Splash_Screen extends AppCompatActivity {
    ImageView Logo_img,tv1;
    ImageView shine_img_lv;
    TextView tv_company_name,companyname;
    private static int SPLASH_SCREEN_TIME_OUT=2000;
    public static  String COMPANY_TOKEN="",COMPANY_NAME="",COMPANY_ID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        // getWindow().setFlags( WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //This method is used so that your splash activity
        //can cover the entire screen.

        // getSupportActionBar().hide(); // hide the title bar


        setContentView(R.layout.splash_screen);
       /* Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.trios_background_image),size.x,size.y,true);
        //BitmapDrawable btr =new BitmapDrawable(bmp);
        ImageView iv_background = (ImageView) findViewById(R.id.splash_screen_img);
        iv_background.setImageBitmap(bmp);*/


        //this will bind your MainActivity.class file with activity_main.
        //getSupportActionBar().hide();
        Logo_img = findViewById(R.id.natarajan_logo);
        tv_company_name = findViewById(R.id.company_name);
       // tv1 = findViewById(R.id.trio_s);

       /* Animation anima = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);
        Logo_img.startAnimation(anima);
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_to_right_shine);
        Animation anim11 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.logo_slide);


        anima.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

               if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    shine_img_lv.setVisibility(View.VISIBLE);
                    shine_img_lv.setAnimation(anim);
                }
                tv.setVisibility(View.VISIBLE);
                Animation anim1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.trios_fadein);
                tv.startAnimation(anim1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                shine_img_lv.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        */


        if(isNetworkAvailable()){
            new AsynccompanyDataJwt().execute();
        }else{
            COMPANY_NAME = "MSR Tooth Clinic";
            tv_company_name.setText(String.valueOf("MSR Tooth Clinic"));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i=new Intent(Splash_Screen.this,Login_Activity.class);
                    startActivity(i);
                    finish();
                }
            }, SPLASH_SCREEN_TIME_OUT);
        }

    }

    public class AsynccompanyDataJwt extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj,json_body,json_token;
        int flag;

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            try {
                //Make webservice connection and call APi

                RestAPI objRestAPI = new RestAPI();

                if (isNetworkAvailable()) {
                    jsonObj = objRestAPI.GetCompanyDataJwt("");

                    if(jsonObj != null){
                        // json_body = new JSONObject(jsonObj.getString("body"));
                        //json_token = new JSONObject(json_body.getString("token"));
                        COMPANY_TOKEN = new JSONObject(jsonObj.getString("body")).getString("token");
                    }
                }
            }
            //Catch Block UserAuth true
            catch (Exception e) {
                Log.d("AsyncLoggerService", "Message");
                Log.d("AsyncLoggerService", e.getMessage());
            }
            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(Boolean result) {
            if(!COMPANY_TOKEN.equals("")) {
                // Toast.makeText(Login_Activity.this, String.valueOf(LOGIN_TOKEN), Toast.LENGTH_SHORT).show();
                new AsynccompanyData().execute();
            }
        }
    }

    public class AsynccompanyData extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj,json_body,jsonObj_userinfo;
        JSONArray CompanyArray;
        String  success ="";

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            try {
                //Make webservice connection and call APi

                RestAPI objRestAPI = new RestAPI();

                if (isNetworkAvailable()) {
                    jsonObj = objRestAPI.GetCompanyData(COMPANY_TOKEN);

                    if(jsonObj != null){

                        CompanyArray =new JSONObject(jsonObj.getString("body")).getJSONArray("CompanyArray");

                        if(CompanyArray.length() >0){
                            COMPANY_ID = CompanyArray.getJSONObject(0).getString("company_id");
                            COMPANY_NAME = CompanyArray.getJSONObject(0).getString("company_name");
                        }
                    }
                }
            }
            //Catch Block UserAuth true
            catch (Exception e) {
                Log.d("AsyncLoggerService", "Message");
                Log.d("AsyncLoggerService", e.getMessage());
            }
            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(Boolean result) {

            try {

                if (!COMPANY_NAME.equals("")) {
                    tv_company_name.setText(String.valueOf(COMPANY_NAME));
                    //Toast.makeText(Login_Activity.this, String.valueOf(codeString), Toast.LENGTH_SHORT).show();
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(Splash_Screen.this, Login_Activity.class);
                        startActivity(i);
                        finish();
                    }
                }, SPLASH_SCREEN_TIME_OUT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

