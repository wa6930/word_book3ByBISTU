package com.example.usedsharedpreferences.by.word_book3bybistu.fragement;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.usedsharedpreferences.by.word_book3bybistu.R;

public class ChangeFragment extends Fragment {
    //FragmentManager fragmentManager = MainActivity.getSupportFragmentManager();

    public static void ChangeFragmentTo(FragmentManager manager,Fragment fragment,boolean isShow){
        FragmentTransaction transaction;
        transaction=manager.beginTransaction();
        if(isShow){
            transaction.add(R.id.right_fragment,fragment);
        }
        else{
            //TODO

        }
        transaction.commit();
    }

}
