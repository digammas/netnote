package co.digamma.netnote.persistence.navigation;

import co.digamma.netnote.navigation.RatedDomain;
import co.digamma.netnote.persistence.rating.JpaNote;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class JpaDomain
        extends JpaLocatable
        implements RatedDomain {

    @OneToOne(mappedBy = "domain")
    private JpaNote note;

    @Nonnull
    @Override
    public JpaNote getNote() {
        return this.note;
    }
}
