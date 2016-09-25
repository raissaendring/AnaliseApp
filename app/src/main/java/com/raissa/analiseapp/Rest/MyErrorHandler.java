package com.raissa.analiseapp.Rest;

import org.androidannotations.annotations.EBean;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.core.NestedRuntimeException;
import org.xml.sax.ErrorHandler;

/**
 * Created by primelan on 9/25/16.
 */

@EBean
public class MyErrorHandler implements RestErrorHandler{
    @Override
    public void onRestClientExceptionThrown(NestedRuntimeException e) {
        e.printStackTrace();
    }
}
