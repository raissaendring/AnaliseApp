package com.raissa.analiseapp;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by primelan on 9/11/15.
 */

@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface MyPrefs {

    String nomeFiscal();

    String matriculaFiscal();

    @DefaultBoolean(value = false)
    boolean credenciaisSalvas();


}
