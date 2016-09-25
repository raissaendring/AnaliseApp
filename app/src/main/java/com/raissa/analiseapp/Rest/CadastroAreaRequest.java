package com.raissa.analiseapp.Rest;

import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;

/**
 * Created by primelan on 9/25/16.
 */
public class CadastroAreaRequest extends LinkedMultiValueMap<String , Object> {
    public CadastroAreaRequest(double latitude, double longitude, int valor, String nomeFiscal, String matriculaFiscal, String referencia, FileSystemResource imagem){
        add("x", String.valueOf(latitude));
        add("y", String.valueOf(longitude));
        add("valor", String.valueOf(valor));
        add("fiscal", nomeFiscal);
        add("matricula", matriculaFiscal);
        add("referencia", referencia);
        add("imagem", imagem);
    }
}
