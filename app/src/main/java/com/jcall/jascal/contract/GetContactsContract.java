package com.jcall.jascal.contract;

import android.app.Activity;
import android.content.Context;

import com.jcall.jascal.presenter.IBasePresenter;
import com.jcall.jascal.view.IBaseView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Cherie_No.47 on 2017/4/2 11:56.
 * Email jascal@163.com
 */

public class GetContactsContract {

    public interface View extends IBaseView<Presenter>{
        void onGetContactsSuccess(ArrayList<HashMap<String,String>> str);
        void onGetContactsFailed();
    }

    public interface Presenter extends IBasePresenter{
        void getContacts(Context activity);
    }
}
