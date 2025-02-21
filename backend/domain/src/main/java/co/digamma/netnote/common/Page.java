package co.digamma.netnote.common;

import jakarta.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public record Page<T>(
        @Nonnull List<T> items,
        Cursor startCursor,
        Cursor endCursor,
        boolean hasBefore,
        boolean hasAfter,
        Optional<Long> totalNumberOfItems
) {
    public int size() {
        return this.items.size();
    }

    public static <T> Page<T> empty() {
        return new Page<>(List.of(), null, null, false, false, Optional.of(0L));
    }
}
