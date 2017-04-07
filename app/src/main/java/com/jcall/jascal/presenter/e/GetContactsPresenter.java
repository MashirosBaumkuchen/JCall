package com.jcall.jascal.presenter.e;

import android.content.Context;
import com.jcall.jascal.contract.GetContactsContract;
import com.jcall.jascal.model.GetContactsModel;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Cherie_No.47 on 2017/4/2 12:07.
 * Email jascal@163.com
 */

public class GetContactsPresenter implements GetContactsContract.Presenter, GetContactsModel.OnResponse{
    private GetContactsContract.View view;

    public GetContactsPresenter(GetContactsContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void getContacts(Context context) {
        GetContactsModel getContactsModel = new GetContactsModel(context);
        getContactsModel.setOnResponse(this);
        getContactsModel.getContacts();
    }

    @Override
    public void onGCModelSuccess(ArrayList<HashMap<String,String>> list) {
        view.onGetContactsSuccess(list);
    }

    @Override
    public void onGCModelFail() {

    }
}
