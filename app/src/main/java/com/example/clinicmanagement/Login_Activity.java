package com.example.clinicmanagement;

import static com.example.clinicmanagement.Splash_Screen.COMPANY_NAME;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;

public class Login_Activity extends AppCompatActivity {

    Button clean,btn1,dot_1,dot_2,dot_3,dot_4,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0;
    TextView company_name_tv,user_name_tv,tv_1,tv_2,tv_3,tv_4;
    View point_1,point_2,point_3,point_4;
    Context context;
    ImageView btn_back;
    private String codeString = "";
    public  static  String getdeviceid="",USER_ID="",USER_CODE="",USER_NAME="",ACTIVE_STATUS="",LOGIN_TOKEN="",EMPLOYEE_ID ="",EMPLOYEE_NAME ="";
    private static final int MAX_LENGHT = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        context = this;

        btn0 =(Button) findViewById(R.id.btn0);
        btn1 =(Button) findViewById(R.id.btn1);
        btn2 =(Button) findViewById(R.id.btn2);
        btn3 =(Button) findViewById(R.id.btn3);
        btn4 =(Button) findViewById(R.id.btn4);
        btn5 =(Button) findViewById(R.id.btn5);
        btn6 =(Button) findViewById(R.id.btn6);
        btn7 =(Button) findViewById(R.id.btn7);
        btn8 =(Button) findViewById(R.id.btn8);
        btn9 =(Button) findViewById(R.id.btn9);

        clean = (Button) findViewById(R.id.clean);
        btn_back = (ImageView) findViewById(R.id.btn_back);


        tv_1 =(TextView) findViewById(R.id.tv_1);
        tv_2 =(TextView) findViewById(R.id.tv_2);
        tv_3 =(TextView) findViewById(R.id.tv_3);
        tv_4 =(TextView) findViewById(R.id.tv_4);

        point_1 =(View) findViewById(R.id.point_1);
        point_2 =(View) findViewById(R.id.point_2);
        point_3 =(View) findViewById(R.id.point_3);
        point_4 =(View) findViewById(R.id.point_4);

        user_name_tv  = (TextView) findViewById(R.id.user_name_tv);

        company_name_tv  = (TextView) findViewById(R.id.company_name);



