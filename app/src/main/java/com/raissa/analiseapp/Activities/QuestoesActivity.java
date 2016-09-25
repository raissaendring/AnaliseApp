package com.raissa.analiseapp.Activities;

import android.app.ProgressDialog;
import android.content.res.TypedArray;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.raissa.analiseapp.Constants;
import com.raissa.analiseapp.Fragments.QuestaoFragment;
import com.raissa.analiseapp.Fragments.QuestaoFragment_;
import com.raissa.analiseapp.ItemQuestao;
import com.raissa.analiseapp.MyViewPager;
import com.raissa.analiseapp.PaginaQuestao;
import com.raissa.analiseapp.Questoes;
import com.raissa.analiseapp.R;
import com.raissa.analiseapp.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;

import java.util.ArrayList;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.functions.Action1;

@EActivity(R.layout.activity_questoes)
public class QuestoesActivity extends AppCompatActivity implements QuestaoFragment.InterfaceQuestoes, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    @ViewById
    transient MyViewPager viewPager;
    @ViewById
    Toolbar toolbar;

    @StringArrayRes
    String[] perguntas;


    @Extra
    String matriculaFiscal;
    @Extra
    String nomeFiscal;

    GoogleApiClient googleApiClient;


    ArrayList<ItemQuestao> questoes;
    ArrayList<PaginaQuestao> paginas;

    int position;

    ProgressDialog progressDialog;

    double latitude;
    double longitude;

    TypedArray dicas;
    TypedArray imagens;

    public QuestoesActivity(){}

    @AfterViews
    void init(){
        dicas = getResources().obtainTypedArray(R.array.dicas);
        imagens = getResources().obtainTypedArray(R.array.imagens);
        progressDialog = new ProgressDialog(this);
        position=0;

        showProgress();
        //getLocation();
        connectGoogleApi();

        initListaQuestoes();
        initToolbar();

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    void initToolbar(){
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
    }

    private synchronized void connectGoogleApi(){
        googleApiClient = new GoogleApiClient.Builder(this,this,this).addApi(LocationServices.API).build();
        googleApiClient.connect();
    }

    void initListaQuestoes(){
        questoes = new ArrayList<>();
        for(int i=0;i<perguntas.length;i++){
            questoes.add(new ItemQuestao(this,i,perguntas[i],"Qual intensidade?"));
        }


        //NEW
        /*paginas = new ArrayList<>();
        PaginaQuestao pagina = new PaginaQuestao();
        String jsonQuestoes = Utils.loadJSONFromAsset(this,"questoes.json");

        Gson gson = new Gson();
        Questoes questoes = gson.fromJson(jsonQuestoes, Questoes.class);
        if(questoes!=null){
            Log.d(Constants.LOG,questoes.getPaginas().size() + " paginas");
        }*/

    }

    /*@Background(delay = 4000)
    void getLocation(){
        Log.d(Constants.LOG, "getLocation: ");
        ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(this);
        locationProvider.getLastKnownLocation().subscribe(new Action1<Location>() {
            @Override
            public void call(Location location) {
                Log.d(Constants.LOG, "SMART - Lat = " + location.getLatitude() + ", Long = " + location.getLongitude());
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        });
        doneProgress();
    }*/

    @UiThread
    void showProgress(){
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Encontrando Localização...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @UiThread
    void doneProgress(){
        progressDialog.dismiss();
    }


    @Override
    public void onProxClicked(ItemQuestao.respostasP respostaP, ItemQuestao.respostasS respostaS, int situacao, int position) {
        questoes.get(position).setRespostaPrimaria(respostaP);
        questoes.get(position).setRespostaSecundaria(respostaS);
        questoes.get(position).setSituacao(situacao);
    }

    @Override
    public void onProxClicked(float tamanho, int situacao, int position) {
        questoes.get(position).setTamanho(tamanho);
        questoes.get(position).setSituacao(situacao);
    }

    @Override
    public void finalizar() {
        PictureActivity_.intent(this).listQuestoes(questoes)
                .latitude(latitude)
                .longitude(longitude)
                .nomeFiscal(nomeFiscal)
                .matriculaFiscal(matriculaFiscal)
                .start();
        //FinalActivity_.intent(this).questoes(questoes).start();
    }

    @Override
    public void nextPage() {
        if(position!=12) {
            position++;
            viewPager.setCurrentItem(position);
        }
    }

    @Override
    public void previousPage() {
        if(position!=0) {
            position--;
            viewPager.setCurrentItem(position);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        doneProgress();
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if(location!=null){
            Log.d(Constants.LOG, "SMART - Lat = " + location.getLatitude() + ", Long = " + location.getLongitude());
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }else{
            Log.d(Constants.LOG, "onConnected: " + "location null");
            showToast(R.string.msg_location_fail);
            finish();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        doneProgress();
        Log.d(Constants.LOG, "onConnectionSuspended: ");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        doneProgress();
        showToast(R.string.msg_location_fail);
        finish();
        Log.d(Constants.LOG, "onConnectionFailed: ");
    }

    @UiThread
    void showToast(int resId){
        Toast.makeText(this,resId,Toast.LENGTH_LONG).show();
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return questoes.size();
        }


        @Override
        public Fragment getItem(int position) {
            Log.d("ANALISE APP","IMAGENS ARRAY "+imagens.length()+ " POSITION " + position);
            return QuestaoFragment_.builder().listQuestoes(questoes)
                    .position(position)
                    .dicas(getResources().getStringArray(dicas.getResourceId(position,0)))
                    .imgId(imagens.getResourceId(position,0))
                    .build();
        }


    }


}
