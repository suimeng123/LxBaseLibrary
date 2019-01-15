package com.base.library.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * com.lx.baselibrary.utils
 * LxBaseLibrary
 * Created by lixiao2
 * 2018/12/20.
 */

public class ToastUtil {
    public static Toast mToast;
    public static void showToast(Context context, String message) {
        if (mToast == null) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setText(message);
        }
        mToast.show();
    }

    public static void hideToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
