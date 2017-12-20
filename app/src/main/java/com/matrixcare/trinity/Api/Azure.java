package com.matrixcare.trinity.Api;

/**
 * Created by nates on 12/20/2017.
 */

import android.content.Context;
import android.util.Log;

import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class Azure {
    private static final String LOG = "AzureApi";
    private String mMobileBackendUrl = "https://trinity-mobileapp.azurewebsites.net";
    private Context mContext;
    private static MobileServiceClient mClient;
    private static Azure mInstance = null;

    private Azure(Context context) {
        mContext = context;
        try {
            mClient = new MobileServiceClient(mMobileBackendUrl, mContext);
            mClient.setAndroidHttpClientFactory(new OkHttpClientFactory() {
                @Override
                public OkHttpClient createOkHttpClient() {
                    OkHttpClient client = new OkHttpClient();
                    client.setReadTimeout(20, TimeUnit.SECONDS);
                    client.setWriteTimeout(20, TimeUnit.SECONDS);
                    return client;
                }
            });
        }
        catch (Exception ex) {
            Log.e(LOG,ex.getMessage());
        }

    }

    public static void Initialize(Context context) {
        if (mInstance == null) {
            mInstance = new Azure(context);
        } else {
            throw new IllegalStateException("AzureServiceAdapter is already initialized");
        }
    }

    public static Azure getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException("AzureServiceAdapter is not initialized");
        }
        return mInstance;
    }

    public static MobileServiceClient getClient() {
        return mClient;
    }

    // Place any public methods that operate on mClient here.
}
