package co.digamma.netnote.persistence.common;

import co.digamma.netnote.common.Cursor;
import co.digamma.netnote.common.Identifiable;
import co.digamma.netnote.common.Page;
import co.digamma.netnote.common.Pagination;
import co.digamma.netnote.common.Timestamped;
import jakarta.annotation.Nonnull;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface JpaBaseEntityRepository<T extends Identifiable<String> & Timestamped, E extends T> extends
        ListPagingAndSortingRepository<E, String>,
        JpaSpecificationExecutor<E>,
        CrudRepository<E, String> {

    String TIMESTAMP = "timestamp";

    default T asDomain(E entity) {
        return entity;
    }

    default Optional<T> read(@Nonnull String id) {
        return this.findById(id).map(this::asDomain);
    }

    default Specification<E> toSpecification(Pagination pagination) {
        return (root, query, criteria) ->
            Optional.ofNullable(pagination.cursor())
                .map(Cursor::toString)
                .map(this::decodeTimestamp)
                .map(it -> criteria.lessThan(root.get(TIMESTAMP), it))
                .orElseGet(criteria::conjunction);
    }

    default Pageable toPageable(Pagination pagination) {
        return PageRequest.of(0, pagination.size(), Sort.Direction.DESC, TIMESTAMP);
    }

    default Optional<T> findOneWhereFieldIs(String field, Object value) {
        Specification<E> specification = (root, query, criteria) ->
                criteria.equal(root.get(field), value);
        return this.findOne(specification).map(this::asDomain);
    }

    default Page<T> findWhereFieldIs(String field, Object value, Pagination pagination) {
        Specification<E> specification = (root, query, criteria) ->
                criteria.equal(root.get(field), value);
        return this.find(specification, pagination);
    }

    default Page<T> find(Pagination pagination) {
        return this.find(null, pagination);
    }

    default Page<T> find(Specification<E> knowing, @Nonnull Pagination pagination) {
        Specification<E> specification = toSpecification(pagination)
                .and(knowing);
        Pageable pageable = toPageable(pagination);
        var page = this.findAll(specification, pageable);
        if (page.isEmpty()) {
            return Page.empty();
        }
        List<T> items = page.map(this::asDomain).toList();
        Cursor startCursor = this.cursor(items.getFirst());
        Cursor endCursor = this.cursor(items.getLast());
        boolean hasBefore = pagination.cursor() != null;
        boolean hasAfter = items.size() == pagination.size();
        Optional<Long> totalNumberOfItems = Optional.of(page.getTotalElements());
        return new Page<>(
                items,
                startCursor,
                endCursor,
                hasBefore,
                hasAfter,
                totalNumberOfItems
        );
    }

    default Cursor cursor(T item) {
        LocalDateTime timestamp = item.getTimestamp();
        String encoded = this.encodeTimestamp(timestamp);
        return new Cursor(encoded);
    }

    @Nonnull
    default String encodeTimestamp(@Nonnull LocalDateTime timestamp) {
        long ms = Timestamp.valueOf(timestamp).getTime();
        byte[] bytes = ByteBuffer.allocate(Long.BYTES).putLong(ms).array();
        return Base64.getEncoder().encodeToString(bytes);

    }

    @Nonnull
    default LocalDateTime decodeTimestamp(@Nonnull String encoded) {
        byte[] bytes = Base64.getDecoder().decode(encoded);
        long ms = ByteBuffer.wrap(bytes).getLong();
        return new Timestamp(ms).toLocalDateTime();
    }
}
