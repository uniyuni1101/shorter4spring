package shorter.infrastructure;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import shorter.domain.OriginalURL;
import shorter.domain.ShortLink;
import shorter.domain.ShortLinkID;
import shorter.domain.ShortLinkRepository;


import static shorter.jooq.Tables.SHORT_LINK;

import org.jooq.DSLContext;

@Repository
public class PostgreSQLShortLinkRepository implements ShortLinkRepository {

    private final DSLContext ctx;

    public PostgreSQLShortLinkRepository(DSLContext dslContext) {
        this.ctx = dslContext;
    }

    @Override
    public Optional<ShortLink> findByID(ShortLinkID shortLinkID) {
        var result = ctx.select(SHORT_LINK.ID, SHORT_LINK.ORIGINAL_URL)
                        .from(SHORT_LINK)
                        .where(SHORT_LINK.ID
                        .eq(shortLinkID.value()))
                        .fetchOptional();

        if (result.isEmpty()) {
            return Optional.ofNullable(null);
        }

        var record = result.get();

        var fetchShortLinkID = new ShortLinkID(record.get(SHORT_LINK.ID));
        var fetchOriginalURL = new OriginalURL(record.get(SHORT_LINK.ORIGINAL_URL));
        var fetchShortLink = new ShortLink(fetchShortLinkID, fetchOriginalURL);

        return Optional.of(fetchShortLink);
    }

    @Override
    public void save(ShortLink shortLink) {
        ctx.insertInto(SHORT_LINK)
            .values(shortLink.shortLinkID(), shortLink.originalURL().value())
            .onConflict(SHORT_LINK.ID)
            .doUpdate()
            .set(SHORT_LINK.ORIGINAL_URL, shortLink.originalURL().value())
            .execute();
    }

    @Override
    public ShortLinkID generateShotLinkID() {
        while (true) {
            var nextID = ShortLinkID.random();
            var isExistsID = ctx.fetchExists(ctx.selectOne().from(SHORT_LINK).where(SHORT_LINK.ID.eq(nextID.value())));

            if (isExistsID) {
                continue;
            }

            return nextID;
        }
    }
    
}
