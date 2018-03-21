package br.com;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.provider.ContactsContract;
import android.widget.Toast;


public class ContactWathService extends Service {

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    public final class ServiceHandler extends Handler {

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            try {
                startContactObserver();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void startContactObserver() {
        try {
            Toast.makeText(getApplicationContext(), "ContactWatchService Started", Toast.LENGTH_SHORT).show();
            getApplication().getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI, true, new MyContentObserver(new Handler(), getApplicationContext()));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        try {

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}