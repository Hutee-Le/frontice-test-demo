package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;


/**
 * TechnicalEntity -
 *
 * @param
 * @return
 * @throws
 */
@Entity
@Table(name = "tbl_technical")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TechnicalEntity extends BaseEntity {

    @Column
    String title;

}

