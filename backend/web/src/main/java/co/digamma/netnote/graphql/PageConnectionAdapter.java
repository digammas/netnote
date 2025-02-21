package co.digamma.netnote.graphql;

import co.digamma.netnote.common.Cursor;
import co.digamma.netnote.common.Page;
import jakarta.annotation.Nonnull;
import java.util.Collection;
import org.springframework.graphql.data.pagination.ConnectionAdapter;
import org.springframework.graphql.data.pagination.ConnectionAdapterSupport;
import org.springframework.stereotype.Component;

@Component
public class PageConnectionAdapter
        extends ConnectionAdapterSupport<Cursor>
        implements ConnectionAdapter {

    public PageConnectionAdapter(PageCursorStrategy cursorStrategy) {
        super(cursorStrategy);
    }

    @Override
    public boolean supports(@Nonnull Class<?> containerType) {
        return Page.class.isAssignableFrom(containerType);
    }

    @Nonnull
    @Override
    public <T> Collection<T> getContent(@Nonnull Object container) {
        Page<T> page = page(container);
        return page.items();
    }

    @Override
    public boolean hasPrevious(@Nonnull Object container) {
        Page<?> page = page(container);
        return page.hasBefore();
    }

    @Override
    public boolean hasNext(@Nonnull Object container) {
        Page<?> page = page(container);
        return page.hasAfter();
    }

    @Nonnull
    @Override
    public String cursorAt(@Nonnull Object container, int index) {
        Page<?> page = page(container);
        if (page.size() == 0) {
            return "";
        }
        if (index == 0) {
            return this.getCursorStrategy().toCursor(page.startCursor());
        }
        if (index == page.size() - 1) {
            return this.getCursorStrategy().toCursor(page.endCursor());
        }
        String prefix = this.getCursorStrategy().toCursor(page.startCursor());
        return "%s+%05d".formatted(prefix, index);
    }

    @SuppressWarnings("unchecked")
    private <T> Page<T> page(@Nonnull Object object) {
        return (Page<T>) object;
    }
}
