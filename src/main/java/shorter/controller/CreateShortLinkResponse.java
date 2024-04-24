package shorter.controller;

import shorter.domain.ShortLink;

public record CreateShortLinkResponse(String id, String originalURL) {
    public static CreateShortLinkResponse fromShortLink(ShortLink shortLink) {
        return new CreateShortLinkResponse(
            shortLink.shortLinkID().value(),
            shortLink.originalURL().value()
        );
    }
}
