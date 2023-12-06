package api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static api.BaseApi.BASE_URL;

public class RequestSpecs {
    public static RequestSpecification requestSpecification;
    public static RequestSpecification getDefaultRequestSpecification(){
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .addHeader("Content-Type", "application/json")
                .build();
        return requestSpecification;
    }

    public void setHeader(String head, String val) { requestSpecification.header(head, val);}

    public void setBody(String body) { requestSpecification.body(body); }

    public void setFormParam(String key, String val) { requestSpecification.formParam(key, val);}

    public void setQueryParam(String key, String val) { requestSpecification.queryParam(key, val);}
}
