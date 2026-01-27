package ar.edu.ubp.das.ristorinobackend.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class UrlHasher {

    @Value("${security.assets.base-path:/api/v1/public/assets}")
    private String assetsBasePath;

    public String hash(String value) {
        if (value == null) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No se pudo calcular el hash", e);
        }
    }

    public String buildPublicUrl(String hash) {
        if (hash == null) {
            return null;
        }
        String normalizedBase = assetsBasePath.endsWith("/") ? assetsBasePath.substring(0, assetsBasePath.length() - 1) : assetsBasePath;
        return normalizedBase + "/" + hash;
    }
}
