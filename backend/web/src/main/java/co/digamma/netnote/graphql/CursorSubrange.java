package co.digamma.netnote.graphql;

import co.digamma.netnote.common.Cursor;
import co.digamma.netnote.common.Pagination;
import org.springframework.graphql.data.pagination.Subrange;

public class CursorSubrange extends Subrange<Cursor> {


    /**
     * Constructor with the relative position, count, and direction.
     *
     * @param position the position in the entire collection
     * @param count    the number of elements in the subrange
     * @param forward  whether the subrange is forward or backward from ths position
     */
    public CursorSubrange(Cursor position, Integer count, boolean forward) {
        super(position, count, forward);
    }

    /**
     * Constructor with the relative position and count.
     *
     * @param position the position in the entire collection
     * @param count    the number of elements in the subrange
     */
    public CursorSubrange(Cursor position, Integer count) {
        this(position, count, true);
    }

    Pagination toPagination() {
        return new Pagination(this.count().orElse(10), this.position().orElse(null));
    }
}
