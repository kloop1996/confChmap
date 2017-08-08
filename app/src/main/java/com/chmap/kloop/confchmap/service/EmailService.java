package com.chmap.kloop.confchmap.service;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.chmap.kloop.confchmap.Constants;

/**
 * Created by kloop1996 on 13.08.2016.
 */
public class EmailService {
    public static void composeEmail(Context context) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, Constants.SUPPORT_EMAIL);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Chmap");
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
}
