package shorter.domain;

import java.util.Optional;

public interface ShortLinkRepository {
    Optional<ShortLink> findByID(ShortLinkID shortLinkID);
    void save(ShortLink shortLink);
    ShortLinkID generateShotLinkID();
}
