package br.com;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

class MyContentObserver extends ContentObserver {

    private Context context;

    public MyContentObserver(Handler handler, Context context) {
        super(handler);
        this.context = context;
        Toast.makeText(context, "MyContentObserver constructor", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange);
        if(!selfChange) {
            try {
                ContentResolver cr = context.getContentResolver();
                Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToLast();
                    String contactName = null, photo = null, contactNumber = null;
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                    if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);

                        if (pCur != null) {
                            while (pCur.moveToNext()) {
                                contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                                if (contactNumber != null && contactNumber.length() > 0) {
                                    contactNumber = contactNumber.replace(" ", "") ;
                                }

                                contactName = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                                String msg = "Name: " + contactName + " Contact No. : " + contactNumber;

                                Log.d("Contact", msg);

                                //Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                                ContactBroadcsat contactBroadcsat = new ContactBroadcsat();
                                contactBroadcsat.notifyChange(context, msg);
                            }

                            pCur.close();

                        }
                    }

                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}