package co.digamma.netnote.navigation;

import co.digamma.netnote.common.Repository;
import java.util.Optional;

public interface RatedDomainRepository extends Repository<RatedDomain, String> {

    RatedDomain create(String url);
    Optional<RatedDomain> readByUrl(String url);
}
