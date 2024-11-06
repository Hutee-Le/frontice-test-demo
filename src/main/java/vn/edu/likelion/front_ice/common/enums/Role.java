package vn.edu.likelion.front_ice.common.enums;

/**
 * Role -
 *
 * @param
 * @return
 * @throws
 */
public enum Role {

    ADMIN("admin"), //root
    MANAGER("manager"),
    MENTOR("mentor"),
    RECRUITER("recruiter"),
    CHALLENGER("challenger");
    ;

    private final String role;
    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
