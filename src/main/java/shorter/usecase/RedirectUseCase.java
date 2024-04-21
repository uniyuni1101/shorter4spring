package shorter.usecase;

import org.springframework.stereotype.Service;

import shorter.domain.OriginalURL;
import shorter.domain.ShortLinkID;
import shorter.domain.ShortLinkRepository;

@Service
public class RedirectUseCase {

    private final ShortLinkRepository shortLinkRepository;

    public RedirectUseCase(ShortLinkRepository shortLinkRepository) {
        this.shortLinkRepository = shortLinkRepository;
    }

    public OriginalURL redirect(ShortLinkID shortLinkID) {
        var result = this.shortLinkRepository.findByID(shortLinkID);
        var shortLink = result.orElseThrow(()-> new NotFoundShortLinkException());
        return shortLink.originalURL();
    }
}
