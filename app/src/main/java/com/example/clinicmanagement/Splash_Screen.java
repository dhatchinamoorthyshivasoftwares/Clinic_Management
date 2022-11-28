package com.example.clinicmanagement;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash_Screen extends AppCompatActivity {
    ImageView Logo_img,tv1;
    ImageView shine_img_lv;
    TextView tv,companyname;
    private static int SPLASH_SCREEN_TIME_OUT=2500;
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
        tv = findViewById(R.id.linesales_management);
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

