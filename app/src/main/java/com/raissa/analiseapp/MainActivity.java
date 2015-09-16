package com.raissa.analiseapp;

import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById
    Button btnComecar;
    @ViewById
    EditText nome;

    @Click
    void btnComecarClicked(){
        String nomeFiscal = nome.getText().toString();
        if(nomeFiscal.isEmpty()){
            Toast.makeText(this,R.string.msg_nome_vazio,Toast.LENGTH_SHORT).show();
        }else{
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                Utils.buildAlertMessageNoGps(this);
            }else QuestoesActivity_.intent(this).nomeFiscal(nomeFiscal).start();
        }
    }

}
