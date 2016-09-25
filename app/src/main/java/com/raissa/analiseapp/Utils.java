package com.raissa.analiseapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import org.androidannotations.annotations.UiThread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by primelan on 9/8/15.
 */
public class Utils {

    public static boolean isConnected(Context context){
        ConnectivityManager conexao = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo conexaoInfo = conexao.getActiveNetworkInfo();
        if (conexaoInfo == null || !conexaoInfo.isConnected()) {
            return false;
        }
        else return true;
    }

    public static void GpsOn(Context context){
        LocationManager locationManager;
        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps(context);
        }
    }

    public static void buildAlertMessageNoGps(final Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.msg_gps_desativado))
                .setCancelable(false)
                .setPositiveButton("Ativar", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    public static void showToast(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int msgResId){
        Toast.makeText(context,msgResId,Toast.LENGTH_SHORT).show();
    }

    public static void resizeImageFile(File image) {
        Bitmap bimage = BitmapFactory.decodeFile(image.getAbsolutePath());

        int maxSize = 1200,
                outWidth,
                outHeight,
                inWidth = bimage.getWidth(),
                inHeight = bimage.getHeight();

        Log.d("ANALISE APP","width "+inWidth+" height "+inHeight);

        if (inWidth <= maxSize && inHeight <= maxSize)
            return;

        if (inWidth > inHeight) {
            outWidth = maxSize;
            outHeight = (inHeight * maxSize) / inWidth;
        } else {
            outHeight = maxSize;
            outWidth = (inWidth * maxSize) / inHeight;
        }

        try {
            bimage = Bitmap.createScaledBitmap(bimage, outWidth, outHeight, false);
            OutputStream out = new FileOutputStream(image.getAbsolutePath());
            bimage.compress(Bitmap.CompressFormat.JPEG, 90, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String loadJSONFromAsset(Context context, String file) {
        String json = null;
        try {

            InputStream is = context.getAssets().open(file);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

}
