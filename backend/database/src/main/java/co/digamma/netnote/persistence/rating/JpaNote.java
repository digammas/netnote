package co.digamma.netnote.persistence.rating;

import co.digamma.netnote.persistence.common.JpaBaseEntity;
import co.digamma.netnote.persistence.navigation.JpaDomain;
import co.digamma.netnote.persistence.navigation.JpaLink;
import co.digamma.netnote.rating.Note;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class JpaNote
        extends JpaBaseEntity
        implements Note {

    private double average = Double.NaN;
    private long support = 0;

    @OneToOne
    @JoinColumn(unique = true)
    private JpaLink link;

    @OneToOne
    @JoinColumn(unique = true)
    private JpaDomain domain;

    @Override
    public double getAverage() {
        return average;
    }

    @Override
    public void setAverage(double average) {
        this.average = average;
    }

    @Override
    public long getSupport() {
        return support;
    }

    @Override
    public void setSupport(long support) {
        this.support = support;
    }

    public JpaLink getLink() {
        return link;
    }

    public void setLink(JpaLink link) {
        this.link = link;
    }

    public JpaDomain getDomain() {
        return domain;
    }

    public void setDomain(JpaDomain domain) {
        this.domain = domain;
    }
}
