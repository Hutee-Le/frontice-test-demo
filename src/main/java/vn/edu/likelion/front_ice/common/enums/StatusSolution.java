package vn.edu.likelion.front_ice.common.enums;

/**
 * Status -
 *
 * @param
 * @return
 * @throws
 */
public enum StatusSolution {

    EMPTY("empty"),
    PROCESSING("processing"),
    APPROVED("approved"),
    HIDE("hide"),
    CANCEL("cancel");

    private final String status;

     StatusSolution(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }


}
