package shorter.domain;

import java.net.URL;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public record OriginalURL(String originalURL) {
    public OriginalURL(String originalURL) {
        validate(originalURL);
        this.originalURL = originalURL;
    }

    private static void validate(String originalURL) {
        try {
            new URL(originalURL);
            new URI(originalURL);
        } catch (MalformedURLException e) {
            throw new InvalidOriginalURLException();
        } catch (URISyntaxException e) {
            throw new InvalidOriginalURLException();
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