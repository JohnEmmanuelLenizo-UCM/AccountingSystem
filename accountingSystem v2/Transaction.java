public class Transaction {
    public String date;
    public String description;
    public String debitAccount;
    public String creditAccount;
    public double amount;

    public Transaction(String date, String description, String debitAccount, String creditAccount, double amount) {
        this.date = date;
        this.description = description;
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
        this.amount = amount;
    }
}
