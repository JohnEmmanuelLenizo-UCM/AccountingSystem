public class Transaction {
    public int number;
    public String date;
    public String description;
    public String debit;
    public String credit;
    public double amount;

    public Transaction(int number, String date, String description, String debit, String credit, double amount) {
        this.number = number;
        this.date = date;
        this.description = description;
        this.debit = debit;
        this.credit = credit;
        this.amount = amount;
    }
}
