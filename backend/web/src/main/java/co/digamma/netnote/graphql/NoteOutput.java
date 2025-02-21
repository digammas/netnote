package co.digamma.netnote.graphql;

import co.digamma.netnote.rating.Note;

public record NoteOutput(
        double average,
        long support
) {
    public static final NoteOutput UNKNOWN = new NoteOutput(0, 0);

    public static NoteOutput from(Note note) {
        return new NoteOutput(note.getAverage(), note.getSupport());
    }
}
