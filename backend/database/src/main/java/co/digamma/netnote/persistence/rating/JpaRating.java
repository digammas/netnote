package co.digamma.netnote.persistence.rating;

import co.digamma.netnote.persistence.common.JpaBaseEntity;
import co.digamma.netnote.persistence.navigation.JpaLink;
import co.digamma.netnote.rating.Rating;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class JpaRating
        extends JpaBaseEntity
        implements Rating {

    private int note;
    private String comment;

    @OneToOne(optional = false)
    private JpaLink link;

    @Override
    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    @Override
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Nonnull
    public JpaLink getLink() {
        return link;
    }

    public void setLink(@Nonnull JpaLink link) {
        this.link = link;
    }
}
