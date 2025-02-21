package co.digamma.netnote.common;

import jakarta.annotation.Nonnull;

public record Cursor(
    @Nonnull String code
) {

    @Override
    public String toString() {
        return this.code;
    }
}
