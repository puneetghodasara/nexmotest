//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package me.puneetghodasara.nexmo.price;

import com.nexmo.client.AbstractMethod;
import com.nexmo.client.HttpWrapper;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.auth.TokenAuthMethod;
import me.puneetghodasara.nexmo.price.PriceRequest;
import me.puneetghodasara.nexmo.price.PriceResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicResponseHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class PriceEndpoint extends AbstractMethod<PriceRequest, PriceResponse> {
    private static final Class[] ALLOWED_AUTH_METHODS = new Class[]{TokenAuthMethod.class};
    private static final String PATH = "/pricing/messaging/";

    public PriceEndpoint(final HttpWrapper httpWrapper) {
        super(httpWrapper);
    }

    protected Class[] getAcceptableAuthMethods() {
        return ALLOWED_AUTH_METHODS;
    }

    @Override
    public RequestBuilder makeRequest(final PriceRequest priceRequest) throws NexmoClientException, UnsupportedEncodingException {
        RequestBuilder requestBuilder = RequestBuilder.get(this.httpWrapper.getHttpConfig().getRestBaseUri() + PATH + priceRequest.getCountry() + "/jsonp");
        return requestBuilder;
    }

    public PriceResponse parseResponse(HttpResponse response) throws IOException {
        final String json = (new BasicResponseHandler()).handleResponse(response);
//        System.out.println(json);
        return PriceResponse.fromJson(json);
    }
}
