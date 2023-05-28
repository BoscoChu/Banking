
package TransactionHistory;


public class History {
    private String name;
    private String amount;
    private String balance;
    private String date;
    private String time;
    private String type;

    public History(String name, String amount, String balance, String date, String time) {
        this.name = name;
        this.amount = amount;
        this.balance = balance;
        this.date = date;
        this.time = time;
    }
      



    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public String getBalance() {
        return balance;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
    
}
