package me.puneetghodasara.nexmo.client;

import com.nexmo.client.HttpConfig;
import com.nexmo.client.HttpWrapper;
import com.nexmo.client.NexmoClientCreationException;
import com.nexmo.client.auth.AuthCollection;
import com.nexmo.client.auth.JWTAuthMethod;
import com.nexmo.client.auth.SignatureAuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import me.puneetghodasara.nexmo.price.PriceClient;
import org.apache.http.client.HttpClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class ModifiedNexmoClient {


    private final HttpWrapper httpWrapper;
    private final PriceClient price;

    protected ModifiedNexmoClient(final Builder builder) {
        this.httpWrapper = new HttpWrapper(builder.httpConfig, builder.authCollection);
        this.httpWrapper.setHttpClient(builder.httpClient);
        this.price = new PriceClient(this.httpWrapper);

    }

    public PriceClient getPrice() {
        return price;
    }
    public static class Builder {

        private AuthCollection authCollection;
        private HttpConfig httpConfig = HttpConfig.defaultConfig();
        private HttpClient httpClient;
        private String applicationId;
        private String apiKey;
        private String apiSecret;
        private String signatureSecret;
        private byte[] privateKeyContents;

        public Builder() {
        }

        public ModifiedNexmoClient.Builder httpConfig(HttpConfig httpConfig) {
            this.httpConfig = httpConfig;
            return this;
        }

        public ModifiedNexmoClient.Builder httpClient(HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public ModifiedNexmoClient.Builder applicationId(String applicationId) {
            this.applicationId = applicationId;
            return this;
        }

        public ModifiedNexmoClient.Builder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public ModifiedNexmoClient.Builder apiSecret(String apiSecret) {
            this.apiSecret = apiSecret;
            return this;
        }

        public ModifiedNexmoClient.Builder signatureSecret(String signatureSecret) {
            this.signatureSecret = signatureSecret;
            return this;
        }

        public ModifiedNexmoClient.Builder privateKeyContents(byte[] privateKeyContents) {
            this.privateKeyContents = privateKeyContents;
            return this;
        }

        public ModifiedNexmoClient.Builder privateKeyContents(String privateKeyContents) {
            return this.privateKeyContents(privateKeyContents.getBytes());
        }

        public ModifiedNexmoClient.Builder privateKeyPath(Path privateKeyPath) throws IOException {
            return this.privateKeyContents(Files.readAllBytes(privateKeyPath));
        }

        public ModifiedNexmoClient.Builder privateKeyPath(String privateKeyPath) throws IOException {
            return this.privateKeyPath(Paths.get(privateKeyPath));
        }

        public ModifiedNexmoClient build() {
            this.authCollection = this.generateAuthCollection(this.applicationId, this.apiKey, this.apiSecret, this.signatureSecret, this.privateKeyContents);
            return new ModifiedNexmoClient(this);
        }

        private AuthCollection generateAuthCollection(String applicationId, String key, String secret, String signature, byte[] privateKeyContents) {
            AuthCollection authMethods = new AuthCollection();

            try {
                this.validateAuthParameters(applicationId, key, secret, signature, privateKeyContents);
            } catch (IllegalStateException var9) {
                throw new NexmoClientCreationException("Failed to generate authentication methods.", var9);
            }

            if (key != null && secret != null) {
                authMethods.add(new TokenAuthMethod(key, secret));
            }

            if (key != null && signature != null) {
                authMethods.add(new SignatureAuthMethod(key, signature));
            }

            if (applicationId != null && privateKeyContents != null) {
                try {
                    authMethods.add(new JWTAuthMethod(applicationId, privateKeyContents));
                } catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException var8) {
                    throw new NexmoClientCreationException("Failed to generate JWT Authentication method.", var8);
                }
            }

            return authMethods;
        }

        private void validateAuthParameters(String applicationId, String key, String secret, String signature, byte[] privateKeyContents) {
            if (key != null && secret == null && signature == null) {
                throw new IllegalStateException("You must provide an API secret or signature secret in addition to your API key.");
            } else if (secret != null && key == null) {
                throw new IllegalStateException("You must provide an API key in addition to your API secret.");
            } else if (signature != null && key == null) {
                throw new IllegalStateException("You must provide an API key in addition to your signature secret.");
            } else if (applicationId == null && privateKeyContents != null) {
                throw new IllegalStateException("You must provide an application ID in addition to your private key.");
            } else if (applicationId != null && privateKeyContents == null) {
                throw new IllegalStateException("You must provide a private key in addition to your application id.");
            }
        }
    }

}
