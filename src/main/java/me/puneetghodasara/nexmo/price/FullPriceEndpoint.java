//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package me.puneetghodasara.nexmo.price;

import com.nexmo.client.AbstractMethod;
import com.nexmo.client.HttpWrapper;
import com.nexmo.client.auth.TokenAuthMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicResponseHandler;

import java.io.IOException;

public class FullPriceEndpoint extends AbstractMethod<Object, FullPriceResponse> {
    private static final Class[] ALLOWED_AUTH_METHODS = new Class[]{TokenAuthMethod.class};
    private static final String PATH = "/account/get-full-pricing/outbound/sms";

    public FullPriceEndpoint(final HttpWrapper httpWrapper) {
        super(httpWrapper);
    }

    protected Class[] getAcceptableAuthMethods() {
        return ALLOWED_AUTH_METHODS;
    }

    @Override
    public RequestBuilder makeRequest(Object o) {
        RequestBuilder requestBuilder = RequestBuilder.get(this.httpWrapper.getHttpConfig().getRestBaseUri() + PATH);
        return requestBuilder;
    }

    public FullPriceResponse parseResponse(HttpResponse response) throws IOException {
        final String json = (new BasicResponseHandler()).handleResponse(response);
//        System.out.println(json);
        return FullPriceResponse.fromJson(json);
    }
}
