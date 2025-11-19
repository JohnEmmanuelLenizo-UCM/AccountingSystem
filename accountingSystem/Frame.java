import java.awt.*;
import java.awt.print.PrinterException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.SpinnerDateModel;

public class Frame {
    public JFrame frame = new JFrame("Accounting System");

    private JTextField DateField, DescriptionField, AmountField;
    private JComboBox<String> debitDropdown, CreditDropdown, ledgerAccountDropdown;
    private JTable transactionTable, accountsTable, journalTable, ledgerTable, assetTable, liabilityTable;
    private DefaultTableModel tableModel, accountsTableModel, journalTableModel, ledgerTableModel, assetModel, liabilityModel;

    public Frame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 720);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JTabbedPane tabbedPane = new JTabbedPane();

        // ======================== HEADER ========================
        JPanel HeaderJPanel = new JPanel();
        HeaderJPanel.setBackground(Color.DARK_GRAY);
        HeaderJPanel.setPreferredSize(new Dimension(0, 60));
        HeaderJPanel.setLayout(null);
        JLabel headerLabel = new JLabel("Accounting System");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 30));
        headerLabel.setBounds(5, 5, 400, 40);
        HeaderJPanel.add(headerLabel);

        // ======================== NEW TRANSACTION TAB ========================
        JPanel NewTransaction = new JPanel();
        NewTransaction.setLayout(null);

        JLabel DateLabel = new JLabel("Date (YYYY-MM-DD):");
        DateLabel.setBounds(20, 10, 210, 30);
        NewTransaction.add(DateLabel);

        // Date field with calendar icon
        DateField = new JTextField();
        DateField.setBounds(140, 12, 950, 25);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateField.setText(LocalDate.now().format(dtf)); // default today
        NewTransaction.add(DateField);

        JButton calendarButton = new JButton("📅");
        calendarButton.setBounds(1100, 12, 40, 25);
        calendarButton.setFocusPainted(false);
        calendarButton.setMargin(new Insets(0, 0, 0, 0));
        NewTransaction.add(calendarButton);

        calendarButton.addActionListener(e -> {
            JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
            JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
            dateSpinner.setEditor(editor);

            try {
                Date spinnerDate = java.sql.Date.valueOf(LocalDate.parse(DateField.getText()));
                dateSpinner.setValue(spinnerDate);
            } catch (Exception ex) {
                dateSpinner.setValue(new Date());
            }

            int result = JOptionPane.showConfirmDialog(
                frame, dateSpinner, "Select Date",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                Date selectedDate = (Date) dateSpinner.getValue();
                String formattedDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(selectedDate);
                DateField.setText(formattedDate);
            }
        });

        JLabel DescriptionLabel = new JLabel("Description:");
        DescriptionLabel.setBounds(20, 40, 100, 30);
        NewTransaction.add(DescriptionLabel);

        DescriptionField = new JTextField();
        DescriptionField.setBounds(140, 42, 1000, 25);
        NewTransaction.add(DescriptionField);

        JLabel DebitAccLabel = new JLabel("Debit Account:");
        DebitAccLabel.setBounds(20, 70, 100, 30);
        NewTransaction.add(DebitAccLabel);

        JLabel CreditAccLabel = new JLabel("Credit Account:");
        CreditAccLabel.setBounds(20, 100, 100, 30);
        NewTransaction.add(CreditAccLabel);

        JLabel AmountLabel = new JLabel("Amount:");
        AmountLabel.setBounds(20, 130, 100, 30);
        NewTransaction.add(AmountLabel);

        AmountField = new JTextField();
        AmountField.setBounds(140, 132, 1000, 25);
        NewTransaction.add(AmountField);

        String[] accounts = {
            "Choose Account", "Cash", "Accounts Receivable", "Inventory", "Prepaid Expenses",
            "Equipment", "Accounts Payable", "Notes Payable", "Owner's Capital",
            "Sales Revenue", "Service Revenue", "Cost of Goods Sold", "Rent Expense",
            "Salaries Expense", "Utilities Expense"
        };

        debitDropdown = new JComboBox<>(accounts);
        debitDropdown.setBounds(140, 72, 1000, 25);
        NewTransaction.add(debitDropdown);

        CreditDropdown = new JComboBox<>(accounts);
        CreditDropdown.setBounds(140, 102, 1000, 25);
        NewTransaction.add(CreditDropdown);

        JButton btnSave = new JButton("Save");
        btnSave.setBounds(640, 200, 100, 30);
        NewTransaction.add(btnSave);

        JButton btnClear = new JButton("Clear");
        btnClear.setBounds(530, 200, 100, 30);
        NewTransaction.add(btnClear);

        JButton btnReset = new JButton("Reset");
        btnReset.setBounds(420, 200, 100, 30);
        NewTransaction.add(btnReset);

        // ======================== TRANSACTIONS TAB ========================
        JPanel TransactionTab = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[]{"Date", "Description", "Debit", "Credit", "Amount"}, 0);
        transactionTable = new JTable(tableModel);
        TransactionTab.add(new JScrollPane(transactionTable), BorderLayout.CENTER);

        JButton btnDeleteTransaction = new JButton("Delete Selected Transaction");
        TransactionTab.add(btnDeleteTransaction, BorderLayout.SOUTH);
        btnDeleteTransaction.addActionListener(e -> removeTransaction());

        // ======================== ACCOUNTS TAB ========================
        accountsTableModel = new DefaultTableModel(new Object[]{"Account", "Type", "Balance"}, 0);
        accountsTable = new JTable(accountsTableModel);
        JPanel AccountsTab = new JPanel(new BorderLayout());
        AccountsTab.add(new JScrollPane(accountsTable), BorderLayout.CENTER);
        initializeDefaultAccounts();

        // ======================== GENERAL JOURNAL ========================
        journalTableModel = new DefaultTableModel(new Object[]{"Date", "Description", "Account", "Debit", "Credit"}, 0);
        journalTable = new JTable(journalTableModel);
        JPanel JournalTab = new JPanel(new BorderLayout());
        JournalTab.add(new JScrollPane(journalTable), BorderLayout.CENTER);

        // ======================== GENERAL LEDGER ========================
        ledgerTableModel = new DefaultTableModel(new Object[]{"Date", "Description", "Debit", "Credit", "Balance"}, 0);
        ledgerTable = new JTable(ledgerTableModel);
        ledgerAccountDropdown = new JComboBox<>();
        for (Account acc : AccountingData.accounts) ledgerAccountDropdown.addItem(acc.name);
        ledgerAccountDropdown.addActionListener(e -> updateLedger(ledgerAccountDropdown.getSelectedItem().toString()));

        JPanel LedgerTab = new JPanel(new BorderLayout());
        LedgerTab.add(ledgerAccountDropdown, BorderLayout.NORTH);
        LedgerTab.add(new JScrollPane(ledgerTable), BorderLayout.CENTER);

        // ======================== BALANCE SHEET ========================
        JPanel BalanceSheetTab = new JPanel(new BorderLayout());

        JLabel sheetHeader = new JLabel("Balance Sheet");
        sheetHeader.setFont(new Font("Arial", Font.BOLD, 22));
        sheetHeader.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        BalanceSheetTab.add(sheetHeader, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));

        JPanel leftPanel = new JPanel(new BorderLayout());
        JLabel leftLabel = new JLabel("Assets");
        leftLabel.setFont(new Font("Arial", Font.BOLD, 18));
        assetModel = new DefaultTableModel(new Object[]{"Asset", "Amount"}, 0);
        assetTable = new JTable(assetModel);
        leftPanel.add(leftLabel, BorderLayout.NORTH);
        leftPanel.add(new JScrollPane(assetTable), BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        JLabel rightLabel = new JLabel("Liabilities & Equity");
        rightLabel.setFont(new Font("Arial", Font.BOLD, 18));
        liabilityModel = new DefaultTableModel(new Object[]{"Liability / Equity", "Amount"}, 0);
        liabilityTable = new JTable(liabilityModel);
        rightPanel.add(rightLabel, BorderLayout.NORTH);
        rightPanel.add(new JScrollPane(liabilityTable), BorderLayout.CENTER);

        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);
        BalanceSheetTab.add(centerPanel, BorderLayout.CENTER);

        JButton btnRefreshBS = new JButton("Refresh");
        JButton btnPrintBS = new JButton("Print");
        JPanel bsButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bsButtons.add(btnRefreshBS);
        bsButtons.add(btnPrintBS);
        BalanceSheetTab.add(bsButtons, BorderLayout.SOUTH);
        btnRefreshBS.addActionListener(e -> updateBalanceSheet());
        btnPrintBS.addActionListener(e -> printBalanceSheet());

        // ======================== BUTTON ACTIONS ========================
        btnSave.addActionListener(e -> saveTransaction());
        btnClear.addActionListener(e -> ClearFields());
        btnReset.addActionListener(e -> resetSystem());

        // ======================== ADDING TABS ========================
        tabbedPane.addTab("New Transaction", NewTransaction);
        tabbedPane.addTab("Transactions", TransactionTab);
        tabbedPane.addTab("Accounts", AccountsTab);
        tabbedPane.addTab("General Journal", JournalTab);
        tabbedPane.addTab("General Ledger", LedgerTab);
        tabbedPane.addTab("Balance Sheet", BalanceSheetTab);

        frame.add(tabbedPane);
        frame.add(HeaderJPanel, BorderLayout.NORTH);
    }

    // ======================== CORE LOGIC ========================
    private void saveTransaction() {
        try {
            String date = DateField.getText();
            String desc = DescriptionField.getText();
            String debit = debitDropdown.getSelectedItem().toString();
            String credit = CreditDropdown.getSelectedItem().toString();
            double amount = Double.parseDouble(AmountField.getText());

            if (debit.equals("Choose Account") || credit.equals("Choose Account")) {
                JOptionPane.showMessageDialog(frame, "Select valid accounts.");
                return;
            }

            Transaction transaction = new Transaction(date, desc, debit, credit, amount);
            AccountingData.transactions.add(transaction);
            tableModel.addRow(new Object[]{date, desc, debit, credit, amount});

            journalTableModel.addRow(new Object[]{date, desc, debit, amount, ""});
            journalTableModel.addRow(new Object[]{date, desc, credit, "", amount});
            AccountingData.journalEntries.add(new JournalEntry(date, desc, debit, amount, 0));
            AccountingData.journalEntries.add(new JournalEntry(date, desc, credit, 0, amount));

            updateAccountBalance(debit, amount, true);
            updateAccountBalance(credit, amount, false);
            refreshAccountsTable();
            updateLedger(ledgerAccountDropdown.getSelectedItem().toString());
            updateBalanceSheet();

            JOptionPane.showMessageDialog(frame, "Transaction saved.");
            ClearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }
// ============== REMOVE TRANSACTIONS, USES THE RECALCULATE METHOD TO UPDATE ACCOUNT BALANCE ==================
    private void removeTransaction() {
        int selectedRow = transactionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Select a transaction to delete.");
            return;
        }

        String date = tableModel.getValueAt(selectedRow, 0).toString();
        String desc = tableModel.getValueAt(selectedRow, 1).toString();

        AccountingData.transactions.removeIf(t -> t.date.equals(date) && t.description.equals(desc));
        AccountingData.journalEntries.removeIf(j -> j.date.equals(date) && j.description.equals(desc));
        tableModel.removeRow(selectedRow);

        recalculateAccountBalances();
        refreshAccountsTable();
        updateLedger(ledgerAccountDropdown.getSelectedItem().toString());
        updateBalanceSheet();

        JOptionPane.showMessageDialog(frame, "Transaction removed.");
    }
