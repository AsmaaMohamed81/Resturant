package com.alatheer.menu.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import com.alatheer.menu.R;

/**
 * Created by elashry on 03/09/2018.
 */

public class Common {
    public static void closeKeyboard(View view, Context context)
    {
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    public static ProgressDialog createProgressDialog(Context context , String msg)
    {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage(msg);
        ProgressBar bar = new ProgressBar(context);
        Drawable drawable = bar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        dialog.setIndeterminateDrawable(drawable);

        return dialog;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getImagePath(Context context , Uri uri)
    {
        int currentApiVersion;

        try {

            currentApiVersion = Build.VERSION.SDK_INT;
        }catch (NumberFormatException e)
        {
            currentApiVersion = 3;
        }

        if (currentApiVersion>= Build.VERSION_CODES.KITKAT)
        {
            String path="";
            String wholeID = DocumentsContract.getDocumentId(uri);
            String id = wholeID.split(":")[1];
            String sel = MediaStore.Images.Media._ID+"=?";

            String [] column = {MediaStore.Images.Media.DATA};

            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,column,sel,new String[]{id},null);
            int column_index = cursor.getColumnIndexOrThrow(column[0]);

            if (cursor.moveToFirst())
            {
                path = cursor.getString(column_index);

            }
            cursor.close();
            return path;
        }else if (currentApiVersion<=Build.VERSION_CODES.HONEYCOMB_MR2 && currentApiVersion>=Build.VERSION_CODES.HONEYCOMB)
        {
            String path="";
            String [] proj = {MediaStore.Images.Media.DATA};
            CursorLoader cursorLoader = new CursorLoader(context,uri,proj,null,null,null);
            Cursor cursor = cursorLoader.loadInBackground();
            if (cursor!=null)
            {
                int index = cursor.getColumnIndexOrThrow(proj[0]);
                cursor.moveToFirst();
                path = cursor.getString(index);
            }
            return path;
        }else
            {
                String path = "";
                String [] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = context.getContentResolver().query(uri,proj,null,null,null);
                int index = cursor.getColumnIndexOrThrow(proj[0]);
                cursor.moveToFirst();
                path = cursor.getString(index);
                return path;
            }
    }

}
