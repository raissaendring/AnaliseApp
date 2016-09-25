package com.raissa.analiseapp.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.raissa.analiseapp.ItemQuestao;
import com.raissa.analiseapp.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.IntArrayRes;

import java.util.ArrayList;

@EFragment
public class QuestaoFragment extends Fragment {
    @FragmentArg
    Integer position;
    @FragmentArg
    ArrayList<ItemQuestao> listQuestoes;
    @FragmentArg
    String[] dicas;
    @FragmentArg
    int imgId;

    @ViewById
    TextView pergunta;
    @ViewById
    RadioGroup respPrimaria;
    @ViewById
    TextView perguntaSecundaria;
    @ViewById
    RadioGroup respSecundaria;
    @ViewById
    RadioGroup respPneus;
    @ViewById
    RadioGroup resp_9_1;
    @ViewById
    RadioGroup resp_9_2;
    @ViewById
    RadioGroup resp_9_3;
    @ViewById
    RadioGroup resp_9_4;
    @ViewById
    Button botaoAnt;
    @ViewById
    Button botaoProx;
    @ViewById
    Button botaoFinalizar;
    @ViewById
    EditText tamanho;
    @ViewById
    LinearLayout layoutMaisPerguntas;
    @ViewById
    LinearLayout layoutDicas;
    @ViewById
    ImageView imagem;
    @ViewById
    EditText m1;
    @ViewById
    EditText m2;
    @ViewById
    TextView resultCalc;

    @IntArrayRes
    int[] classificacoes;

    boolean respondido;


    public InterfaceQuestoes mInterface;

    public interface InterfaceQuestoes{
        void onProxClicked(ItemQuestao.respostasP respostaP, ItemQuestao.respostasS respostaS, int situacao, int position);
        void onProxClicked(float tamanho, int situacao, int position);
        void finalizar();
        void nextPage();
        void previousPage();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        if(position==0) return  inflater.inflate(R.layout.fragment_questao_1,container,false);
        if(position==4) return inflater.inflate(R.layout.fragment_questao_5,container,false);
        if(position==8) return inflater.inflate(R.layout.fragment_questao_9,container,false);
        return inflater.inflate(R.layout.fragment_questao,container,false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mInterface = (InterfaceQuestoes) activity;
    }

