package vn.edu.likelion.front_ice.common.enums;

public enum AccountType {

    CHALLENGER("challenger"),
    STAFF("staff"),
    RECRUITER("recruiter")
    ;
    private final String accountType;
    

    AccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountType() {
        return accountType;
    }
}
