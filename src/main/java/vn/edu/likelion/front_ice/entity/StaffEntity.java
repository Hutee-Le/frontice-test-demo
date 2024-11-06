package vn.edu.likelion.front_ice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.likelion.front_ice.common.enums.Role;

import java.util.HashSet;
import java.util.Set;


/**
 * StaffEntity -
 *
 * @param
 * @return
 * @throws
 */
@Entity
@Table(name = "tbl_staff")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffEntity extends BaseEntity {
    @Column
    String accountId;

    @Column
    String nameStaff;

    @Column
    Role role;
}
