package com.matrixcare.trinity.Api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by nates on 12/20/2017.
 */

public class BaseApi {
    private static final String LOG = "BaseApi";
    private static final int  MEG = 1024 * 1024;
    protected String mBaseUrl;
    protected Context mContext;

    public BaseApi(Context context) {
        mContext = context;
        mBaseUrl = "http://trinity-mapsapp.azurewebsites.net/";
    }
    protected String ExecuteRequest(String partialUrl, String parameters) {
        try {
            URL obj = new URL(mBaseUrl+partialUrl);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Content-Type", "application/json");
          //  connection.setDoOutput(true);
            if (parameters!=null && parameters.length()>0) { //only write parms to the outgoing request if they are specified
                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(parameters);
                wr.flush();
                wr.close();
            }

            int responseCode = connection.getResponseCode();
            InputStream stream;
            if (responseCode!=200) { //200 is success, everything else, for this purpose, is not a success.
                stream = connection.getErrorStream();
            }
            else {
                stream = connection.getInputStream();
            }
            return GetResponseStringBuffer(stream).toString();

        }
        catch (Exception ex) {
            Log.e(LOG,ex.getMessage());
            return (ex.getMessage());
        }
    }


    @NonNull
    protected StringBuffer GetResponseStringBuffer(InputStream stream) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(stream));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = input.readLine()) != null) {
            response.append(inputLine);
        }
        input.close();
        return response;
    }

    protected String BuildParm(Integer field, Integer value, Boolean useDelimiter) {
        try {
            String charset = "UTF-8";
            String result = useDelimiter?"&":"";
            result+= mContext.getString(field) + "=" + URLEncoder.encode(mContext.getString(value), charset);
            return result;
        }
        catch(UnsupportedEncodingException ex) {
            Log.e(LOG,ex.getMessage());
            throw new AssertionError("no utf found");
        }
    }

    protected String BuildParm(Integer field, String value, Boolean useDelimiter) {
        try {
            String charset = "UTF-8";
            String result = useDelimiter?"&":"";
            result+= mContext.getString(field) + "=" + URLEncoder.encode(value, charset);
            return result;
        }
        catch(UnsupportedEncodingException ex) {
            Log.e(LOG,ex.getMessage());
            throw new AssertionError("no utf found");
        }
    }

    protected Bitmap ExecuteBitmapRequest(String url) {
        try {
            //url= "http://merge3llc.com/images/portfolio/grey_house.JPG";
            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod("GET");
            Bitmap bmp = BitmapFactory.decodeStream(connection.getInputStream());
            return bmp;
        }
        catch (Exception ex) {
            Log.e(LOG,ex.getMessage());
            return null;
        }

    }


}