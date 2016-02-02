package com.raissa.analiseapp.Activities;

import android.app.ProgressDialog;
import android.content.res.TypedArray;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.raissa.analiseapp.MyViewPager;
import com.raissa.analiseapp.R;

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
public class QuestoesActivity extends AppCompatActivity implements QuestaoFragment.InterfaceQuestoes {
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


    ArrayList<ItemQuestao> questoes;

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
        getLocation();

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

    void initListaQuestoes(){
        questoes = new ArrayList<>();
        for(int i=0;i<perguntas.length;i++){
            questoes.add(new ItemQuestao(this,i,perguntas[i],"Qual intensidade?"));
        }
    }

    @Background(delay = 4000)
    void getLocation(){
        ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(this);
        locationProvider.getLastKnownLocation().subscribe(new Action1<Location>() {
            @Override
            public void call(Location location) {
                Log.d("ANALISE APP", "SMART - Lat = " + location.getLatitude() + ", Long = " + location.getLongitude());
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        });
        doneProgress();
    }

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
