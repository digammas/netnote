package co.digamma.netnote.persistence.rating;

import co.digamma.netnote.common.Page;
import co.digamma.netnote.common.Pagination;
import co.digamma.netnote.navigation.RatedDomain;
import co.digamma.netnote.navigation.RatedLink;
import co.digamma.netnote.persistence.common.JpaBaseEntityRepository;
import co.digamma.netnote.persistence.common.UnmanagedObjectException;
import co.digamma.netnote.persistence.navigation.JpaLink;
import co.digamma.netnote.rating.Rating;
import co.digamma.netnote.rating.RatingRepository;
import jakarta.annotation.Nonnull;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface JpaRatingRepository extends
        RatingRepository,
        JpaBaseEntityRepository<Rating, JpaRating>,
        CrudRepository<JpaRating, String>,
        ListPagingAndSortingRepository<JpaRating, String> {

    @Override
    default Rating write(Rating entity) {
        if (entity instanceof JpaRating note) {
            return this.save(note);
        }
        throw UnmanagedObjectException.of(entity);
    }

    @Override
    default Optional<Rating> read(@Nonnull String id) {
        return JpaBaseEntityRepository.super.read(id);
    }

    @Override
    default Page<Rating> findByDomain(RatedDomain domain, Pagination pagination) {
        Specification<JpaRating> specification = (root, query, criteria) ->
            criteria.equal(root.join("link").get("domain_id"), domain.getId());
        return find(specification, pagination);
    }

    @Override
    default Page<Rating> findByLink(RatedLink link, Pagination pagination) {
        return JpaBaseEntityRepository.super.findWhereFieldIs("link_id", link.getId(), pagination);
    }

    @Override
    default Rating create(RatedLink link, int note, String comment) {
        if (!(link instanceof JpaLink jpaLink)) {
            throw UnmanagedObjectException.of(link);
        }
        JpaRating rating = new JpaRating();
        rating.setLink(jpaLink);
        rating.setNote(note);
        rating.setComment(comment);
        return rating;
    }
}
