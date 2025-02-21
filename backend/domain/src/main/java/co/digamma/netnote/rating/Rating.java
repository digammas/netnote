package co.digamma.netnote.rating;

import co.digamma.netnote.common.Identifiable;
import co.digamma.netnote.common.Timestamped;

public interface Rating extends Identifiable<String>, Timestamped {

    int getNote();

    String getComment();
}
