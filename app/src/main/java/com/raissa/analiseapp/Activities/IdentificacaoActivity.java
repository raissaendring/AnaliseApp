package com.raissa.analiseapp.Activities;

import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.raissa.analiseapp.Constants;
import com.raissa.analiseapp.MyPrefs_;
import com.raissa.analiseapp.R;
import com.raissa.analiseapp.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EActivity(R.layout.activity_identificacao)
public class IdentificacaoActivity extends AppCompatActivity{

    @ViewById
    Button btnComecar;
    @ViewById
    EditText nome;
    @ViewById
    EditText matricula;
    @ViewById
    Toolbar toolbar;
    @ViewById
    CheckBox salvar;

    @Pref
    MyPrefs_ prefs;

    @AfterViews
    void init(){
        initToolbar();
        initCredenciais();
    }

    void initToolbar(){
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
    }

    void initCredenciais(){
        if(prefs.credenciaisSalvas().get()){
            salvar.setChecked(true);
            nome.setText(prefs.nomeFiscal().getOr(""));
            matricula.setText(prefs.matriculaFiscal().getOr(""));
        }
    }

    @Click
    void btnComecarClicked(){
        String nomeFiscal = nome.getText().toString();
        String matriculaString = matricula.getText().toString();
        if(nomeFiscal.isEmpty()){
            Toast.makeText(this,R.string.msg_nome_vazio,Toast.LENGTH_SHORT).show();
        }
        else if(matriculaString.isEmpty()){
            Toast.makeText(this,R.string.msg_matricula_vazia,Toast.LENGTH_SHORT).show();
        }else{
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            int mode=0;
            try {
                mode = Settings.Secure.getInt(getContentResolver(),Settings.Secure.LOCATION_MODE);
                Log.d("APP ANALISE","ACCURACY "+mode);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) /*|| mode !=3*/ ) {
                Utils.buildAlertMessageNoGps(this);
            }else {
                if(salvar.isChecked()){
                    prefs.credenciaisSalvas().put(true);
                    prefs.nomeFiscal().put(nomeFiscal);
                    prefs.matriculaFiscal().put(matriculaString);
                }else{
                    prefs.credenciaisSalvas().put(false);
                    prefs.nomeFiscal().remove();
                    prefs.matriculaFiscal().remove();
                }
                QuestoesActivity_.intent(this).nomeFiscal(nomeFiscal).matriculaFiscal(matriculaString).start();
            }
        }
    }


}
