package com.raissa.analiseapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by primelan on 5/29/16.
 */
public class Questoes implements Serializable{
    ArrayList<PaginaQuestao> paginas;

    public ArrayList<PaginaQuestao> getPaginas() {
        return paginas;
    }

    public void setPaginas(ArrayList<PaginaQuestao> paginas) {
        this.paginas = paginas;
    }
}