// ============ WAPA NI NAHUMAN ===================
    private void printBalanceSheet() {
        try {
            assetTable.print();
            liabilityTable.print();
            JOptionPane.showMessageDialog(frame, "Balance Sheet sent to printer.");
        } catch (PrinterException pe) {
            JOptionPane.showMessageDialog(frame, "Printing failed: " + pe.getMessage());
        }
    }
// =========== RECALCULATE ACCOUNT BALANCES IF NAAY GI DELETE NGA TRANSACTION ================
    private void recalculateAccountBalances() {
        for (Account acc : AccountingData.accounts) acc.balance = 0;
        for (Transaction t : AccountingData.transactions) {
            updateAccountBalance(t.debitAccount, t.amount, true);
            updateAccountBalance(t.creditAccount, t.amount, false);
        }
    }
// =============== UPDATE BALANCE SHEET ====================
    private void updateBalanceSheet() {
        assetModel.setRowCount(0);
        liabilityModel.setRowCount(0);

        double totalAssets = 0, totalLiaEquity = 0;

        for (Account acc : AccountingData.accounts) {
            if (acc.type.equals("Asset")) {
                assetModel.addRow(new Object[]{acc.name, String.format("%.2f", acc.balance)});
                totalAssets += acc.balance;
            } else {
                liabilityModel.addRow(new Object[]{acc.name, String.format("%.2f", acc.balance)});
                totalLiaEquity += acc.balance;
            }
        }

        assetModel.addRow(new Object[]{"Total Assets", String.format("%.2f", totalAssets)});
        liabilityModel.addRow(new Object[]{"Total Liabilities & Equity", String.format("%.2f", totalLiaEquity)});
    }
