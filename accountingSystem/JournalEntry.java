public class JournalEntry {
    public String date;
    public String description;
    public String account;
    public double debit;
    public double credit;

    public JournalEntry(String date, String description, String account, double debit, double credit) {
        this.date = date;
        this.description = description;
        this.account = account;
        this.debit = debit;
        this.credit = credit;
    }
}
