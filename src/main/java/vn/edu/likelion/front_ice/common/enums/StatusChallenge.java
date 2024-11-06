package vn.edu.likelion.front_ice.common.enums;

/**
 * StatusChallenge -
 *
 * @param
 * @return
 * @throws
 */
public enum StatusChallenge {

    PROCESSING("processing"),
    APPROVED("approved"),
    HIDE("hide"),
    CANCEL("cancel");

    private final String status;

    StatusChallenge(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
