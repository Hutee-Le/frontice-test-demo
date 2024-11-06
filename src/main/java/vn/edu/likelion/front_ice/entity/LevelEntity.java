package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;


/**
 * LevelEntity -
 *
 * @param
 * @return
 * @throws
 */
@Entity
@Table(name = "tbl_level")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LevelEntity extends BaseEntity {

    @Column
    String title;

    @Column
    int minScore;

    @Column
    int maxScore;
}

