package co.digamma.netnote.rating;

import co.digamma.netnote.common.Page;
import co.digamma.netnote.common.Pagination;
import co.digamma.netnote.common.Repository;
import co.digamma.netnote.navigation.RatedDomain;
import co.digamma.netnote.navigation.RatedLink;

public interface RatingRepository
        extends Repository<Rating, String> {

    Rating create(RatedLink link, int note, String comment);
    Page<Rating> findByLink(RatedLink link, Pagination pagination);
    Page<Rating> findByDomain(RatedDomain domain, Pagination pagination);
}
