package co.digamma.netnote.navigation;

import jakarta.annotation.Nonnull;
import java.net.URI;

public interface Link extends Locatable {

    @Nonnull
    static String getDomainFromUrl(String url) {
        return URI.create(url).getHost();
    }

    @Nonnull
    Domain getDomain();
}
