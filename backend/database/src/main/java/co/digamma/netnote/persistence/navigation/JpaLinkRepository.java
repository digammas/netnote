package co.digamma.netnote.persistence.navigation;

import co.digamma.netnote.common.Page;
import co.digamma.netnote.common.Pagination;
import co.digamma.netnote.navigation.RatedDomain;
import co.digamma.netnote.navigation.RatedLink;
import co.digamma.netnote.navigation.RatedLinkRepository;
import co.digamma.netnote.persistence.common.JpaBaseEntityRepository;
import co.digamma.netnote.persistence.common.UnmanagedObjectException;
import jakarta.annotation.Nonnull;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface JpaLinkRepository extends
        RatedLinkRepository,
        JpaBaseEntityRepository<RatedLink, JpaLink>,
        CrudRepository<JpaLink, String>,
        ListPagingAndSortingRepository<JpaLink, String> {

    @Override
    default RatedLink create(@Nonnull String url, @Nonnull RatedDomain domain) {
        if (!(domain instanceof JpaDomain jpaDomain)) {
            throw UnmanagedObjectException.of(domain);
        }
        JpaLink link = new JpaLink();
        link.setUrl(url);
        link.setDomain(jpaDomain);
        return link;
    }

    @Override
    default Page<RatedLink> findByDomain(RatedDomain domain, Pagination pagination) {
        return this.findWhereFieldIs("domain_id", domain.getId(), pagination);
    }

    @Override
    default Optional<RatedLink> read(@Nonnull String id) {
        return JpaBaseEntityRepository.super.read(id);
    }

    default RatedLink write(RatedLink entity) {
        if (entity instanceof JpaLink link) {
            return this.save(link);
        }
        throw UnmanagedObjectException.of(entity);
    }
}
