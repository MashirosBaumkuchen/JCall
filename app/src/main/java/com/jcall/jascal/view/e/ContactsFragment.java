package com.jcall.jascal.view.e;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.jcall.jascal.contract.GetContactsContract;
import com.jcall.jascal.jcall.Constant;
import com.jcall.jascal.jcall.R;
import com.jcall.jascal.presenter.e.GetContactsPresenter;
import java.util.ArrayList;
import java.util.HashMap;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Cherie_No.47 on 2017/4/2 12:13.
 * Email jascal@163.com
 */

public class ContactsFragment extends Fragment implements GetContactsContract.View {
    private String[] initPermissions = {Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE};
    private GetContactsContract.Presenter presenter;

    @Bind(R.id.contacts_content)
    CoordinatorLayout coordinatorLayout;

    @OnClick(R.id.fab_call)
    void turnToCall(){
        Snackbar.make(coordinatorLayout, "turnToCall", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        ButterKnife.bind(this, view);
        new GetContactsPresenter(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int i = ContextCompat.checkSelfPermission(this.getContext(), initPermissions[0]);
            if (i != PackageManager.PERMISSION_GRANTED) {
                showDialogTipUserRequestPermission();
            } else {
                presenter.getContacts(this.getActivity().getBaseContext());
            }
        }
        return view;
    }

    private void showDialogTipUserRequestPermission() {
        new AlertDialog.Builder(this.getContext())
                .setTitle("等一下")
                .setMessage("看看你的联系人呗！")
                .setPositiveButton("好啊", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissions(initPermissions, 321);
                    }
                })
                .setNegativeButton("西奈", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(false).show();
    }

    @Override// 用户权限 申请 的回调方法
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 321) {
            if (grantResults.length > 0) {
                if (permissions[0].equals(initPermissions[0])) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        presenter.getContacts(this.getActivity().getBaseContext());
                    } else {
                        Snackbar.make(coordinatorLayout, "permission granted denied", Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(coordinatorLayout, "permission granted denied", Snackbar.LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(coordinatorLayout, "permission granted denied", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void setPresenter(GetContactsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Bind(R.id.listView)
    ListView listView;

    @Override
    public void onGetContactsSuccess(final ArrayList<HashMap<String, String>> list) {
        ListAdapter listAdapter = new SimpleAdapter(this.getContext(), list, R.layout.list_item,
                new String[]{Constant.CONTACTS_NAME, Constant.CONTACTS_PHONENUM}, new int[]{R.id.phoneNum, R.id.name});
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String phoneNum = list.get(position).get(Constant.CONTACTS_PHONENUM);
                if (phoneNum != null && !phoneNum.equals("")) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
                    if (ActivityCompat.checkSelfPermission(ContactsFragment.this.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Snackbar.make(coordinatorLayout, "call denied", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    ContactsFragment.this.getActivity().startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onGetContactsFailed() {

    }

}
