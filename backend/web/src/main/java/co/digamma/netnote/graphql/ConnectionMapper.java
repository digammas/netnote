package co.digamma.netnote.graphql;

import co.digamma.netnote.common.Page;
import graphql.relay.Connection;
import graphql.relay.ConnectionCursor;
import graphql.relay.DefaultConnection;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.DefaultEdge;
import graphql.relay.DefaultPageInfo;
import graphql.relay.Edge;
import graphql.relay.PageInfo;
import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface ConnectionMapper<I, O> {

    ConnectionCursor NULL_CURSOR = () -> null;

    default Connection<O> from(Page<I> page) {
        List<Edge<O>> edges = page.items().stream()
                .map(this::map)
                .map(this::toEdge)
                .toList();
        PageInfo pageInfo = new DefaultPageInfo(
                new DefaultConnectionCursor(page.startCursor().toString()),
                new DefaultConnectionCursor(page.endCursor().toString()),
                page.hasBefore(),
                page.hasAfter()
        );
        return new DefaultConnection<>(edges, pageInfo);
    }

    O map(I input);

    private <T> Edge<T> toEdge(T item) {
        return new DefaultEdge<>(item, NULL_CURSOR);
    }

    static <I, O> Connection<O> from(Page<I> page, Function<I, O> mapper) {
        return ((ConnectionMapper<I, O>) mapper::apply).from(page);
    }
}
