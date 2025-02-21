package co.digamma.netnote.persistence.navigation;

import co.digamma.netnote.navigation.RatedLocatable;
import co.digamma.netnote.persistence.common.JpaBaseEntity;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class JpaLocatable
        extends JpaBaseEntity
        implements RatedLocatable {

    @Column(unique = true, nullable = false)
    private String url;

    @Nonnull
    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(@Nonnull String url) {
        this.url = url;
    }
}
