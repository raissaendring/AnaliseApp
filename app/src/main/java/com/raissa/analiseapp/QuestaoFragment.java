package com.raissa.analiseapp;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.IntArrayRes;

import java.util.ArrayList;

@EFragment(R.layout.fragment_questao)
public class QuestaoFragment extends Fragment {
   @FragmentArg
    Integer position;
    @FragmentArg
    ArrayList<ItemQuestao> listQuestoes;
    //@FragmentArg
    //QuestoesActivity callingActivity;

    @ViewById
    TextView pergunta;
    @ViewById
    RadioGroup respPrimaria;
    @ViewById
    TextView perguntaSecundaria;
    @ViewById
    RadioGroup respSecundaria;
    @ViewById
    Button botaoAnt;
    @ViewById
    Button botaoProx;
    @ViewById
    Button botaoFinalizar;
    @ViewById
    LinearLayout layoutPerguntaTam;
    @ViewById
    LinearLayout layoutDefault;
    @ViewById
    EditText tamanho;

    @IntArrayRes
    int[] classificacoes;

    boolean respondido;


    private InterfaceQuestoes mInterface;

    interface InterfaceQuestoes{
        void onProxClicked(ItemQuestao.respostasP respostaP, ItemQuestao.respostasS respostaS, int situacao, int position);
        void onProxClicked(float tamanho, int situacao, int position);
        void finalizar();
        void nextPage();
        void previousPage();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mInterface = (InterfaceQuestoes) activity;
    }

    @AfterViews
    void init(){
        if(position>0) {
            respondido = false;
            pergunta.setText(position + 1 + " - " + listQuestoes.get(position).getPerguntaPrincipal());
            respPrimaria.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    respondido = true;
                    if (checkedId == R.id.sim) {
                        perguntaSecundaria.setVisibility(View.VISIBLE);
                        respSecundaria.setVisibility(View.VISIBLE);
                    } else {
                        perguntaSecundaria.setVisibility(View.GONE);
                        respSecundaria.setVisibility(View.GONE);
                    }
                }
            });

            if (position == 0) botaoAnt.setVisibility(View.GONE);
            if (position == 12) {
                botaoProx.setVisibility(View.GONE);
                botaoFinalizar.setVisibility(View.VISIBLE);
            }
        }else{
            layoutDefault.setVisibility(View.GONE);
            layoutPerguntaTam.setVisibility(View.VISIBLE);
        }
    }

    @Click
    void botaoProxClicked(){
        if(foiRespondido()) {
            atualizaQuestao();
            mInterface.nextPage();
        }else{
            Toast.makeText(getActivity(),"Responda a pergunta completamente",Toast.LENGTH_SHORT).show();
        }
    }

    @Click
    void botaoAntClicked(){
        mInterface.previousPage();
    }

    @Click
    void botaoFinalizarClicked(){
        if(foiRespondido()){
            atualizaQuestao();
            mInterface.finalizar();
            //FinalActivity_.intent(this).questoes(listQuestoes).start();
        }else{
            Toast.makeText(getActivity(),"Responda a pergunta completamente",Toast.LENGTH_SHORT).show();
        }
    }

    boolean foiRespondido(){
        if(position>0) {
            if (respPrimaria.getCheckedRadioButtonId() == R.id.nao) {
                return true;
            } else {
                if (respSecundaria.getCheckedRadioButtonId() != -1) {
                    return true;
                } else return false;
            }
        }else{
            if (tamanho.getText().toString().isEmpty()) return false;
            else return true;
        }
    }

    void atualizaQuestao(){
        int situacao;
        if(position>0) {
            ItemQuestao.respostasP respostaP;
            ItemQuestao.respostasS respostaS;
            if (respPrimaria.getCheckedRadioButtonId() == R.id.sim) {
                respostaP = ItemQuestao.respostasP.Sim;
                if (respSecundaria.getCheckedRadioButtonId() == R.id.muito) {
                    situacao = classificacoes[(position * 2 + 1) - 2];
                    respostaS = ItemQuestao.respostasS.Muito;
                } else {
                    situacao = classificacoes[position * 2 - 2];
                    respostaS = ItemQuestao.respostasS.Pouco;
                }
            } else {
                situacao = 1;
                respostaP = ItemQuestao.respostasP.Nao;
                respostaS = ItemQuestao.respostasS.Indiferente;
            }
            mInterface.onProxClicked(respostaP, respostaS, situacao, position);
        }else{
            float tamFloat = Float.valueOf(tamanho.getText().toString());
            if (tamFloat >= 12) situacao=2;
            else situacao=1;
            mInterface.onProxClicked(tamFloat, situacao, position);
        }
    }




}
