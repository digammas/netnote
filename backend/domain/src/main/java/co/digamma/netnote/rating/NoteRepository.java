package co.digamma.netnote.rating;

import co.digamma.netnote.common.Repository;
import co.digamma.netnote.navigation.Locatable;

import java.util.Optional;

public interface NoteRepository extends Repository<Note, String> {

    Optional<Note> readByLocatable(Locatable locatable);
    Note create(Locatable locatable);
}
