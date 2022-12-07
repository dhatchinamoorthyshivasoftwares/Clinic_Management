package com.example.clinicmanagement;

import static com.example.clinicmanagement.Login_Activity.USER_CODE;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RestAPI {

    // public  static  String neturl = "";
    // public  static  String neturl = "";

    public  static  String urlString= "http://172.16.1.151:8080/api/mobileapi";
    public  static  String neturl = "http://172.16.1.151:8080/";


    public String GetJSONResponse(String paraURL, String paraData) {
        BufferedReader reader = null;
        StringBuffer response = null;
        try {
            URL url = new URL(paraURL);
            // Send POST data request
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            if (!paraData.equals("")) {
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(paraData);
                wr.flush();
            }
            // Get the server response
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            response = new StringBuffer();
            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                response.append(line);
                Log.i("GET_ResponseException=========>", line.toString());
            }
            // Log.i("GET_Response ======>",response.toString());
        }
        catch (Exception ex) {
            Log.i("GET_ResponseException", ex.toString());
        } finally {
            try {
                reader.close();
            } catch (Exception ex) {
            }
        }
        return response.toString();
    }

    public JSONObject GetDetails(String deviceid) throws Exception {
        String url = urlString+"login.php";
        JSONObject myResponse = null;

        // Create data variable for sent values to server
        String data = URLEncoder.encode("paradeviceid", "UTF-8") + "=" + URLEncoder.encode(deviceid, "UTF-8");
        // String data = URLEncoder.encode("pararequest", "UTF-8") + "=" + URLEncoder.encode("is_worker", "UTF-8");

        // Send datas
        try {
            //Read JSON response and print
            myResponse = new JSONObject(GetJSONResponse(url, data));
            //Log.i("LoginException",myResponse.getString("UserID"));
        } catch (JSONException ex) {
            Log.i("LoginException", ex.toString());
        }

        return myResponse;
    }


    public JSONObject GetDeviceCheck(String deviceid ) throws Exception {

       /* SimpleDateFormat dateFormatprev = new SimpleDateFormat("dd-MM-yyyy");
        Date d = dateFormatprev.parse(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String changedDate = dateFormat.format(d);
        */
        String Url = urlString+"/deviceCheck";
        JSONObject myResponse = null;

        URL url = new URL(Url); //Enter URL here
        String json = "{\"device_id\": \""+deviceid+"\"}";
        //Log.e("", "JSON_URL=========>" + json);
        //  URL url = new URL("https://dummy.restapiexample.com/api/v1/create"); //Enter URL here
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8"); // here you are setting the Content-Type for the data you are sending which is application/json
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.connect();

        DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
        wr.writeBytes(json);
        wr.flush();
        wr.close();

        InputStream response = httpURLConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
        StringBuilder sb = new StringBuilder();
        String line = null;

        // Log.e("", "LOGIN URL=========>" + response);
        Log.e("", "REST API =====(DEVICE CHECK PARAMS)====>" + json);

        try {
            while ((line = reader.readLine()) != null) {
                // sb.append(line+"\n");
                sb.append(line);
                Log.e("", "LOGIN URL===========>" + line);
            }

            //JSONTokener tokener = new JSONTokener(line);
            // Token_details = new JSONObject(tokener);
            JSONTokener tokener = new JSONTokener(sb.toString());
            myResponse = new JSONObject(tokener);
            Log.e("", "REST API =====(DEVICE CHECK JSON RESPONSE)====>" + myResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myResponse;
    }

    public JSONObject GetLoginJWT(String user_code,String username,String pincode) throws Exception {

       /* SimpleDateFormat dateFormatprev = new SimpleDateFormat("dd-MM-yyyy");
        Date d = dateFormatprev.parse(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String changedDate = dateFormat.format(d);
        */
        String Url = urlString+"/apploginJwt";
        JSONObject myResponse = null;

        URL url = new URL(Url); //Enter URL here
        String json = "{\"user_code\": \""+user_code+"\",\"username\":\""+username+" \",\"pincode\":\""+pincode+"\"}";
        //Log.e("", "JSON_URL=========>" + json);
        //  URL url = new URL("https://dummy.restapiexample.com/api/v1/create"); //Enter URL here
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8"); // here you are setting the Content-Type for the data you are sending which is application/json
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.connect();

        DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
        wr.writeBytes(json);
        wr.flush();
        wr.close();

        InputStream response = httpURLConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
        StringBuilder sb = new StringBuilder();
        String line = null;

        // Log.e("", "LOGIN URL=========>" + response);
        Log.e("", "REST API =====(LOGIN JWT PARAMS)====>" + json);

        try {
            while ((line = reader.readLine()) != null) {
                // sb.append(line+"\n");
                sb.append(line);
                Log.e("", "LOGIN URL===========>" + line);
            }

            //JSONTokener tokener = new JSONTokener(line);
            // Token_details = new JSONObject(tokener);
            JSONTokener tokener = new JSONTokener(sb.toString());
            myResponse = new JSONObject(tokener);
            Log.e("", "REST API =====(DEVICE CHECK JSON RESPONSE)====>" + myResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myResponse;
    }

    public JSONObject GetSchedule(String current_date ) throws Exception {

       /* SimpleDateFormat dateFormatprev = new SimpleDateFormat("dd-MM-yyyy");
        Date d = dateFormatprev.parse(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String changedDate = dateFormat.format(d);
        */
        String Url = urlString+"/schedule";
        JSONObject myResponse = null;

        URL url = new URL(Url); //Enter URL here
        String json = "{\"current_date\": \""+current_date+"\"}";
        //Log.e("", "JSON_URL=========>" + json);
        //  URL url = new URL("https://dummy.restapiexample.com/api/v1/create"); //Enter URL here
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8"); // here you are setting the Content-Type for the data you are sending which is application/json
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.connect();

        DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
        wr.writeBytes(json);
        wr.flush();
        wr.close();

        InputStream response = httpURLConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
        StringBuilder sb = new StringBuilder();
        String line = null;

        // Log.e("", "LOGIN URL=========>" + response);
        Log.e("", "REST API =====()====>" + json);

        try {
            while ((line = reader.readLine()) != null) {
                // sb.append(line+"\n");
                sb.append(line);
                Log.e("", "LOGIN URL===========>" + line);
            }

            //JSONTokener tokener = new JSONTokener(line);
            // Token_details = new JSONObject(tokener);
            JSONTokener tokener = new JSONTokener(sb.toString());
            myResponse = new JSONObject(tokener);
            Log.e("", "REST API =====(SCHEDULE JSON RESPONSE)====>" + myResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myResponse;
    }

    public JSONObject GetLogin(String jwtToken) throws Exception {

       /* SimpleDateFormat dateFormatprev = new SimpleDateFormat("dd-MM-yyyy");
        Date d = dateFormatprev.parse(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String changedDate = dateFormat.format(d);
        */
        String Url = urlString+"/applogin";
        JSONObject myResponse = null;

        URL url = new URL(Url); //Enter URL here
        String json = "{\"jwtToken\": \""+jwtToken+"\"}";
        //Log.e("", "JSON_URL=========>" + json);
        //  URL url = new URL("https://dummy.restapiexample.com/api/v1/create"); //Enter URL here
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8"); // here you are setting the Content-Type for the data you are sending which is application/json
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.connect();

        DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
        wr.writeBytes(json);
        wr.flush();
        wr.close();

        InputStream response = httpURLConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
        StringBuilder sb = new StringBuilder();
        String line = null;

        // Log.e("", "LOGIN URL=========>" + response);
        Log.e("", "REST API =====(LOGIN JWT PARAMS)====>" + json);

        try {
            while ((line = reader.readLine()) != null) {
                // sb.append(line+"\n");
                sb.append(line);
                Log.e("", "LOGIN URL===========>" + line);
            }

            //JSONTokener tokener = new JSONTokener(line);
            // Token_details = new JSONObject(tokener);
            JSONTokener tokener = new JSONTokener(sb.toString());
            myResponse = new JSONObject(tokener);
            Log.e("", "REST API =====(DEVICE CHECK JSON RESPONSE)====>" + myResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myResponse;
    }


    public JSONObject GetPatientJWT(String search_Value,String search_Type,String from_Date,String to_Date) throws Exception {

       /* SimpleDateFormat dateFormatprev = new SimpleDateFormat("dd-MM-yyyy");
        Date d = dateFormatprev.parse(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String changedDate = dateFormat.format(d);
        */
        String Url = urlString+"/searchPatientsJwt";
        JSONObject myResponse = null;

        URL url = new URL(Url); //Enter URL here
        String json = "{\"search_Value\": \""+search_Value.toLowerCase()+"\",\"search_Type\":\""+search_Type+"\",\"from_Date\":\""+from_Date+"\",\"to_Date\":\""+to_Date+"\"}";
        //Log.e("", "JSON_URL=========>" + json);
        //  URL url = new URL("https://dummy.restapiexample.com/api/v1/create"); //Enter URL here
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8"); // here you are setting the Content-Type for the data you are sending which is application/json
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.connect();

        DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
        wr.writeBytes(json);
        wr.flush();
        wr.close();

        InputStream response = httpURLConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
        StringBuilder sb = new StringBuilder();
        String line = null;

        // Log.e("", "LOGIN URL=========>" + response);
        Log.e("", "REST API =====(searchPatientsJwt PARAMS)====>" + json);

        try {
            while ((line = reader.readLine()) != null) {
                // sb.append(line+"\n");
                sb.append(line);
                Log.e("", "LOGIN URL===========>" + line);
            }

            //JSONTokener tokener = new JSONTokener(line);
            // Token_details = new JSONObject(tokener);
            JSONTokener tokener = new JSONTokener(sb.toString());
            myResponse = new JSONObject(tokener);
            Log.e("", "REST API =====(searchPatientsJwt JSON RESPONSE)====>" + myResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myResponse;
    }


    public JSONObject GetPatient(String jwtToken) throws Exception {

       /* SimpleDateFormat dateFormatprev = new SimpleDateFormat("dd-MM-yyyy");
        Date d = dateFormatprev.parse(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String changedDate = dateFormat.format(d);
        */
        String Url = urlString+"/searchPatients";
        JSONObject myResponse = null;

        URL url = new URL(Url); //Enter URL here
        String json = "{\"jwtToken\": \""+jwtToken+"\"}";
        //Log.e("", "JSON_URL=========>" + json);
        //  URL url = new URL("https://dummy.restapiexample.com/api/v1/create"); //Enter URL here
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8"); // here you are setting the Content-Type for the data you are sending which is application/json
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.connect();

        DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
        wr.writeBytes(json);
        wr.flush();
        wr.close();

        InputStream response = httpURLConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
        StringBuilder sb = new StringBuilder();
        String line = null;

        // Log.e("", "LOGIN URL=========>" + response);
        Log.e("", "REST API =====(searchPatients PARAMS)====>" + json);

        try {
            while ((line = reader.readLine()) != null) {
                // sb.append(line+"\n");
                sb.append(line);
                Log.e("", "LOGIN URL===========>" + line);
            }

            //JSONTokener tokener = new JSONTokener(line);
            // Token_details = new JSONObject(tokener);
            JSONTokener tokener = new JSONTokener(sb.toString());
            myResponse = new JSONObject(tokener);
            Log.e("", "REST API =====(searchPatients JSON RESPONSE)====>" + myResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myResponse;
    }


    public JSONObject GetSaveBookingJWT(String patient_id,String user_id,String schedule_id,String doctor_note,String booking_date,String employee_id) throws Exception {

       /* SimpleDateFormat dateFormatprev = new SimpleDateFormat("dd-MM-yyyy");
        Date d = dateFormatprev.parse(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String changedDate = dateFormat.format(d);
        */
        String Url = urlString+"/saveBookingJwt";
        JSONObject myResponse = null;

        URL url = new URL(Url); //Enter URL here
        String json = "{\"patient_id\": \""+patient_id+"\",\"user_id\":\""+user_id+"\",\"schedule_id\":\""+schedule_id+"\",\"doctor_note\":\""+doctor_note+"\",\"booking_date\":\""+booking_date+"\",\"user_code\":\""+USER_CODE+"\",\"employee_id\":\""+employee_id+"\"}";
        //Log.e("", "JSON_URL=========>" + json);
        //  URL url = new URL("https://dummy.restapiexample.com/api/v1/create"); //Enter URL here
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8"); // here you are setting the Content-Type for the data you are sending which is application/json
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.connect();

        DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
        wr.writeBytes(json);
        wr.flush();
        wr.close();

        InputStream response = httpURLConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
        StringBuilder sb = new StringBuilder();
        String line = null;

        // Log.e("", "LOGIN URL=========>" + response);
        Log.e("", "REST API =====(SaveBookingJWT PARAMS)====>" + json);

        try {
            while ((line = reader.readLine()) != null) {
                // sb.append(line+"\n");
                sb.append(line);
                Log.e("", "LOGIN URL===========>" + line);
            }

            //JSONTokener tokener = new JSONTokener(line);
            // Token_details = new JSONObject(tokener);
            JSONTokener tokener = new JSONTokener(sb.toString());
            myResponse = new JSONObject(tokener);
            Log.e("", "REST API =====(SaveBookingJWT JSON RESPONSE)====>" + myResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myResponse;
    }

    public JSONObject GetUpdateBooking(String user_code,String schedule_id,String doctor_note,String booking_id,String booking_date,String employee_id) throws Exception {

       /* SimpleDateFormat dateFormatprev = new SimpleDateFormat("dd-MM-yyyy");
        Date d = dateFormatprev.parse(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String changedDate = dateFormat.format(d);
        */
        String Url = urlString+"/updateBooking";
        JSONObject myResponse = null;

        URL url = new URL(Url); //Enter URL here
        String json = "{\"user_code\": \""+user_code+"\",\"schedule_id\":\""+schedule_id+"\",\"doctor_note\":\""+doctor_note+"\",\"booking_id\":\""+booking_id+"\",\"booking_date\":\""+booking_date+"\",\"employee_id\":\""+employee_id+"\"}";
        //Log.e("", "JSON_URL=========>" + json);
        //  URL url = new URL("https://dummy.restapiexample.com/api/v1/create"); //Enter URL here
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8"); // here you are setting the Content-Type for the data you are sending which is application/json
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.connect();

        DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
        wr.writeBytes(json);
        wr.flush();
        wr.close();

        InputStream response = httpURLConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
        StringBuilder sb = new StringBuilder();
        String line = null;

        // Log.e("", "LOGIN URL=========>" + response);
        Log.e("", "REST API =====(Update Booking PARAMS)====>" + json);

        try {
            while ((line = reader.readLine()) != null) {
                // sb.append(line+"\n");
                sb.append(line);
                Log.e("", "LOGIN URL===========>" + line);
            }

            //JSONTokener tokener = new JSONTokener(line);
            // Token_details = new JSONObject(tokener);
            JSONTokener tokener = new JSONTokener(sb.toString());
            myResponse = new JSONObject(tokener);
            Log.e("", "REST API =====(Update Booking JSON RESPONSE)====>" + myResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myResponse;
    }

    public JSONObject GetSaveBooking(String token) throws Exception {

       /* SimpleDateFormat dateFormatprev = new SimpleDateFormat("dd-MM-yyyy");
        Date d = dateFormatprev.parse(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String changedDate = dateFormat.format(d);
        */
        String Url = urlString+"/saveBooking";
        JSONObject myResponse = null;

        URL url = new URL(Url); //Enter URL here
        String json = "{\"jwtToken\": \""+token+"\"}";
        //Log.e("", "JSON_URL=========>" + json);
        //  URL url = new URL("https://dummy.restapiexample.com/api/v1/create"); //Enter URL here
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8"); // here you are setting the Content-Type for the data you are sending which is application/json
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.connect();

        DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
        wr.writeBytes(json);
        wr.flush();
        wr.close();

        InputStream response = httpURLConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
        StringBuilder sb = new StringBuilder();
        String line = null;

        // Log.e("", "LOGIN URL=========>" + response);
        Log.e("", "REST API =====(SaveBooking PARAMS)====>" + json);

        try {
            while ((line = reader.readLine()) != null) {
                // sb.append(line+"\n");
                sb.append(line);
                Log.e("", "LOGIN URL===========>" + line);
            }

            //JSONTokener tokener = new JSONTokener(line);
            // Token_details = new JSONObject(tokener);
            JSONTokener tokener = new JSONTokener(sb.toString());
            myResponse = new JSONObject(tokener);
            Log.e("", "REST API =====(SaveBooking JSON RESPONSE)====>" + myResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myResponse;
    }


    public JSONObject GetBookingList(String current_Date) throws Exception {

       /* SimpleDateFormat dateFormatprev = new SimpleDateFormat("dd-MM-yyyy");
        Date d = dateFormatprev.parse(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String changedDate = dateFormat.format(d);
        */
        String Url = urlString+"/listBooking";
        JSONObject myResponse = null;

        URL url = new URL(Url); //Enter URL here
        String json = "{\"current_Date\": \""+current_Date+"\"}";
        //Log.e("", "JSON_URL=========>" + json);
        //  URL url = new URL("https://dummy.restapiexample.com/api/v1/create"); //Enter URL here
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8"); // here you are setting the Content-Type for the data you are sending which is application/json
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.connect();

        DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
        wr.writeBytes(json);
        wr.flush();
        wr.close();

        InputStream response = httpURLConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
        StringBuilder sb = new StringBuilder();
        String line = null;

        // Log.e("", "LOGIN URL=========>" + response);
        Log.e("", "REST API =====(Booking List PARAMS)====>" + json);

        try {
            while ((line = reader.readLine()) != null) {
                // sb.append(line+"\n");
                sb.append(line);
                Log.e("", "LOGIN URL===========>" + line);
            }

            //JSONTokener tokener = new JSONTokener(line);
            // Token_details = new JSONObject(tokener);
            JSONTokener tokener = new JSONTokener(sb.toString());
            myResponse = new JSONObject(tokener);
            Log.e("", "REST API =====(Booking List JSON RESPONSE)====>" + myResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myResponse;
    }

    public JSONObject GetDoctorList(String user_code) throws Exception {

       /* SimpleDateFormat dateFormatprev = new SimpleDateFormat("dd-MM-yyyy");
        Date d = dateFormatprev.parse(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String changedDate = dateFormat.format(d);
        */
        String Url = urlString+"/listDoctor";
        JSONObject myResponse = null;

        URL url = new URL(Url); //Enter URL here
        String json = "{\"user_code\": \""+user_code+"\"}";
        //Log.e("", "JSON_URL=========>" + json);
        //  URL url = new URL("https://dummy.restapiexample.com/api/v1/create"); //Enter URL here
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8"); // here you are setting the Content-Type for the data you are sending which is application/json
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.connect();

        DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
        wr.writeBytes(json);
        wr.flush();
        wr.close();

        InputStream response = httpURLConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
        StringBuilder sb = new StringBuilder();
        String line = null;

        // Log.e("", "LOGIN URL=========>" + response);
        Log.e("", "REST API =====(Doctor List PARAMS)====>" + json);

        try {
            while ((line = reader.readLine()) != null) {
                // sb.append(line+"\n");
                sb.append(line);
                Log.e("", "LOGIN URL===========>" + line);
            }

            //JSONTokener tokener = new JSONTokener(line);
            // Token_details = new JSONObject(tokener);
            JSONTokener tokener = new JSONTokener(sb.toString());
            myResponse = new JSONObject(tokener);
            Log.e("", "REST API =====(Doctor List JSON RESPONSE)====>" + myResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myResponse;
    }

    public JSONObject GetCompanyDataJwt(String user_id) throws Exception {

       /* SimpleDateFormat dateFormatprev = new SimpleDateFormat("dd-MM-yyyy");
        Date d = dateFormatprev.parse(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String changedDate = dateFormat.format(d);
        */
        String Url = neturl+"api/common/companyDataJwt";
        JSONObject myResponse = null;

        URL url = new URL(Url); //Enter URL here
        String json = "{\"user_id\": "+0+"}";
        //Log.e("", "JSON_URL=========>" + json);
        //  URL url = new URL("https://dummy.restapiexample.com/api/v1/create"); //Enter URL here
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8"); // here you are setting the Content-Type for the data you are sending which is application/json
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.connect();

        DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
        wr.writeBytes(json);
        wr.flush();
        wr.close();

        InputStream response = httpURLConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
        StringBuilder sb = new StringBuilder();
        String line = null;

        // Log.e("", "LOGIN URL=========>" + response);
        Log.e("", "REST API =====(COMPANY DATA PARAMS)====>" + json);

        try {
            while ((line = reader.readLine()) != null) {
                // sb.append(line+"\n");
                sb.append(line);
                Log.e("", "LOGIN URL===========>" + line);
            }

            //JSONTokener tokener = new JSONTokener(line);
            // Token_details = new JSONObject(tokener);
            JSONTokener tokener = new JSONTokener(sb.toString());
            myResponse = new JSONObject(tokener);
            Log.e("", "REST API =====(COMPANY DATA JSON RESPONSE)====>" + myResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myResponse;
    }

    public JSONObject GetCompanyData(String jwtToken) throws Exception {

       /* SimpleDateFormat dateFormatprev = new SimpleDateFormat("dd-MM-yyyy");
        Date d = dateFormatprev.parse(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String changedDate = dateFormat.format(d);
        */
        String Url = neturl+"api/common/companyData";
        JSONObject myResponse = null;

        URL url = new URL(Url); //Enter URL here
        String json = "{\"jwtToken\": \""+jwtToken+"\"}";
        //Log.e("", "JSON_URL=========>" + json);
        //  URL url = new URL("https://dummy.restapiexample.com/api/v1/create"); //Enter URL here
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8"); // here you are setting the Content-Type for the data you are sending which is application/json
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.connect();

        DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
        wr.writeBytes(json);
        wr.flush();
        wr.close();

        InputStream response = httpURLConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(response));
        StringBuilder sb = new StringBuilder();
        String line = null;

        // Log.e("", "LOGIN URL=========>" + response);
        Log.e("", "REST API =====(COMPANY DATA PARAMS)====>" + json);

        try {
            while ((line = reader.readLine()) != null) {
                // sb.append(line+"\n");
                sb.append(line);
                Log.e("", "LOGIN URL===========>" + line);
            }

            //JSONTokener tokener = new JSONTokener(line);
            // Token_details = new JSONObject(tokener);
            JSONTokener tokener = new JSONTokener(sb.toString());
            myResponse = new JSONObject(tokener);
            Log.e("", "REST API =====(COMPANY DATA JSON RESPONSE)====>" + myResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myResponse;
    }
}