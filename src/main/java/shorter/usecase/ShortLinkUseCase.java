package shorter.usecase;

import org.springframework.stereotype.Service;

import shorter.domain.OriginalURL;
import shorter.domain.ShortLink;
import shorter.domain.ShortLinkRepository;

@Service
public class ShortLinkUseCase {

    private final ShortLinkRepository shortLinkRepository;

    public ShortLinkUseCase(ShortLinkRepository shortLinkRepository) {
        this.shortLinkRepository = shortLinkRepository;
    }

    public ShortLink create(String originalURL) {
        var newID = shortLinkRepository.generateShotLinkID();
        var newShortLink = new ShortLink(newID, new OriginalURL(originalURL));

        shortLinkRepository.save(newShortLink);
        return newShortLink;
    }
}
