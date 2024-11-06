package vn.edu.likelion.front_ice.common.exceptions;

public enum ValidationAttribute {
    MIN("min"),
    MAX("max"),
    VALUE("value"),
    NOT_NULL("notNull"),
    SIZE("size");
    // Thêm các thuộc tính khác mà bạn cần

    private final String key;

    ValidationAttribute(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
