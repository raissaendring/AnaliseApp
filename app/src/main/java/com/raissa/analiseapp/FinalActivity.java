package com.raissa.analiseapp;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity(R.layout.activity_final)
public class FinalActivity extends Activity {

    @Extra
    ArrayList<ItemQuestao> questoes;

    @ViewById
    ListView listView;

    @AfterViews
    void init(){
        QuickAdapter<ItemQuestao> adapter = new QuickAdapter<ItemQuestao>(this,R.layout.item_resposta) {
            @Override
            protected void convert(BaseAdapterHelper helper, ItemQuestao item) {
                Log.d("ANALISE APP","ID " + item.id +"  PERGUNTA " + item.getPerguntaPrincipal());
                if(item.id>0) {
                    Log.d("ANALISE APP","Entrou id>0, id="+item.id);
                    helper.setText(R.id.perguntaP, item.getPerguntaPrincipal());
                    helper.setText(R.id.respostaP, item.getRespostaPrimaria().toString());
                    if (item.getRespostaPrimaria() == ItemQuestao.respostasP.Sim) {
                        helper.setVisible(R.id.perguntaS, true);
                        helper.setVisible(R.id.respostaS, true);
                        helper.setText(R.id.perguntaS, "Qual intensidade?");
                        helper.setText(R.id.respostaS, item.getRespostaSecundaria().toString());
                    }
                    helper.setText(R.id.situacao, String.valueOf(item.getSituacao()));
                }else{
                    Log.d("ANALISE APP","Entrou id<=0, id="+item.id);
                    helper.setVisible(R.id.layout_pergunta_1,true);
                    helper.setVisible(R.id.layout_default,false);
                    helper.setText(R.id.tamanho,String.valueOf(item.getTamanho()));
                }
            }
        };
        adapter.addAll(questoes);
        listView.setAdapter(adapter);
    }


}
