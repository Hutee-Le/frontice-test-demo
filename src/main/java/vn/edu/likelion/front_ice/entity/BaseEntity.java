package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;
import vn.edu.likelion.front_ice.common.utils.UniqueID;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * BaseEntity -
 *
 * @param
 * @return
 * @throws
 */
@MappedSuperclass
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseEntity implements Serializable {

    @Id
    @UuidGenerator
    String id;

    @Column(nullable = false, updatable = false)
    LocalDateTime createAt;

    @Column(nullable = true, insertable = false)
    LocalDateTime updateAt;

    @Column(nullable = false)
    int isDeleted;

    @PrePersist
    protected void onCreate() {

        if (id == null) {
            id = UniqueID.getUUID();
        }
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateAt = LocalDateTime.now();
    }
}
