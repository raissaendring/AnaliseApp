package com.raissa.analiseapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
        builder.setMessage("O GPS do seu dispositivo se encontra desativado. Ative-o para continuar.")
                .setCancelable(false)
                .setPositiveButton("Ativar", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        //((Activity)context).startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),1);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
