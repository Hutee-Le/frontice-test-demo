package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.likelion.front_ice.common.enums.StatusSolution;


/**
 * SolutionEntity -
 *
 * @param
 * @return
 * @throws
 */
@Entity
@Table(name = "tbl_solution")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SolutionEntity extends BaseEntity {

    @Column
    String challengerId;

    @Column
    String challengeId;

    @Column
    String urlProduct;

    @Column
    String urlRepository;

    @Column
    String title;

    @Column
    String description;

    @Column
    String note;

   @Column
   StatusSolution statusSolution;

}

