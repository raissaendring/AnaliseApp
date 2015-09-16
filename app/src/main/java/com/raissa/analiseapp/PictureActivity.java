package com.raissa.analiseapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

@EActivity(R.layout.activity_picture)
public class PictureActivity extends Activity {

    @ViewById
    Button btnTirarFoto;
    @ViewById
    ImageView foto;
    //@ViewById
    //Button btnFinalizar;
    @ViewById
    Button btnEnviar;
    //@ViewById
    //Button btnSalvar;

    @Extra
    ArrayList<ItemQuestao> listQuestoes;
    @Extra
    double latitude;
    @Extra
    double longitude;
    @Extra
    String nomeFiscal;

    @Pref
    MyPrefs_ prefs;

    String folderPath = Environment.getExternalStorageDirectory() + "/AnaliseApp";

    String imgPath;
    String txtPath;
    String tablePath;
    ProgressDialog progress;

    @AfterViews
    void init(){
        progress = new ProgressDialog(this);
        File folder = new File(folderPath);
        if(!folder.exists()) folder.mkdirs();
    }

    @Click
    void btnTirarFotoClicked(){
        Calendar c = Calendar.getInstance(Locale.ENGLISH);

        imgPath = folderPath+"/img_area_"+c.getTimeInMillis()+".jpg";
        File imgFile = new File(imgPath);
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        it.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(imgFile));
        startActivityForResult(it, 1001);
    }

    @OnActivityResult(1001)
    void tirouFoto(int resultCode){
        if(resultCode==RESULT_OK) {
            btnEnviar.setVisibility(View.VISIBLE);
            Log.d("ANALISE APP","Img Path = "+imgPath);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory().getAbsolutePath())));
            File fileImg = new File(imgPath);
            Uri uriImg = Uri.fromFile(fileImg);
            Glide.with(this).load(uriImg).asBitmap().into(foto);
        }
    }

    void createTxtFile(){
        String results = "";
        for(ItemQuestao item : listQuestoes){
            results += item.toString();
        }
        results += "Situação da área analisada: " + situacaoFinal();
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        txtPath = folderPath + "/results_" + c.getTimeInMillis() + ".txt";
        try {
            FileOutputStream fOut = new FileOutputStream(txtPath);
            OutputStreamWriter writer = new OutputStreamWriter(fOut);
            writer.write(results);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("ANALISE APP","FILE NOT FOUND EXCEPTION");
        } catch (IOException e) {
            Log.e("ANALISE APP", "IO EXCEPTION");
            e.printStackTrace();
        }
    }

    void createTabela(){
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        tablePath = folderPath + "/table_" + c.getTimeInMillis() + ".txt";
        String dados = "";
        dados += "Coord. X\tCoord. Y\tValor\n";
        dados += longitude+ "\t" +latitude + "\t" + valorFinal()+"\n";
        try {
            FileOutputStream fOut = new FileOutputStream(tablePath);
            OutputStreamWriter writer = new OutputStreamWriter(fOut);
            writer.write(dados);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("ANALISE APP","FILE NOT FOUND EXCEPTION");
        } catch (IOException e) {
            Log.e("ANALISE APP", "IO EXCEPTION");
            e.printStackTrace();
        }
    }

    String situacaoFinal(){
        int soma=valorFinal();
        if(soma>=23){
            return "Vermelho";
        }else if(soma > 18){
            return "Amarelo";
        }else{
            return "Verde";
        }
    }

    int valorFinal(){
        int soma = 0;
        for(ItemQuestao q : listQuestoes){
            soma += q.situacao;
        }
        return soma;
    }

    @Click
    void btnEnviarClicked(){
        if(Utils.isConnected(this)) {
            createTxtFile();
            createTabela();
            enviarEmailBackground();
        }else{
            showToast(R.string.msg_sem_conexao);
        }
    }

    @Background
    void enviarEmailBackground(){
        showProgress();
        String body = getString(R.string.email_msg);
        body += "\n\nNome do Fiscal: " + nomeFiscal;
        Mail mail = new Mail(Constants.EMAIL,Constants.SENHA);
        String[] to = {Constants.EMAIL};
        mail.setTo(to);
        mail.setBody(body);
        mail.setSubject(getResources().getString(R.string.email_subject));
        mail.setFrom(Constants.EMAIL);
        try {
            mail.addAttachment(txtPath);
            mail.addAttachment(tablePath);
            mail.addAttachment(imgPath);
        } catch (Exception e) {
            Log.e("ANALISE APP","ATTACHMENT EXCEPTION");
            e.printStackTrace();
        }
        try {
            if(mail.send()){
                doneProgress();
                showToast(R.string.msg_email_enviado);
                MainActivity_.intent(this).start();
            }
            else{
                doneProgress();
                showToast(R.string.msg_email_n_enviado);
            }
        } catch (Exception e) {
            doneProgress();
            showToast(R.string.msg_email_n_enviado);
            e.printStackTrace();
        }
    }

    @UiThread
    void showProgress(){
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress = new ProgressDialog(this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage(getString(R.string.msg_enviando));
        progress.show();
    }

    @UiThread
    void doneProgress(){
        Log.d("ANALISE_APP","PROGRESS DISMISS");
        progress.dismiss();
    }

    @UiThread
    void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @UiThread
    void showToast(int msgRes){
        Toast.makeText(this,msgRes,Toast.LENGTH_SHORT).show();
    }

}
