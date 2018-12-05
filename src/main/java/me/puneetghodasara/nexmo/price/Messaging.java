package me.puneetghodasara.nexmo.price;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Messaging {

    private Outbound outbound;

    private Inbound inbound;

    public Inbound getInbound() {
        return inbound;
    }

    public Outbound getOutbound() {
        return outbound;
    }

    @Override
    public String toString() {
        return "Messaging{" +
                "outbound=" + outbound +
                '}';
    }
}
