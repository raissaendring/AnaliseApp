package com.raissa.analiseapp.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.raissa.analiseapp.Constants;
import com.raissa.analiseapp.DataBase.Banco;
import com.raissa.analiseapp.ItemCadastro;
import com.raissa.analiseapp.R;
import com.raissa.analiseapp.Rest.Api;
import com.raissa.analiseapp.Rest.CadastroAreaRequest;
import com.raissa.analiseapp.Rest.MyErrorHandler;
import com.raissa.analiseapp.Utils;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.core.io.FileSystemResource;

import java.util.ArrayList;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewById
    Toolbar toolbar;
    @ViewById
    Button btnIniciar;
    @ViewById
    Button btnEnviar;

    Banco bd;
    ProgressDialog progress;

    @RestService
    Api api;
    @Bean
    MyErrorHandler errorHandler;

    @AfterViews
    void init() {
        bd = new Banco(this);
        progress = new ProgressDialog(this);
        initToolbar();
    }

    @AfterInject
    void afterInject() {
        api.setRestErrorHandler(errorHandler);
    }

    void initToolbar() {
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
    }

    @Click
    void btnIniciarClicked() {
        IdentificacaoActivity_.intent(this).start();
    }

    @Click
    void btnEnviarClicked() {
        if (bd.countCadastros() == 0) {
            Utils.showToast(this, R.string.msg_zero_cadastros_salvos);
        } else if (!Utils.isConnected(this)) {
            Utils.showToast(this, R.string.msg_sem_conexao);
        } else {
            ArrayList<ItemCadastro> cadastros = bd.todosCadastros();
            for (ItemCadastro cadastro : cadastros) {
                try {
                    enviarArea(cadastro);
                    //enviarEmailBackground(cadastro);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Background
    void enviarArea(ItemCadastro cadastro) {
        showProgress();
        FileSystemResource file = new FileSystemResource(cadastro.getFoto());
        String response = api.saveArea(new CadastroAreaRequest(cadastro.getLatitude(), cadastro.getLongitude(), cadastro.getValor(), cadastro.getNome(), cadastro.getMatricula(), cadastro.getReferencia(), file));
        doneProgress();

        if (response != null) {
            bd.deletar(cadastro.getId());
            doneProgress();
            showToast(R.string.msg_enviar_success);
        } else {
            showToast(R.string.msg_enviar_fail);
        }

        Log.d(Constants.LOG, "enviarArea: response = " + response);
    }

//    @Background
//    void enviarEmailBackground(ItemCadastro cadastro){
//        showProgress();
//        String body = getString(R.string.email_msg);
//        body += "\n\nNome do Fiscal: " + cadastro.getNome();
//        body += "\nMatrícula: " + cadastro.getMatricula();
//        Mail mail = new Mail(Constants.EMAIL,Constants.SENHA);
//        String[] to = {Constants.EMAIL};
//        mail.setTo(to);
//        mail.setBody(body);
//        mail.setSubject(getResources().getString(R.string.email_subject));
//        mail.setFrom(Constants.EMAIL);
//        try {
//            mail.addAttachment(cadastro.getTxt());
//            mail.addAttachment(cadastro.getTabela());
//            mail.addAttachment(cadastro.getFoto());
//        } catch (Exception e) {
//            Log.e("ANALISE APP","ATTACHMENT EXCEPTION");
//            e.printStackTrace();
//        }
//        try {
//            if(mail.send()){
//                bd.deletar(cadastro.getId());
//                doneProgress();
//                showToast(R.string.msg_email_enviado);
//                MainActivity_.intent(this).start();
//            }
//            else{
//                doneProgress();
//                showToast(R.string.msg_email_n_enviado);
//            }
//        } catch (Exception e) {
//            doneProgress();
//            showToast(R.string.msg_email_n_enviado);
//            e.printStackTrace();
//        }
//    }

    @UiThread
    void showProgress() {
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage(getString(R.string.msg_enviando) + getString(R.string.msg_demora));
        progress.show();
    }

    @UiThread
    void doneProgress() {
        Log.d("ANALISE_APP", "PROGRESS DISMISS");
        progress.dismiss();
    }

    @UiThread
    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @UiThread
    void showToast(int msgRes) {
        Toast.makeText(this, msgRes, Toast.LENGTH_LONG).show();
    }

}
