package shorter.domain;

import java.net.MalformedURLException;
import java.net.URL;

public record OriginalURL(String originalURL) {
    public OriginalURL(String originalURL) {
        validate(originalURL);
        this.originalURL = originalURL;
    }

    private static void validate(String originalURL) {
        try {
            new URL(originalURL);
        } catch (MalformedURLException e) {
            throw new OriginURLSyntaxException();
        }
    }

    public String value() {
        return this.originalURL;
    }
    @Override
    public final String toString() {
        return this.originalURL;
    }
}