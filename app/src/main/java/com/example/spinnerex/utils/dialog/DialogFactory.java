package com.example.spinnerex.utils.dialog;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import java.util.List;
public class DialogFactory {

    public static void showPickerDialg(String title, List<String> items, Context context, DialogInterface.OnClickListener callback){
        if(items == null){
            return;
        }
        CharSequence[] keys = items.toArray(new CharSequence[0]);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setCancelable(true)
                .setItems(keys, callback)
                .create()
                .show();
    }

}
