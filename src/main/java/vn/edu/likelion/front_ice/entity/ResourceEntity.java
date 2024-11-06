package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;


/**
 * ResourceEntity -
 *
 * @param
 * @return
 * @throws
 */
@Entity
@Table(name = "tbl_resource")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResourceEntity extends BaseEntity {

    @Column
    String challenge_id;

    @Column
    String url_resource;

}

