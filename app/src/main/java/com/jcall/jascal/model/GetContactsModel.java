package com.jcall.jascal.model;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.jcall.jascal.jcall.Constant;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Cherie_No.47 on 2017/4/4 20:03.
 * Email jascal@163.com
 */

public class GetContactsModel {
    private OnResponse onResponse;
    private Context context;

    public GetContactsModel(Context context) {
        this.context = context;
    }

    public void getContacts() {
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        ArrayList<HashMap<String,String>> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            HashMap<String, String> map = new HashMap<>();
            map.put(Constant.CONTACTS_NAME, cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            map.put(Constant.CONTACTS_PHONENUM, cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            list.add(map);
        }
        onResponse.onGCModelSuccess(list);
    }

    public interface OnResponse {
        void onGCModelSuccess(ArrayList<HashMap<String,String>> list);

        void onGCModelFail();
    }

    public void setOnResponse(OnResponse onResponse) {
        this.onResponse = onResponse;
    }
}
