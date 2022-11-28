package com.example.clinicmanagement;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class RestAPI {

    // public  static  String neturl = "";
    // public  static  String neturl = "";

    public  static  String urlString= "";
    public  static  String neturl = "";


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

    public JSONObject GetLoginDetails(String deviceid) throws Exception {
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

}