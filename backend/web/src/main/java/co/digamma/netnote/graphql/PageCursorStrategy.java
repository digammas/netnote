package co.digamma.netnote.graphql;

import co.digamma.netnote.common.Cursor;
import jakarta.annotation.Nonnull;
import org.springframework.graphql.data.pagination.CursorStrategy;
import org.springframework.stereotype.Component;

@Component
public class PageCursorStrategy implements CursorStrategy<Cursor> {

    @Override
    public boolean supports(@Nonnull Class<?> targetType) {
        return Cursor.class.isAssignableFrom(targetType);
    }

    @Nonnull
    @Override
    public String toCursor(@Nonnull Cursor position) {
        return position.toString();
    }

    @Nonnull
    @Override
    public Cursor fromCursor(@Nonnull String cursor) {
        return new Cursor(cursor);
    }
}