        if(!COMPANY_NAME.equals("")){
            company_name_tv.setText(String.valueOf(COMPANY_NAME));
        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codeString.length() > 0) {
                    codeString = removeLastChar(codeString);
                    setDotImagesState();
                }
            }
        });

        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codeString.length() > 0) {
                    codeString = "";
                    setDotImagesState();
                }
            }
        });

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(0);
                // Toast.makeText(MainActivity.this, String.valueOf(codeString), Toast.LENGTH_SHORT).show();
                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();

                }
                if (codeString.length() > MAX_LENGHT){
                    codeString = "";
                    call();
                    getbtnStringCode(0);
                }
                setDotImagesState();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getbtnStringCode(1);
                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();

                }
                if (codeString.length() > MAX_LENGHT){
                    //reset the input code
                    codeString = "";
                    call();
                    getbtnStringCode(1);
                }
                setDotImagesState();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(2);
                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();

                }
                if (codeString.length() > MAX_LENGHT){
                    //reset the input code
                    codeString = "";
                    call();
                    getbtnStringCode(2);
                }
                setDotImagesState();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(3);
                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();

                }
                if (codeString.length() > MAX_LENGHT){
                    //reset the input code
                    codeString = "";
                    getbtnStringCode(3);
                    call();
                }
                setDotImagesState();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(4);

                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();
                }
                if (codeString.length() > MAX_LENGHT){
                    //reset the input code
                    codeString = "";
                    getbtnStringCode(4);
                    call();
                }
                setDotImagesState();
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(5);

                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();
                    call();
                }
                if (codeString.length() > MAX_LENGHT){
                    //reset the input code
                    codeString = "";
                    getbtnStringCode(5);
                }
                setDotImagesState();
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(6);

                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();
                }
                if (codeString.length() > MAX_LENGHT){
                    codeString = "";
                    getbtnStringCode(6);
                    call();
                }
                setDotImagesState();
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(7);
                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();
                }
                if (codeString.length() > MAX_LENGHT){
                    codeString = "";
                    getbtnStringCode(7);
                    call();
                }
                setDotImagesState();
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(8);
                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();
                }
                if (codeString.length() > MAX_LENGHT){
                    codeString = "";
                    getbtnStringCode(8);
                    call();
                }
                setDotImagesState();
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbtnStringCode(9);
                if (codeString.length() == MAX_LENGHT) {
                    new AsyncLogin().execute();
                }
                if (codeString.length() > MAX_LENGHT){
                    codeString = "";
                    getbtnStringCode(9);
                    call();
                }
                setDotImagesState();
            }
        });

        CheckDeviceAuthendication();
    }

    @SuppressLint("HardwareIds")
    public void CheckDeviceAuthendication(){

        try{
            getdeviceid =  Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

            //Log.d(LOGTAG, "CheckDeviceAuthendication: "+getdeviceid);
            if (getdeviceid.equals(null)) {
                //  getdeviceid = "000000";
            }
            //Toast.makeText(context,"Connection succeeded...!",Toast.LENGTH_SHORT).show();
            Log.d("Device ID : ",getdeviceid);
            // new AsyncChkUser().execute(getdeviceid);
        }
        catch (Exception ex){
            String getfunname = new Object(){}.getClass().getEnclosingMethod().getName();
            // networkstate = isNetworkAvailable();
            // if (networkstate == true) {
            //      new AsyncLoggerService().execute("Android : " + LOGTAG, getfunname, ex.toString());
            // }
        }

        if(!getdeviceid.equals("")){
            // Clipboard();
            // new Async_Local_DB().execute();
            // Toast.makeText(context,String.valueOf(getdeviceid),Toast.LENGTH_SHORT).show();
            if(isNetworkAvailable()){
                new AsyncDeviceCheck().execute(getdeviceid);
            }else{
                Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public class AsyncCheck extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj,jsonObject_token;
        int flag;

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            try {
                //Make webservice connection and call APi

                RestAPI objRestAPI = new RestAPI();
                //  if (networkstate) {

               // jsonObj = objRestAPI.GetLoginDetails(getdeviceid);
                //  }
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

            if (jsonObj == null || jsonObj.equals("")) {
                // Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                toast("Please check your internet connection");
            } else {
                try {
                    int success = jsonObj.getInt("success");
                    if (success == 0) {

                        toast("Invaild Password");

                    } else if (success == 1) {

                    } else if (success == 3) {
                        // Toast.makeText(context, "Your account is inactive. Please contact admin.", Toast.LENGTH_SHORT).show();
                        toast("Your account is inactive. Please contact admin.");
                    } else {
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Log.d("Login", e.getMessage());
                    if (isNetworkAvailable()) {
                        // new AsyncLoggerService().execute("Android : " + LOGTAG, getfunname, e.toString());
                    }
                }
            }
        }
    }

    public class AsyncDeviceCheck extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj, jsonObj_body,jsonObj_userinfo;
        String message, token;
        String  success="";

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            try {
                //Make webservice connection and call APi

                RestAPI objRestAPI = new RestAPI();
                if (isNetworkAvailable()) {
                    jsonObj = objRestAPI.GetDeviceCheck(getdeviceid);
                    message = "";
                    token = "";


                       if(jsonObj!=null) {
                           jsonObj_body = new JSONObject(jsonObj.getString("body"));
                       }

                       if(jsonObj_body != null) {
                           success = jsonObj_body.getString("message");
                           jsonObj_userinfo = new JSONObject(jsonObj_body.getString("UserInfo"));

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

        @Override
        protected void onPostExecute(Boolean result) {
            //Toast.makeText(context, String.valueOf(final_flag) +""+getdeviceid, Toast.LENGTH_SHORT).show();
            try {
                if(success.equals("success") || success.equals("unsuccess")) {
                        USER_CODE = jsonObj_userinfo.getString("user_code");
                        USER_NAME = jsonObj_userinfo.getString("username");
                        USER_ID  = jsonObj_userinfo.getString("user_id");

                        EMPLOYEE_ID = jsonObj_userinfo.getString("employee_id");
                        EMPLOYEE_NAME = jsonObj_userinfo.getString("employee_name");
                }
                if(success.equals("unsuccess")){
                    Toast.makeText(context, "Inactive User", Toast.LENGTH_SHORT).show();
                    ACTIVE_STATUS = "0";
                }
                if(success.equals("Invalid device id")){

                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context,R.style.AlertDialogStyle);
                    builder.setMessage("Please contact system administrator with this [ "+ getdeviceid +" ] device id")
                            .setTitle("Access Denied")
                            .setCancelable(false)
                            .setPositiveButton("Copy", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ClipboardManager myClipboard;
                                    myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                                    ClipData myClip;
                                    //String text = "hello world";
                                    myClip = ClipData.newPlainText("Device ID", getdeviceid);
                                    myClipboard.setPrimaryClip(myClip);
                                    Toast.makeText(getApplicationContext(),"Device ID Copied to ClipBoard",Toast.LENGTH_SHORT).show();
                                    //.setIcon(R.mipmap.admin)
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    Toast.makeText(getApplicationContext(), "You are not authorized to use this application. Please contact admin with device ID", Toast.LENGTH_SHORT).show();

                   // Toast.makeText(context, "Invalid device id", Toast.LENGTH_SHORT).show();
                }

            if(!EMPLOYEE_NAME.equals("")){
                user_name_tv.setText(String.valueOf(EMPLOYEE_NAME));
               // Toast.makeText(context, "NAME : "+USER_NAME+"  CODE : "+USER_CODE, Toast.LENGTH_SHORT).show();
                Log.d("LOGIN ACTIVITY ========(DEVICE CHECK)========>", "NAME : "+USER_NAME+"  CODE : "+USER_CODE);
            }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    public class AsyncLogin extends
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
                    jsonObj = objRestAPI.GetLoginJWT(USER_CODE,USER_NAME,codeString);

                    if(jsonObj != null){
                       // json_body = new JSONObject(jsonObj.getString("body"));
                        //json_token = new JSONObject(json_body.getString("token"));
                        LOGIN_TOKEN = new JSONObject(jsonObj.getString("body")).getString("token");
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
            if(codeString != null) {
                    // Toast.makeText(Login_Activity.this, String.valueOf(LOGIN_TOKEN), Toast.LENGTH_SHORT).show();

                if(isNetworkAvailable()) {
                    //API Login Validation

                    new AsyncLoginFinal().execute();
                }
            }
        }
    }

    public class AsyncLoginFinal extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj,json_body,jsonObj_userinfo;
        String  success ="";

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            try {
                //Make webservice connection and call APi

                RestAPI objRestAPI = new RestAPI();

                if (isNetworkAvailable()) {
                    jsonObj = objRestAPI.GetLogin(LOGIN_TOKEN);

                    if(jsonObj != null){

                        json_body = new JSONObject(jsonObj.getString("body"));
                        success =new JSONObject(jsonObj.getString("body")).getString("message");

                        if(success.equals("Login Success")){
                            USER_CODE =  new JSONObject(jsonObj.getString("UserInfo")).getString("user_code");
                            USER_NAME =  new JSONObject(jsonObj.getString("UserInfo")).getString("username");
                            USER_ID  = new JSONObject(jsonObj.getString("UserInfo")).getString("user_id");
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

            if(success.equals("Login Success")) {
                //Toast.makeText(Login_Activity.this, String.valueOf(codeString), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Login_Activity.this, MainActivity.class);
                Login_Activity.this.finish();
                startActivity(i);
                if (codeString.length() > 0) {
                    codeString = "";
                    setDotImagesState();
                }
            }
            if(!ACTIVE_STATUS.equals("0")) {

                if (success.equals("unsuccess")) {
                    Toast.makeText(Login_Activity.this, "Username or password is incorrect", Toast.LENGTH_SHORT).show();
                    if (codeString.length() > 0) {
                        codeString = "";
                        setDotImagesState();
                    }
                }
            }

        }
    }

    private void call() {
        //tv_1.setText("");
        // tv_2.setText("");
        // tv_3.setText("");
        //  tv_4.setText("");

        point_1.setVisibility(View.GONE);
        point_2.setVisibility(View.GONE);
        point_3.setVisibility(View.GONE);
        point_4.setVisibility(View.GONE);
        if(codeString != null) {
            // Toast.makeText(Login_Activity.this, String.valueOf(codeString), Toast.LENGTH_SHORT).show();
        }
    }

    private void setDotImagesState() {
      /*  for (int i = 0; i < codeString.length(); i++) {

            dots.get(i).setBackgroundResource(R.drawable.paswd_enable);
        }
        if (codeString.length()<4) {
            for (int j = codeString.length(); j<4; j++) {
                dots.get(j).setBackgroundResource(R.drawable.paswd_disable);
            }
        }*/
        if(codeString.length() == 0){
            //  tv_1.setText("");
            //  tv_2.setText("");
            //  tv_3.setText("");
            //  tv_4.setText("");
            point_1.setVisibility(View.GONE);
            point_2.setVisibility(View.GONE);
            point_3.setVisibility(View.GONE);
            point_4.setVisibility(View.GONE);
        }
        if(codeString.length() == 1){
            // tv_1.setText(String.valueOf(codeString.charAt(0)));
            //  tv_2.setText("");
            //  tv_3.setText("");
            //  tv_4.setText("");
            point_1.setVisibility(View.VISIBLE);
            point_2.setVisibility(View.GONE);
            point_3.setVisibility(View.GONE);
            point_4.setVisibility(View.GONE);
        }
        if(codeString.length() == 2){
            // tv_1.setText(String.valueOf(codeString.charAt(0)));
            //  tv_2.setText(String.valueOf(codeString.charAt(1)));
            // tv_3.setText("");
            // tv_4.setText("");
            point_1.setVisibility(View.VISIBLE);
            point_2.setVisibility(View.VISIBLE);
            point_3.setVisibility(View.GONE);
            point_4.setVisibility(View.GONE);
        }
        if(codeString.length() == 3){
            // tv_1.setText(String.valueOf(codeString.charAt(0)));
            // tv_2.setText(String.valueOf(codeString.charAt(1)));
            // tv_3.setText(String.valueOf(codeString.charAt(2)));
            //  tv_4.setText("");
            point_1.setVisibility(View.VISIBLE);
            point_2.setVisibility(View.VISIBLE);
            point_3.setVisibility(View.VISIBLE);
            point_4.setVisibility(View.GONE);
        }
        if(codeString.length() == 4){
            // tv_1.setText(String.valueOf(codeString.charAt(0)));
            // tv_2.setText(String.valueOf(codeString.charAt(1)));
            //  tv_3.setText(String.valueOf(codeString.charAt(2)));
            //  tv_4.setText(String.valueOf(codeString.charAt(3)));
            point_1.setVisibility(View.VISIBLE);
            point_2.setVisibility(View.VISIBLE);
            point_3.setVisibility(View.VISIBLE);
            point_4.setVisibility(View.VISIBLE);
        }
    }

    private String removeAllChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length()-codeString.length() );
    }
    //Remove only last character
    private String removeLastChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length()-1 );
    }
    private void getbtnStringCode(int val) {
        switch (val) {
            case 0:
                codeString += "0";
                break;
            case 1:
                codeString += "1";
                break;
            case 2:
                codeString += "2";
                break;
            case 3:
                codeString += "3";
                break;
            case 4:
                codeString += "4";
                break;
            case 5:
                codeString += "5";
                break;
            case 6:
                codeString += "6";
                break;
            case 7:
                codeString += "7";
                break;
            case 8:
                codeString += "8";
                break;
            case 9:
                codeString += "9";
                break;
            default:
                break;
        }


    }

    public void toast(String message)
    {
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.check_internet, null);
        TextView textView = (TextView) view.findViewById(R.id.custom_toast_text);
        textView.setText(message);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

}

