public class Account {
    public String name;
    public String type;
    public double balance;

    public Account(String name, String type) {
        this.name = name;
        this.type = type;
        this.balance = 0; // starts with zero
    }
}
