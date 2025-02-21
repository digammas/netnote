package co.digamma.netnote.navigation;

import co.digamma.netnote.common.Identifiable;
import co.digamma.netnote.common.Timestamped;
import jakarta.annotation.Nonnull;

import java.net.URI;

public interface Locatable extends Identifiable<String>, Timestamped {

    @Nonnull
    String getUrl();

    @Nonnull
    default URI asURI() {
        return URI.create(this.getUrl());
    }

    static String normalizeUrl(String url) {
        return URI.create(url)
                .normalize()
                .toString()
                .replaceAll("/$", "");
    }
}
