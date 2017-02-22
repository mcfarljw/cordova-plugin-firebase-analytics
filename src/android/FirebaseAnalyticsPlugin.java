package com.jernung.plugins.firebase.analytics;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.jernung.plugins.firebase.PluginUtils;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FirebaseAnalyticsPlugin extends CordovaPlugin {

    private static final String PLUGIN_NAME = "FirebaseAnalyticsPlugin";

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(cordova.getActivity());
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("logEvent".equals(action)) {
            String eventName = args.getString(0);
            JSONObject eventParams = args.getJSONObject(1);
            analyticsLogEvent(eventName, eventParams);
            return true;
        }
        if ("setUserId".equals(action)) {
            String userId = args.getString(0);
            analyticsSetUserId(userId);
            return true;
        }

        return false;
    }

    private void analyticsLogEvent(final String eventName, final JSONObject eventParams) {
        try {
            final Bundle params = PluginUtils.jsonToBundle(eventParams);

            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    mFirebaseAnalytics.logEvent(eventName, params);
                }
            });
        } catch (Exception error) {
            Log.e(PLUGIN_NAME, error.getMessage());
        }
    }

    private void analyticsSetUserId(final String userId) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                mFirebaseAnalytics.setUserId(userId);
            }
        });
    }

}
