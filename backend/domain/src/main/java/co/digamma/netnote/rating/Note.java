package co.digamma.netnote.rating;

import co.digamma.netnote.common.Identifiable;
import co.digamma.netnote.common.Timestamped;

public interface Note extends Identifiable<String>, Timestamped {

    double getAverage();
    void setAverage(double value);

    long getSupport();
    void setSupport(long value);

    default void update(int value) {
        long newSupport = this.getSupport() + 1;
        this.setAverage((this.getAverage() * this.getSupport() + value) / newSupport);
        this.setSupport(newSupport);
    }

    default boolean isUnknown() {
        return this.getSupport() == 0;
    }
}
