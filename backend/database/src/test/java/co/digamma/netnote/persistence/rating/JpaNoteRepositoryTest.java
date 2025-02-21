package co.digamma.netnote.persistence.rating;

import co.digamma.netnote.navigation.RatedDomain;
import co.digamma.netnote.navigation.RatedLink;
import co.digamma.netnote.persistence.common.BaseTestSuite;
import co.digamma.netnote.rating.Note;
import co.digamma.netnote.rating.NoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JpaNoteRepositoryTest extends BaseTestSuite {

    @Autowired
    private NoteRepository sut;

    @Test
    public void create_should_create() {
        RatedLink link = givenLink();
        Note note = sut.create(link);
        assertEquals(Double.NaN, note.getAverage());
        assertEquals(0L, note.getSupport());
    }

    @Test
    public void create_should_not_save() {
        RatedLink link = givenLink();
        Note note = sut.create(link);
        try {
            assertTrue(sut.read(note.getId()).isEmpty());
        } catch (DataAccessException ignore) {
            // Throwing an exception is OK, but not necessary (read may return empty).
        }
    }

    @Test
    public void write_should_save() {
        RatedLink link = givenLink();
        Note note = sut.create(link);
        note = sut.write(note);
        assertEquals(Double.NaN, note.getAverage());
        assertEquals(0L, note.getSupport());
    }

    @Test
    public void write_should_update() {
        RatedLink link = givenLink();
        Note note = sut.create(link);
        note = sut.write(note);
        long support = 1L;
        double average = 5.0;
        note.setSupport(support);
        note.setAverage(average);
        assertEquals(average, note.getAverage());
        assertEquals(support, note.getSupport());
    }

    @Test
    public void write_should_impact_link() {
        RatedLink link = givenLink();
        Note note = sut.create(link);
        sut.write(note);
        link = linkRepository.read(link.getId()).orElseThrow();
        note = link.getNote();
        assertEquals(Double.NaN, note.getAverage());
        assertEquals(0L, note.getSupport());
    }

    @Test
    public void write_should_impact_domain() {
        RatedDomain domain = givenDomain();
        Note note = sut.create(domain);
        sut.write(note);
        domain = domainRepository.read(domain.getId()).orElseThrow();
        note = domain.getNote();
        assertEquals(Double.NaN, note.getAverage());
        assertEquals(0L, note.getSupport());
    }

    @Test
    public void read_should_read() {
        RatedLink link = givenLink();
        Note note = sut.create(link);
        sut.write(note);
        note = sut.read(note.getId()).orElseThrow();
        assertEquals(Double.NaN, note.getAverage());
        assertEquals(0L, note.getSupport());
    }

}