// ================ INITIALIZE THE ACCOUNTS PANEL WITH DEFAULT ACCOUNTS VALUES =================
    private void initializeDefaultAccounts() {
        String[][] defaultAccounts = {
            {"Cash", "Asset"}, {"Accounts Receivable", "Asset"}, {"Inventory", "Asset"},
            {"Prepaid Expenses", "Asset"}, {"Equipment", "Asset"},
            {"Accounts Payable", "Liability"}, {"Notes Payable", "Liability"},
            {"Owner's Capital", "Equity"}
        };

        for (String[] accData : defaultAccounts) {
            Account acc = new Account(accData[0], accData[1]);
            AccountingData.accounts.add(acc);
            accountsTableModel.addRow(new Object[]{acc.name, acc.type, "0.00"});
        }
    }
// ===================== UPDATE ACCOUNT BALANCE IN THE ACCOUNTS PANEL ============================== 
    private void updateAccountBalance(String accountName, double amount, boolean isDebit) {
        for (Account acc : AccountingData.accounts) {
            if (acc.name.equals(accountName)) {
                if ((acc.type.equals("Asset") || acc.type.equals("Expense")) && isDebit)
                    acc.balance += amount;
                else if ((acc.type.equals("Liability") || acc.type.equals("Equity") || acc.type.equals("Income")) && !isDebit)
                    acc.balance += amount;
                else
                    acc.balance -= amount;
            }
        }
    }
