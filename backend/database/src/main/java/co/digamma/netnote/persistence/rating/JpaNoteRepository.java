package co.digamma.netnote.persistence.rating;

import co.digamma.netnote.navigation.Locatable;
import co.digamma.netnote.persistence.common.JpaBaseEntityRepository;
import co.digamma.netnote.persistence.common.UnmanagedObjectException;
import co.digamma.netnote.persistence.navigation.JpaDomain;
import co.digamma.netnote.persistence.navigation.JpaLink;
import co.digamma.netnote.rating.Note;
import co.digamma.netnote.rating.NoteRepository;
import jakarta.annotation.Nonnull;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface JpaNoteRepository extends
        NoteRepository,
        JpaBaseEntityRepository<Note, JpaNote>,
        CrudRepository<JpaNote, String>,
        ListPagingAndSortingRepository<JpaNote, String> {

    @Override
    default Note write(Note entity) {
        if (entity instanceof JpaNote note) {
            return this.save(note);
        }
        throw UnmanagedObjectException.of(entity);
    }

    @Override
    default Optional<Note> read(@Nonnull String id) {
        return JpaBaseEntityRepository.super.read(id);
    }

    @Override
    default JpaNote create(Locatable locatable) {
        JpaNote note = new JpaNote();
        switch (locatable) {
            case JpaLink link -> note.setLink(link);
            case JpaDomain domain -> note.setDomain(domain);
            default -> throw UnmanagedObjectException.of(locatable);
        }
        return note;
    }

    @Override
    default Optional<Note> readByLocatable(Locatable locatable) {
        String field = switch (locatable) {
            case JpaLink ignore -> "link_id";
            case JpaDomain ignore -> "domain_id";
            default -> throw UnmanagedObjectException.of(locatable);
        };
        return this.findOneWhereFieldIs(field, locatable.getId());
    }
}
