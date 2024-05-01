package shorter.domain;

import java.util.regex.Pattern;

public record UserID(String id) {
    private static final Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]{1,16}$");

    public UserID(String id) {
        validate(id);
        this.id = id;
    }

    private static void validate(String unvalidated_userID) {
        var matcher = pattern.matcher(unvalidated_userID);
        if (!matcher.matches()) {
            throw new InvalidUserIDException();
        }
    }

    @Override
    public String toString() {
        return this.id;
    }
}
