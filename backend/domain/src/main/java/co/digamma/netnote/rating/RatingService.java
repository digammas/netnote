package co.digamma.netnote.rating;


import co.digamma.netnote.common.Page;
import co.digamma.netnote.common.Pagination;
import co.digamma.netnote.navigation.Link;
import co.digamma.netnote.navigation.Locatable;
import co.digamma.netnote.navigation.RatedDomain;
import co.digamma.netnote.navigation.RatedDomainRepository;
import co.digamma.netnote.navigation.RatedLink;
import co.digamma.netnote.navigation.RatedLinkRepository;
import co.digamma.netnote.navigation.RatedLocatable;
import jakarta.inject.Named;
import java.util.Optional;

@Named
public class RatingService {

    private final RatedLinkRepository ratedLinkRepository;
    private final RatedDomainRepository ratedDomainRepository;
    private final NoteRepository noteRepository;
    private final RatingRepository ratingRepository;

    public RatingService(
            RatedLinkRepository ratedLinkRepository, RatedDomainRepository ratedDomainRepository,
            NoteRepository noteRepository, RatingRepository ratingRepository
    ) {
        this.ratedLinkRepository = ratedLinkRepository;
        this.ratedDomainRepository = ratedDomainRepository;
        this.noteRepository = noteRepository;
        this.ratingRepository = ratingRepository;
    }

    public void rate(RatingRequest request) {
        String url = request.url();
        int note = request.note();
        String comment = request.comment();
        String normalizedUrl = Locatable.normalizeUrl(url);
        RatedLink link = this.ratedLinkRepository.readByUrl(normalizedUrl)
            .orElseGet(() -> this.createLink(normalizedUrl));
        RatedDomain domain = link.getDomain();
        updateNote(link, note);
        updateNote(domain, note);
        Rating rating = this.ratingRepository.create(link, note, comment);
        this.ratingRepository.write(rating);
    }

    private RatedLink createLink(String url) {
        String domainUrl = Link.getDomainFromUrl(url);
        RatedDomain domain = this.ratedDomainRepository.readByUrl(domainUrl)
                .orElseGet(() -> this.createDomain(domainUrl));
        RatedLink link = this.ratedLinkRepository.create(url, domain);
        return this.ratedLinkRepository.write(link);
    }

    private RatedDomain createDomain(String url) {
        RatedDomain domain = this.ratedDomainRepository.create(url);
        return this.ratedDomainRepository.write(domain);
    }

    private void updateNote(RatedLocatable locatable, int value) {
        Note note = this.noteRepository.readByLocatable(locatable)
            .orElseGet(() -> this.noteRepository.create(locatable));
        note.update(value);
        this.noteRepository.write(note);
    }

    public Optional<Note> retrieveNoteForLink(String url) {
        return this.ratedLinkRepository.read(url)
                .flatMap(this::retrieveNoteForLink);
    }

    public Optional<Note> retrieveNoteForLink(RatedLink link) {
        return this.noteRepository.readByLocatable(link);
    }

    public Optional<Note> retrieveNoteForDomain(String url) {
        return this.ratedDomainRepository.read(url)
                .flatMap(this::retrieveNoteForDomain);
    }

    public Optional<Note> retrieveNoteForDomain(RatedDomain domain) {
        return this.noteRepository.readByLocatable(domain);
    }

    public Page<Rating> retrieveRatingForLink(String url, Pagination pagination) {
        return this.ratedLinkRepository.read(url)
                .map(link ->  this.retrieveRatingForLink(link, pagination))
                .orElseGet(Page::empty);
    }

    public Page<Rating> retrieveRatingForLink(RatedLink link, Pagination pagination) {
        return this.ratingRepository.findByLink(link, pagination);
    }

    public Page<Rating> retrieveRatingForDomain(String url, Pagination pagination) {
        return this.ratedDomainRepository.read(url)
                .map(domain ->  this.retrieveRatingForDomain(domain, pagination))
                .orElseGet(Page::empty);
    }

    public Page<Rating> retrieveRatingForDomain(RatedDomain domain, Pagination pagination) {
        return this.ratingRepository.findByDomain(domain, pagination);
    }
}
