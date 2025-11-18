import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.*;


public class Frame {
    public JFrame frame = new JFrame("Accounting System");
    Transaction[] transactions = new Transaction[200];
    int count = 0;
    public Frame() {
        //main frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 720);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Object nga tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Himo panels for each tab
        JPanel HeaderJPanel = new JPanel();
        HeaderJPanel.setBackground(Color.DARK_GRAY);
        HeaderJPanel.setPreferredSize(new Dimension(0, 60));
        HeaderJPanel.setLayout(null);
        JLabel headerLabel = new JLabel("Accounting System");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 30));
        headerLabel.setBounds(5, 5, 400, 40);
        HeaderJPanel.add(headerLabel);

        JPanel NewTransaction = new JPanel();
        NewTransaction.add(new JLabel("New Transaction"));
        NewTransaction.setLayout(null);
        
        //Labels dri
        JLabel DateLabel = new JLabel("Date(YYYY-MM-DD):");
        DateLabel.setBounds(20, 10, 210, 30);
        NewTransaction.add(DateLabel);

        JLabel DescriptionLabel = new JLabel("Description:");
        DescriptionLabel.setBounds(20, 40, 100, 30);
        NewTransaction.add(DescriptionLabel);

        JLabel DebitAccLabel = new JLabel("Debit Account:");
        DebitAccLabel.setBounds(20, 70, 100, 30);
        NewTransaction.add(DebitAccLabel);

        JLabel CreditAccLabel = new JLabel("Credit Account:");
        CreditAccLabel.setBounds(20, 100, 100, 30);
        NewTransaction.add(CreditAccLabel);

        JLabel AmountLabel = new JLabel("Amount:");
        AmountLabel.setBounds(20, 130, 100, 30);
        NewTransaction.add(AmountLabel);

        //TextFields dri
        JTextField DateField = new JTextField();
        DateField.setBounds(140, 12, 1000, 25);
        NewTransaction.add(DateField);

        JTextField DescriptionField = new JTextField();
        DescriptionField.setBounds(140, 42, 1000, 25);
        NewTransaction.add(DescriptionField);

        JTextField AmountField = new JTextField();
        AmountField.setBounds(140, 132, 1000, 25);
        NewTransaction.add(AmountField);

        //Buttons dri
        JButton btn1 = new JButton("Reset");
        NewTransaction.add(btn1);
        btn1.setBounds(420, 200, 100, 30);

        JButton btn2 = new JButton("Clear");
        NewTransaction.add(btn2);
        btn2.setBounds(530, 200, 100, 30);

        JButton btn3 = new JButton("Save");
        NewTransaction.add(btn3);
        btn3.setBounds(640, 200, 100, 30);
        
        //Choices nis Drop Box(E adjust nyalang ni nga paras debit ra ug paras credit nya e bonus nalang ang equity)
        String[] debitAccount = {
        "Choose Account", "Cash (Asset)", "Accounts Receivable (Asset)", "Accounts Payable (Liability)","Notes Payable (Liability)",
        "Sales Revenue (Income)", "Inventory (Asset)", "Prepaid Expense (Asset)",
        "Owner's Capital (Equity)", "Service Revenue (Income)", "Cost of Goods Sold (Expense)",
        "Equipment (Asset)", "Utilities Expense (Expense)", "Salaries Expense (Expense)", "Rent Expense (Expense)"
         };

        String[] creditAccount = {
        "Choose Account", "Cash (Asset)", "Accounts Receivable (Asset)", "Accounts Payable (Liability)","Notes Payable (Liability)",
        "Sales Revenue (Income)", "Inventory (Asset)", "Prepaid Expense (Asset)",
        "Owner's Capital (Equity)", "Service Revenue (Income)", "Cost of Goods Sold (Expense)",
        "Equipment (Asset)", "Utilities Expense (Expense)", "Salaries Expense (Expense)", "Rent Expense (Expense)"
         };

        JComboBox<String> debitDropDown = new JComboBox<>(debitAccount);
        debitDropDown.setSelectedIndex(0);

        JComboBox<String> creditDropDown = new JComboBox<>(creditAccount);
        creditDropDown.setSelectedIndex(0);

        NewTransaction.add(debitDropDown);
        debitDropDown.setBounds(140, 72, 1000, 25);
        NewTransaction.add(creditDropDown);
        creditDropDown.setBounds(140, 102, 1000, 25);


        // Mo refresh nis panel para makita ang new component pero para pop-up2 ta ni nya warasa nako gi gamit
        NewTransaction.revalidate();
        NewTransaction.repaint();
        
        JPanel TranscationTab = new JPanel();
        TranscationTab.add(new JLabel("Transactions"));

        JPanel AccountsTab = new JPanel();
        AccountsTab.add(new JLabel("Accounts"));

        JPanel JournalTab = new JPanel();
        JournalTab.add(new JLabel("General Journal"));

        JPanel LedgerTab = new JPanel();
        LedgerTab.add(new JLabel("General Ledger"));

        JPanel BalanceSheetTab = new JPanel();
        BalanceSheetTab.add(new JLabel("Balance Sheet"));

        // Add ug panels sa mga tabbed pane
        tabbedPane.addTab("New Transaction", NewTransaction);
        tabbedPane.addTab("Transactions", TranscationTab);
        tabbedPane.addTab("Accounts", AccountsTab);
        tabbedPane.addTab("General Journal", JournalTab);
        tabbedPane.addTab("General Ledger", LedgerTab);
        tabbedPane.addTab("Balance Sheet", BalanceSheetTab);

        // Add tabbed pane to frame
        frame.add(tabbedPane);
        frame.add(HeaderJPanel, BorderLayout.NORTH);

        //functions ni diri
        btn2.addActionListener((ActionEvent e) -> {
            DateField.setText(null);
            DescriptionField.setText(null);
            debitDropDown.setSelectedIndex(0);
            creditDropDown.setSelectedIndex(0);
            AmountField.setText(null);
        });

        btn3.addActionListener((ActionEvent e) -> {
            String date = DateField.getText();
            String description = DescriptionField.getText();
            String debit = (String) debitDropDown.getSelectedItem();
            String credit = (String) creditDropDown.getSelectedItem();
            double amount = Double.parseDouble(AmountField.getText());
            count++;
            int number = count;
            transactions[count] = new Transaction(number, date, description, debit, credit, amount);

            JOptionPane.showMessageDialog(null, "Transaction Saved!", "Transactions", JOptionPane.INFORMATION_MESSAGE);
            
            DateField.setText(null);
            DescriptionField.setText(null);
            debitDropDown.setSelectedIndex(0);
            creditDropDown.setSelectedIndex(0);
            AmountField.setText(null);
            //trial purposes
            System.out.println("Transaction number: " + number +
                                "\nDate: " + date + 
                                "\nDescription: " + description + 
                                "\nDebit: " + debit + 
                                "\nCredit: " + credit + 
                                "\nAmount: " + amount);
        });
        
    }
    // Make frame visible
    public void show() {
        frame.setVisible(true);
    }
}