// ================ REFRESH BALANCE SHEET =======================
    private void refreshAccountsTable() {
        accountsTableModel.setRowCount(0);
        for (Account acc : AccountingData.accounts)
            accountsTableModel.addRow(new Object[]{acc.name, acc.type, String.format("%.2f", acc.balance)});
    }
// ================ UPDATE GENERAL LEDGER =========================
    private void updateLedger(String accountName) {
        ledgerTableModel.setRowCount(0);
        double runningBalance = 0;
        for (JournalEntry entry : AccountingData.journalEntries) {
            if (entry.account.equals(accountName)) {
                runningBalance += entry.debit - entry.credit;
                ledgerTableModel.addRow(new Object[]{
                    entry.date, entry.description,
                    entry.debit > 0 ? entry.debit : "",
                    entry.credit > 0 ? entry.credit : "",
                    runningBalance
                });
            }
        }
    }
// ===================== CLEAR FIELDS IN THE NEW TRANSACTION PANEL ================
    private void ClearFields() {
        DateField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        DescriptionField.setText("");
        AmountField.setText("");
        debitDropdown.setSelectedIndex(0);
        CreditDropdown.setSelectedIndex(0);
    }
// ================== RESET SYSTEM ============================
    private void resetSystem() {
        AccountingData.transactions.clear();
        AccountingData.accounts.clear();
        AccountingData.journalEntries.clear();

        tableModel.setRowCount(0);
        accountsTableModel.setRowCount(0);
        journalTableModel.setRowCount(0);
        ledgerTableModel.setRowCount(0);
        assetModel.setRowCount(0);
        liabilityModel.setRowCount(0);

        initializeDefaultAccounts();
        JOptionPane.showMessageDialog(frame, "System has been reset.");
    }
//================== SET VISIBLE ===============================
    public void show() {
        frame.setVisible(true);
    }
}
