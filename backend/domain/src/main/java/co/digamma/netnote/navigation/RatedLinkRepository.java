package co.digamma.netnote.navigation;

import co.digamma.netnote.common.Page;
import co.digamma.netnote.common.Pagination;
import co.digamma.netnote.common.Repository;
import jakarta.annotation.Nonnull;
import java.util.Optional;

public interface RatedLinkRepository
        extends Repository<RatedLink, String> {

    RatedLink create(@Nonnull String url, @Nonnull RatedDomain domain);
    Optional<RatedLink> readByUrl(String url);
    Page<? extends RatedLink> findByDomain(RatedDomain domain, Pagination pagination);
}
