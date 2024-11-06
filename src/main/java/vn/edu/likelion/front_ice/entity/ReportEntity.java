package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;


/**
 * ReportEntity -
 *
 * @param
 * @return
 * @throws
 */
@Entity
@Table(name = "tbl_feedback")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportEntity extends BaseEntity {

    @Column
    String solutionId;

    @Column
    String accountId;

    @Column
    String feedbackId;

    @Column
    String reason;
    
}

