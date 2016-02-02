package com.raissa.analiseapp.Activities;

import android.location.LocationManager;
import android.location.LocationProvider;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.raissa.analiseapp.R;
import com.raissa.analiseapp.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_identificacao)
public class IdentificacaoActivity extends AppCompatActivity {

    @ViewById
    Button btnComecar;
    @ViewById
    EditText nome;
    @ViewById
    EditText matricula;
    @ViewById
    Toolbar toolbar;

    @AfterViews
    void init(){
        initToolbar();
    }

    void initToolbar(){
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
    }

    @Click
    void btnComecarClicked(){
        String nomeFiscal = nome.getText().toString();
        String matriculaString = matricula.getText().toString();
        if(nomeFiscal.isEmpty()){
            Toast.makeText(this,R.string.msg_nome_vazio,Toast.LENGTH_SHORT).show();
        }
        if(matriculaString.isEmpty()){
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
            }else QuestoesActivity_.intent(this).nomeFiscal(nomeFiscal).matriculaFiscal(matriculaString).start();
        }
    }

}
