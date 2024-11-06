package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;


/**
 * RecruiterEntity -
 *
 * @param
 * @return
 * @throws
 */
@Entity
@Table(name = "tbl_recruiter")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecruiterEntity extends BaseEntity {

    @Column
    String accountId;

    @Column
    String name;

    @Column
    String description;

    @Column
    String urlWebsite;

    @Column
    String address;
    
}
