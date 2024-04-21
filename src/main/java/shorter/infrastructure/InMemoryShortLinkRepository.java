package shorter.infrastructure;

import java.util.Optional;
import java.util.HashMap;

import shorter.domain.ShortLink;
import shorter.domain.ShortLinkID;
import shorter.domain.ShortLinkRepository;

public class InMemoryShortLinkRepository implements ShortLinkRepository {

    private final HashMap<ShortLinkID, ShortLink> shortLinkMap;

    public InMemoryShortLinkRepository() {
        this.shortLinkMap = new HashMap<ShortLinkID, ShortLink>();
    }

    @Override
    public Optional<ShortLink> findByID(ShortLinkID shortLinkID) {
        var res = this.shortLinkMap.get(shortLinkID);
        return Optional.ofNullable(res);
    }

    @Override
    public void save(ShortLink shortLink) {
        this.shortLinkMap.put(shortLink.shortLinkID(), shortLink);
    }

    @Override
    public ShortLinkID generateShotLinkID() {
        while (true) {
            var nextID = ShortLinkID.random();
            if (this.shortLinkMap.containsKey(nextID)) {
                continue;
            }

            return nextID;
        }
    }
    
}
