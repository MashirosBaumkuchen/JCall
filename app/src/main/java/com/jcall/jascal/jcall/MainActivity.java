package com.jcall.jascal.jcall;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.jcall.jascal.view.e.CallFragment;
import com.jcall.jascal.view.e.ContactsFragment;

public class MainActivity extends AppCompatActivity{
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ContactsFragment contactsFragment;
    private CallFragment callFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(null == contactsFragment){
            contactsFragment = new ContactsFragment();
        }
        if(null == callFragment){
            callFragment = new CallFragment();
        }
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exit);
        fragmentTransaction.replace(R.id.main_content,contactsFragment);
        fragmentTransaction.commit();
    }

    public void turnToCall() {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fragment_slide_right_enter, R.anim.fragment_slide_right_exit);
        fragmentTransaction.replace(R.id.main_content, callFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void turnToContacts() {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exit);
        fragmentTransaction.replace(R.id.main_content, contactsFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

}
