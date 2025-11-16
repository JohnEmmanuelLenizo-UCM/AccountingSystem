import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;

public class AccountingSystem {
    public static void main(String[] args) {
        //main frame
        JFrame frame = new JFrame("JTabbedPane Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

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

        JPanel NewTranscation = new JPanel();
        NewTranscation.add(new JLabel("New Transaction"));
        NewTranscation.setLayout(null);
        
        //Labels dri
        JLabel DateLabel = new JLabel("Date(YYYY-MM-DD):");
        DateLabel.setBounds(20, 10, 210, 30);
        NewTranscation.add(DateLabel);

        JLabel DescriptionLabel = new JLabel("Description:");
        DescriptionLabel.setBounds(20, 40, 100, 30);
        NewTranscation.add(DescriptionLabel);

        JLabel DebitAccLabel = new JLabel("Debit Account:");
        DebitAccLabel.setBounds(20, 70, 100, 30);
        NewTranscation.add(DebitAccLabel);

        JLabel CreditAccLabel = new JLabel("Credit Account:");
        CreditAccLabel.setBounds(20, 100, 100, 30);
        NewTranscation.add(CreditAccLabel);

        JLabel AmountLabel = new JLabel("Amount:");
        AmountLabel.setBounds(20, 130, 100, 30);
        NewTranscation.add(AmountLabel);

        //TextFields dri
        JTextField DateField = new JTextField();
        DateField.setBounds(140, 12, 200, 25);
        NewTranscation.add(DateField);

        JTextField DescriptionField = new JTextField();
        DescriptionField.setBounds(140, 42, 200, 25);
        NewTranscation.add(DescriptionField);

        JTextField AmountField = new JTextField();
        AmountField.setBounds(140, 132, 200, 25);
        NewTranscation.add(AmountField);

        //Buttons dri
        JButton btn1 = new JButton("Reset");
        NewTranscation.add(btn1);
        btn1.setBounds(310, 400, 100, 30);

        JButton btn2 = new JButton("Clear");
        NewTranscation.add(btn2);
        btn2.setBounds(420, 400, 100, 30);

        JButton btn3 = new JButton("Save");
        NewTranscation.add(btn3);
        btn3.setBounds(530, 400, 100, 30);
        
        //Choices nis Drop Box(E adjust nyalang ni nga paras debit ra ug paras credit nya e bonus nalang ang equity)
        String[] DebitDropDown = {
        "Choose Account", "Cash (Asset)", "Accounts Receivable (Asset)", "Accounts Payable (Liability)","Notes Payable (Liability)",
        "Sales Revenue (Income)", "Inventory (Asset)", "Prepaid Expense (Asset)",
        "Owner's Capital (Equity)", "Service Revenue (Income)", "Cost of Goods Sold (Expense)",
        "Equipment (Asset)", "Utilities Expense (Expense)", "Salaries Expense (Expense)", "Rent Expense (Expense)"
         };

        String[] CreditDropDown = {
        "Choose Account", "Cash (Asset)", "Accounts Receivable (Asset)", "Accounts Payable (Liability)","Notes Payable (Liability)",
        "Sales Revenue (Income)", "Inventory (Asset)", "Prepaid Expense (Asset)",
        "Owner's Capital (Equity)", "Service Revenue (Income)", "Cost of Goods Sold (Expense)",
        "Equipment (Asset)", "Utilities Expense (Expense)", "Salaries Expense (Expense)", "Rent Expense (Expense)"
         };

        JComboBox<String> accountDropdown = new JComboBox<>(DebitDropDown);
        accountDropdown.setSelectedIndex(0);

        JComboBox<String> CreditDropdown = new JComboBox<>(CreditDropDown);
        CreditDropdown.setSelectedIndex(0);

        NewTranscation.add(accountDropdown);
        accountDropdown.setBounds(140, 72, 200, 25);
        NewTranscation.add(CreditDropdown);
        CreditDropdown.setBounds(140, 102, 200, 25);


        // Mo refresh nis panel para makita ang new component pero para pop-up2 ta ni nya warasa nako gi gamit
        NewTranscation.revalidate();
        NewTranscation.repaint();
        



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
        tabbedPane.addTab("New Transaction", NewTranscation);
        tabbedPane.addTab("Transactions", TranscationTab);
        tabbedPane.addTab("Accounts", AccountsTab);
        tabbedPane.addTab("General Journal", JournalTab);
        tabbedPane.addTab("General Ledger", LedgerTab);
        tabbedPane.addTab("Balance Sheet", BalanceSheetTab);

        // Add tabbed pane to frame
        frame.add(tabbedPane);
        frame.add(HeaderJPanel, BorderLayout.NORTH);

        //New Transaction Tab
        //NewTranscation.add(accountDropdown);
        //NewTranscation.add(btn1);

        // Make frame visible
        frame.setVisible(true);
    }
    
    
}
