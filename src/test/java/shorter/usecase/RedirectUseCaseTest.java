package shorter.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import shorter.domain.OriginalURL;
import shorter.domain.ShortLink;
import shorter.domain.ShortLinkID;
import shorter.infrastructure.InMemoryShortLinkRepository;

public class RedirectUseCaseTest {
    @Test
    void ShortLinkIDが存在している場合() {
        var repo = new InMemoryShortLinkRepository();
        repo.save(new ShortLink(new ShortLinkID("asdfgh"), new OriginalURL("http://example.com")));

        var usecase = new RedirectUseCase(repo);

        assertEquals("http://example.com", usecase.redirect(new ShortLinkID("asdfgh")).toString());
    }

    @Test
    void ShortLinkIDが存在していない場合() {
        var repo = new InMemoryShortLinkRepository();
        var usecase = new RedirectUseCase(repo);

        assertThrows(NotFoundShortLinkException.class, () -> usecase.redirect(new ShortLinkID("asdfgh")));
    }
}
