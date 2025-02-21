package co.digamma.netnote.persistence.rating;

import co.digamma.netnote.common.Page;
import co.digamma.netnote.common.Pagination;
import co.digamma.netnote.navigation.RatedDomain;
import co.digamma.netnote.navigation.RatedDomainRepository;
import co.digamma.netnote.navigation.RatedLink;
import co.digamma.netnote.navigation.RatedLinkRepository;
import co.digamma.netnote.persistence.common.BaseTestSuite;
import co.digamma.netnote.rating.Rating;
import co.digamma.netnote.rating.RatingRepository;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JpaRatingRepositoryTest extends BaseTestSuite {

    @Autowired
    private RatingRepository sut;

    @Test
    public void create_should_create() {
        RatedLink link = givenLink();
        int note = 5;
        String comment = "Amazing";
        Rating rating = sut.create(link, note, comment);
        assertEquals(note, rating.getNote());
        assertEquals(comment, rating.getComment());
    }

    @Test
    public void create_should_not_save() {
        RatedLink link = givenLink();
        int note = 5;
        String comment = "Amazing";
        Rating rating = sut.create(link, note, comment);
        try {
            assertTrue(sut.read(rating.getId()).isEmpty());
        } catch (DataAccessException ignore) {
            // Throwing an exception is OK, but not necessary (read may return empty).
        }
    }

    @Test
    public void write_should_save() {
        RatedLink link = givenLink();
        int note = 5;
        String comment = "Amazing";
        Rating rating = sut.create(link, note, comment);
        rating = sut.write(rating);
        assertEquals(note, rating.getNote());
        assertEquals(comment, rating.getComment());
    }

    @Test
    public void read_should_read() {
        RatedLink link = givenLink();
        int note = 5;
        String comment = "Amazing";
        Rating rating = sut.create(link, note, comment);
        sut.write(rating);
        rating = sut.read(rating.getId()).orElseThrow();
        assertEquals(note, rating.getNote());
        assertEquals(comment, rating.getComment());
    }

    @Test
    public void read_and_write_null_comment_should_work() {
        RatedLink link = givenLink();
        int note = 5;
        String comment = null;
        Rating rating = sut.create(link, note, comment);
        sut.write(rating);
        rating = sut.read(rating.getId()).orElseThrow();
        assertEquals(note, rating.getNote());
        assertEquals(comment, rating.getComment());
    }

    public void findByDomain_should_find_by_domain() {
        RatedDomain domain = givenDomain();
        for (int i = 0; i < 5; i++) {
            RatedLink link = givenLinkForDomain(domain);
            int note = 5;
            String comment = UUID.randomUUID().toString();
            Rating rating = sut.create(link, note, comment);
            sut.write(rating);
        }
        Page<Rating> page = sut.findByDomain(domain, new Pagination(10, null));
        assertEquals(5, page.size());
    }

    public void findByLink_should_find_by_link() {
        RatedLink link = givenLink();
        for (int i = 0; i < 5; i++) {
            int note = 5;
            String comment = UUID.randomUUID().toString();
            Rating rating = sut.create(link, note, comment);
            sut.write(rating);
        }
        Page<Rating> page = sut.findByLink(link, new Pagination(10, null));
        assertEquals(5, page.size());
    }
}

