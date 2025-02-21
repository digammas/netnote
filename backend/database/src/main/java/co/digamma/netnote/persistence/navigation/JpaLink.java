package co.digamma.netnote.persistence.navigation;

import co.digamma.netnote.navigation.RatedLink;
import co.digamma.netnote.persistence.rating.JpaNote;
import co.digamma.netnote.rating.Note;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class JpaLink
        extends JpaLocatable
        implements RatedLink {

    @ManyToOne
    private JpaDomain domain;

    @OneToOne(mappedBy = "link")
    private JpaNote note;

    @Nonnull
    @Override
    public JpaDomain getDomain() {
        return domain;
    }

    public void setDomain(@Nonnull JpaDomain domain) {
        this.domain = domain;
    }

    @Nonnull
    @Override
    public Note getNote() {
        return this.note;
    }
}
