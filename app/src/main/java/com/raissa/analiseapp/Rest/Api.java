package com.raissa.analiseapp.Rest;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Created by primelan on 9/25/16.
 */

@Rest(rootUrl = "http://lowcost-env.b3ahqhwn6u.us-east-1.elasticbeanstalk.com/", converters = {FormHttpMessageConverter.class,StringHttpMessageConverter.class})
public interface Api extends RestClientErrorHandling{

    @Post("api/areas/add")
    String saveArea(CadastroAreaRequest request);
}
