package co.digamma.netnote.persistence.common;

import co.digamma.netnote.common.Identifiable;
import co.digamma.netnote.common.Timestamped;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@MappedSuperclass
public abstract class JpaBaseEntity
    implements Identifiable<String>, Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, columnDefinition = "bpchar")
    protected String id;

    @CreationTimestamp
    protected LocalDateTime timestamp;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