    @AfterViews
    void init(){
        inserirDicas();
        inserirImagem();
        if(position==8){
            respPrimaria.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.sim) {
                        layoutMaisPerguntas.setVisibility(View.VISIBLE);
                    } else {
                        layoutMaisPerguntas.setVisibility(View.GONE);
                    }
                }
            });
        }
        else if(position>0) {
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


            if (position == 12) {
                botaoProx.setVisibility(View.GONE);
                botaoFinalizar.setVisibility(View.VISIBLE);
            }
        }else if (position == 0) {
            botaoAnt.setVisibility(View.GONE);
            m1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.d("ANALISE APP","1 - n1 "+s.toString()+" n2 "+m2.getText().toString());
                    float n1 = !s.toString().isEmpty() ? Float.valueOf(s.toString()) : 0;
                    float n2 = !m2.getText().toString().isEmpty() ? Float.valueOf(m2.getText().toString()) : 0;
                    float result = n1 * n2;
                    resultCalc.setText(String.valueOf(result));
                    tamanho.setText(String.valueOf(result));
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            m2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.d("ANALISE APP","2 - n1 "+m1.getText().toString()+" n2 "+s.toString());
                    float n2 = !s.toString().isEmpty() ? Float.valueOf(s.toString()) : 0;
                    float n1 = !m1.getText().toString().isEmpty() ? Float.valueOf(m1.getText().toString()) : 0;
                    float result = n1 * n2;
                    resultCalc.setText(String.valueOf(result) + "m2");
                    tamanho.setText(String.valueOf(result));
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    void inserirDicas(){
        for(String dica : dicas){
            LinearLayout dicaLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.item_dica,null);
            ((TextView)dicaLayout.findViewById(R.id.dica)).setText(dica);
            layoutDicas.addView(dicaLayout);
        }
    }

    void inserirImagem(){
        if(imgId!=0) imagem.setImageResource(imgId);
    }

    @Click
    void botaoProxClicked(){
        if(foiRespondido()) {
            atualizaQuestao();
        }else{
            Toast.makeText(getActivity(),R.string.pergunta_incompleta,Toast.LENGTH_SHORT).show();
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
        }else{
            Toast.makeText(getActivity(),R.string.pergunta_incompleta,Toast.LENGTH_SHORT).show();
        }
    }

    boolean foiRespondido() {
        switch (position) {
            case 8:
                if (respPrimaria.getCheckedRadioButtonId() == -1) return false;
                else {
                    if (respPrimaria.getCheckedRadioButtonId() == R.id.nao) {
                        return true;
                    } else {
                        if (resp_9_1.getCheckedRadioButtonId() != -1 && resp_9_2.getCheckedRadioButtonId() != -1 && resp_9_3.getCheckedRadioButtonId() != -1 && resp_9_4.getCheckedRadioButtonId() != -1) {
                            return true;
                        } else return false;
                    }
                }
            case 4:
                if (respPneus.getCheckedRadioButtonId() == -1) {
                    return false;
                } else {
                    if (respPrimaria.getCheckedRadioButtonId() == R.id.nao) {
                        return true;
                    } else {
                        if (respSecundaria.getCheckedRadioButtonId() != -1) {
                            return true;
                        } else return false;
                    }
                }
            case 0:
                if (tamanho.getText().toString().isEmpty() || Float.valueOf(tamanho.getText().toString())==0) return false;
                else return true;
            default:
                if (respPrimaria.getCheckedRadioButtonId() == R.id.nao) {
                    return true;
                } else {
                    if (respSecundaria.getCheckedRadioButtonId() != -1) {
                        return true;
                    } else return false;
                }
        }
    }

    void atualizaQuestao(){
        int situacao;
        ItemQuestao.respostasP respostaP;
        ItemQuestao.respostasS respostaS;

        if(position==8){
            if(respPrimaria.getCheckedRadioButtonId()==R.id.nao){
                respostaP = ItemQuestao.respostasP.Nao;
                respostaS = ItemQuestao.respostasS.Indiferente;
                situacao = 1;
                mInterface.onProxClicked(respostaP,respostaS,situacao,8);
                respostaP= ItemQuestao.respostasP.Indiferente;
                respostaS= ItemQuestao.respostasS.Indiferente;
                situacao=0;
                mInterface.onProxClicked(respostaP,respostaS,situacao,9);
                mInterface.onProxClicked(respostaP,respostaS,situacao,10);
                mInterface.onProxClicked(respostaP,respostaS,situacao,11);
                mInterface.onProxClicked(respostaP, respostaS, situacao, 12);
            }else{
                respostaP = ItemQuestao.respostasP.Sim;
                respostaS = ItemQuestao.respostasS.Indiferente;
                situacao = classificacoes[8*2 -2];
                mInterface.onProxClicked(respostaP,respostaS,situacao,8);
                //Pergunta 9/1
                if(resp_9_1.getCheckedRadioButtonId()==R.id.sim1){
                    respostaP = ItemQuestao.respostasP.Sim;
                    respostaS = ItemQuestao.respostasS.Indiferente;
                    situacao = classificacoes[9*2 -2];
                }else{
                    respostaP = ItemQuestao.respostasP.Nao;
                    respostaS = ItemQuestao.respostasS.Indiferente;
                    situacao = 1;
                }
                mInterface.onProxClicked(respostaP,respostaS,situacao,9);
                //Pergunta 9/2
                if(resp_9_2.getCheckedRadioButtonId()==R.id.sim2){
                    respostaP = ItemQuestao.respostasP.Sim;
                    respostaS = ItemQuestao.respostasS.Indiferente;
                    situacao = classificacoes[10*2 -2];
                }else{
                    respostaP = ItemQuestao.respostasP.Nao;
                    respostaS = ItemQuestao.respostasS.Indiferente;
                    situacao = 1;
                }
                mInterface.onProxClicked(respostaP,respostaS,situacao,10);
                //Pergunta 9/3
                if(resp_9_3.getCheckedRadioButtonId()==R.id.sim3){
                    respostaP = ItemQuestao.respostasP.Sim;
                    respostaS = ItemQuestao.respostasS.Indiferente;
                    situacao = classificacoes[11*2 -2];
                }else{
                    respostaP = ItemQuestao.respostasP.Nao;
                    respostaS = ItemQuestao.respostasS.Indiferente;
                    situacao = 1;
                }
                mInterface.onProxClicked(respostaP,respostaS,situacao,11);
                //Pergunta 9/4
                if(resp_9_4.getCheckedRadioButtonId()==R.id.sim4){
                    respostaP = ItemQuestao.respostasP.Sim;
                    respostaS = ItemQuestao.respostasS.Indiferente;
                    situacao = classificacoes[12*2 -2];
                }else{
                    respostaP = ItemQuestao.respostasP.Nao;
                    respostaS = ItemQuestao.respostasS.Indiferente;
                    situacao = 1;
                }
                mInterface.onProxClicked(respostaP,respostaS,situacao,12);
            }
            mInterface.finalizar();
        }
        else if(position>0) {
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
            if(position==4) {
                if (respPneus.getCheckedRadioButtonId() == R.id.simP) situacao += 15;
                else situacao += 1;
            }
            mInterface.onProxClicked(respostaP, respostaS, situacao, position);
            mInterface.nextPage();
        }else{
            float tamFloat = Float.valueOf(tamanho.getText().toString());
            if (tamFloat >= 12) situacao=2;
            else situacao=1;
            mInterface.onProxClicked(tamFloat, situacao, position);
            mInterface.nextPage();
        }
    }




}
