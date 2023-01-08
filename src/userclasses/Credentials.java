package userclasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import inputclasses.CredentialsInput;
@JsonIgnoreProperties({"balanceNr"})
public final class Credentials {
    private String name;
    private String password;
    private String accountType;
    private String country;
    private int balanceNr;

    private String balance;

    public Credentials(final CredentialsInput credentials) {
        this.name = credentials.getName();
        this.password = credentials.getPassword();
        this.accountType = credentials.getAccountType();
        this.country = credentials.getCountry();
        this.balanceNr = credentials.getBalance();
        this.balance = Integer.toString(this.balanceNr);
    }
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(final String accountType) {
        this.accountType = accountType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public int getBalanceNr() {
        return balanceNr;
    }

    /**
     * Saves the int value of the value while also converting it to string for the output.
     * @param balanceNr number value for balance
     */
    public void setBalanceNr(final int balanceNr) {
        this.balanceNr = balanceNr;
        setBalance(Integer.toString(this.balanceNr));
    }

    /**
     * @param credentials Input data
     * @return if the account is correct or not (aka has the same password)
     */
    public boolean checkPassword(final Credentials credentials) {
        return this.password.equals(credentials.getPassword());
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(final String balance) {
        this.balance = balance;
    }

}
