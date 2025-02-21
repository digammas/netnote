package co.digamma.netnote.navigation;

import jakarta.annotation.Nonnull;

public interface RatedLink extends Link, RatedLocatable {

    @Nonnull
    @Override
    RatedDomain getDomain();
}
