package shorter.domain;

import java.util.regex.Pattern;
import java.util.Random;

public record ShortLinkID(String shortLinkID) {

    private static final Pattern pattern = Pattern.compile("^\\w{6}$");

    public ShortLinkID(String shortLinkID) {
        validate(shortLinkID);
        this.shortLinkID = shortLinkID;
    }

    public static ShortLinkID random() {
        var baseChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_";
        var idLength = 6;
        var rand = new Random();

        String resID = "";
        for (int i = 0; i < idLength; i++) {
            resID += baseChars.charAt(rand.nextInt(baseChars.length()));
        }

        return new ShortLinkID(resID);
    }

    private static void validate(String shortLinkID) {
        var matcher = pattern.matcher(shortLinkID);
        var ok = matcher.matches();
        if (!ok) {
            throw new InvalidShortLinkIDException();
        }
    }

    public String value() {
        return this.shortLinkID;
    }

    @Override
    public final String toString() {
        return this.shortLinkID;
    }
}
