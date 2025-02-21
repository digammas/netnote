package co.digamma.netnote.common;

public record Pagination(
    int size,
    Cursor cursor
) {}
