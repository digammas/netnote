package co.digamma.netnote.persistence.navigation;

import co.digamma.netnote.navigation.RatedDomain;
import co.digamma.netnote.navigation.RatedDomainRepository;
import co.digamma.netnote.persistence.common.JpaBaseEntityRepository;
import co.digamma.netnote.persistence.common.UnmanagedObjectException;
import jakarta.annotation.Nonnull;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface JpaDomainRepository extends
        RatedDomainRepository,
        JpaBaseEntityRepository<RatedDomain, JpaDomain>,
        CrudRepository<JpaDomain, String>,
        ListPagingAndSortingRepository<JpaDomain, String> {

    @Override
    default JpaDomain create(String url) {
        JpaDomain domain = new JpaDomain();
        domain.setUrl(url);
        return domain;
    }

    @Override
    default Optional<RatedDomain> read(@Nonnull String id) {
        return JpaBaseEntityRepository.super.read(id);
    }

    @Override
    default RatedDomain write(RatedDomain entity) {
        if (entity instanceof JpaDomain domain) {
            return this.save(domain);
        }
        throw UnmanagedObjectException.of(entity);
    }
}
