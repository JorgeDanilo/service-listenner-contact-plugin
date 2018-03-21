package br.com;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.content.Context;
import android.content.Intent;

public class ContactBroadcsat extends CordovaPlugin {

    private static final String TAG = "ContactBroadcsat";
    CordovaInterface mCordova;
    BroadcastReceiver mReceiver;

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        Log.d(TAG, "Initializing MyCordovaPlugin");
        super.initialize(cordova, webView);
        this.mCordova = cordova;
        startContactLookService();
    }

    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {

        return true;
    }

    public void startContactLookService() {
        Context context = this.mCordova.getActivity();
        Intent intent = new Intent(context, ContactWathService.class);
        context.startService(intent);
    }

    public void notifyChange(Context context, String msg) {
        Intent intent = new Intent("contact.action.broadcsat");
        intent.putExtra("contact", msg);
        //TODO: refatorar para usar DTO dos contatos..
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        this.list(context);
    }

    public void list(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("contact.action.broadcsat");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String contact = intent.getExtras().getString("contact");
                Log.d("RECEIVER ", contact);
            }
        };

        String resultData = receiver.getResultData();
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, filter);
    }

}
