import java.awt.*;
import java.awt.print.PrinterException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.Flow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.border.Border;

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
        // Calendar ActionListener
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
        // Description
        JLabel DescriptionLabel = new JLabel("Description:");
        DescriptionLabel.setBounds(20, 40, 100, 30);
        NewTransaction.add(DescriptionLabel);
        // Description Field
        DescriptionField = new JTextField();
        DescriptionField.setBounds(140, 42, 1000, 25);
        NewTransaction.add(DescriptionField);
        // Debit 
        JLabel DebitAccLabel = new JLabel("Debit Account:");
        DebitAccLabel.setBounds(20, 70, 100, 30);
        NewTransaction.add(DebitAccLabel);
        // Credit
        JLabel CreditAccLabel = new JLabel("Credit Account:");
        CreditAccLabel.setBounds(20, 100, 100, 30);
        NewTransaction.add(CreditAccLabel);
        // Amount
        JLabel AmountLabel = new JLabel("Amount:");
        AmountLabel.setBounds(20, 130, 100, 30);
        NewTransaction.add(AmountLabel);
        // Amount Textfield
        AmountField = new JTextField();
        AmountField.setBounds(140, 132, 1000, 25);
        NewTransaction.add(AmountField);
        // Accounts for Dropdown List
        String[] accounts = {
            "Choose Account", "Cash (Asset)", "Accounts Receivable (Asset)", "Inventory (Asset)", "Prepaid Expenses (Asset)",
            "Equipment (Asset)", "Accounts Payable (Liability)", "Notes Payable (Liability)", "Owner's Capital (Equity)",
            "Sales Revenue (Income)", "Service Revenue (Income)", "Cost of Goods Sold (Income)", "Rent Expense (Expense)",
            "Salaries Expense (Expense)", "Utilities Expense (Expense)"
        };
        // Debit Dropdown
        debitDropdown = new JComboBox<>(accounts);
        debitDropdown.setBounds(140, 72, 1000, 25);
        NewTransaction.add(debitDropdown);
        // Credit Dropdown
        CreditDropdown = new JComboBox<>(accounts);
        CreditDropdown.setBounds(140, 102, 1000, 25);
        NewTransaction.add(CreditDropdown);
        // ==================== BUTTONS ==================================
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
        JPanel TransactionTab = new JPanel();
        TransactionTab.setLayout(new BoxLayout(TransactionTab, BoxLayout.Y_AXIS));
        // Search bar for Transactions
        JPanel searchPanel = new JPanel();
        JTextField searchField = new JTextField();
        JLabel searchLabel = new JLabel("Search Transactions: ");
        JButton searchBtn = new JButton("Search");
        JButton clearSearch = new JButton("Clear");
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchField.setMaximumSize(new Dimension(1000, 30));
        searchBtn.setMaximumSize(new Dimension(100, 30));
        clearSearch.setMaximumSize(new Dimension(100, 30));
        searchBtn.setFocusable(false);
        clearSearch.setFocusable(false);
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(clearSearch);
        TransactionTab.add(Box.createRigidArea(new Dimension(0, 5)));
        TransactionTab.add(searchPanel);
        TransactionTab.add(Box.createRigidArea(new Dimension(0, 5)));
        searchBtn.addActionListener(e -> searchTransaction(searchField));
        clearSearch.addActionListener(e -> updateTransactionTable());

        //Table Model for Transactions
        JPanel TransactionPanel = new JPanel(new BorderLayout());
        TransactionTab.add(TransactionPanel);

        //Table Model for Transactions
        tableModel = new DefaultTableModel(new Object[]{"Date", "Description", "Debit", "Credit", "Amount"}, 0);
        transactionTable = new JTable(tableModel);
        TransactionPanel.add(new JScrollPane(transactionTable), BorderLayout.CENTER);
        
        // delete button for selected transactions
        JButton btnDeleteTransaction = new JButton("Delete Selected Transaction");
        TransactionPanel.add(btnDeleteTransaction, BorderLayout.SOUTH);
        btnDeleteTransaction.addActionListener(e -> removeTransaction());

        // ======================== ACCOUNTS TAB ========================
        JPanel AccountsTab = new JPanel(new BorderLayout());
        // Accounts Table Model
        accountsTableModel = new DefaultTableModel(new Object[]{"Account", "Type", "Balance"}, 0);
        accountsTable = new JTable(accountsTableModel);
        // Scrollpane for Accounts
        AccountsTab.add(new JScrollPane(accountsTable), BorderLayout.CENTER);
        initializeDefaultAccounts();

        // ======================== GENERAL JOURNAL ========================
        JPanel JournalTab = new JPanel(new BorderLayout());
        // General Journal Table Model
        journalTableModel = new DefaultTableModel(new Object[]{"Date", "Description", "Account", "Debit", "Credit"}, 0);
        journalTable = new JTable(journalTableModel);
        // Scrollpane for Journal
        JournalTab.add(new JScrollPane(journalTable), BorderLayout.CENTER);

        // ======================== GENERAL LEDGER ========================
        JPanel LedgerTab = new JPanel(new BorderLayout());
        // Table for Ledger
        ledgerTableModel = new DefaultTableModel(new Object[]{"Date", "Description", "Debit", "Credit", "Balance"}, 0);
        ledgerTable = new JTable(ledgerTableModel);
        // Dropdown List for Accounts in the Ledger
        ledgerAccountDropdown = new JComboBox<>();
        for (Account acc : AccountingData.accounts) ledgerAccountDropdown.addItem(acc.name);
        ledgerAccountDropdown.addActionListener(e -> updateLedger(ledgerAccountDropdown.getSelectedItem().toString()));

        LedgerTab.add(ledgerAccountDropdown, BorderLayout.NORTH);
        //Scrollpane for ledger
        LedgerTab.add(new JScrollPane(ledgerTable), BorderLayout.CENTER);

        // ======================== BALANCE SHEET ========================
        // Balance Sheet main panel
        JPanel BalanceSheetTab = new JPanel(new BorderLayout());
        
        JLabel sheetHeader = new JLabel("Balance Sheet");
        sheetHeader.setFont(new Font("Arial", Font.BOLD, 22));
        sheetHeader.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        BalanceSheetTab.add(sheetHeader, BorderLayout.NORTH);
        
        // Grid Panel
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        
        // Assets (Left Side)
        JPanel leftPanel = new JPanel(new BorderLayout());
        JLabel leftLabel = new JLabel("Assets");
        leftLabel.setFont(new Font("Arial", Font.BOLD, 18));
        assetModel = new DefaultTableModel(new Object[]{"Asset", "Amount"}, 0);
        assetTable = new JTable(assetModel);
        leftPanel.add(leftLabel, BorderLayout.NORTH);
        leftPanel.add(new JScrollPane(assetTable), BorderLayout.CENTER);
        
        // Liabilities and Equity (Right Side)
        JPanel rightPanel = new JPanel(new BorderLayout());
        JLabel rightLabel = new JLabel("Liabilities & Equity");
        rightLabel.setFont(new Font("Arial", Font.BOLD, 18));
        liabilityModel = new DefaultTableModel(new Object[]{"Liability / Equity", "Amount"}, 0);
        liabilityTable = new JTable(liabilityModel);
        rightPanel.add(rightLabel, BorderLayout.NORTH);
        rightPanel.add(new JScrollPane(liabilityTable), BorderLayout.CENTER);
        
        // Add Right and Left Panels to the Center Panel
        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);
        BalanceSheetTab.add(centerPanel, BorderLayout.CENTER);

        //Refresh Button and Print Button
        JButton btnRefreshBS = new JButton("Refresh");
        JButton btnPrintBS = new JButton("Print");
        JPanel bsButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bsButtons.add(btnRefreshBS);
        bsButtons.add(btnPrintBS);
        BalanceSheetTab.add(bsButtons, BorderLayout.SOUTH);
        
        //Button Actions
        btnRefreshBS.addActionListener(e -> updateBalanceSheet());
        //btnPrintBS.addActionListener(e -> printBalanceSheet());

        // ======================== BUTTON ACTIONS IN TRANSACTION TAB ========================
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

    // ======================== CORE LOGIC ===========================
    // ================= SAVING A NEW TRANSACTION ====================
    private void saveTransaction() {
        try {
            String newDebit = debitDropdown.getSelectedItem().toString();
            String newCredit = CreditDropdown.getSelectedItem().toString();
            
            if (newDebit.contains("(Asset)")) newDebit = newDebit.replace("(Asset)", " ");
            if (newDebit.contains("(Liability)")) newDebit = newDebit.replace("(Liability)", " ");
            if (newDebit.contains("(Equity)")) newDebit = newDebit.replace("(Equity)", " ");
            if (newDebit.contains("(Income)")) newDebit = newDebit.replace("(Income)", " ");
            if (newDebit.contains("(Expense)")) newDebit = newDebit.replace("(Expense)", " ");
            
            if (newCredit.contains("(Asset)")) newCredit = newCredit.replace("(Asset)", " ");
            if (newCredit.contains("(Liability)")) newCredit = newCredit.replace("(Liability)", " ");
            if (newCredit.contains("(Equity)")) newCredit = newCredit.replace("(Equity)", " ");
            if (newCredit.contains("(Income)")) newCredit = newCredit.replace("(Income)", " ");
            if (newCredit.contains("(Expense)")) newCredit = newCredit.replace("(Expense)", " ");
            
            String date = DateField.getText();
            String desc = DescriptionField.getText();
            String debit = newDebit.trim();
            String credit = newCredit.trim();
            double amount = Double.parseDouble(AmountField.getText());
            
            // Shows Message Dialog if user does not choose a type of account in debit or credit
            if (debit.equals("Choose Account") || credit.equals("Choose Account")) {
                JOptionPane.showMessageDialog(frame, "Select valid accounts.");
                return;
            }
            if (debit.equals(credit) || credit.equals(debit)) {
                JOptionPane.showMessageDialog(frame, "Select valid accounts.");
                return;
            }
            // Transfer values to transaction object and array
            Transaction transaction = new Transaction(date, desc, debit, credit, amount);
            AccountingData.transactions.add(transaction);
            // adding the values of the new transaction to the table model of the transactions tab
            tableModel.addRow(new Object[]{date, desc, debit, credit, amount});
            // adding the values of the new transaction to the table model of the general journal tab
            // debit
            journalTableModel.addRow(new Object[]{date, desc, debit, amount, ""});
            // credit
            journalTableModel.addRow(new Object[]{date, desc, credit, "", amount});
            // adding the values to AccountingData class journalEntries arraylist
            AccountingData.journalEntries.add(new JournalEntry(date, desc, debit, amount, 0));
            AccountingData.journalEntries.add(new JournalEntry(date, desc, credit, 0, amount));
            // using the updateAccountBalance() method to update the data in the Accounts Tab
            updateAccountBalance(debit, amount, true); // this is for debit
            updateAccountBalance(credit, amount, false); // this is for credit
            refreshAccountsTable();
            // update each item of the ledger 
            updateLedger(ledgerAccountDropdown.getSelectedItem().toString());
            // update the balance sheet
            updateBalanceSheet();
            // Pop up message after saving a new transaction
            JOptionPane.showMessageDialog(frame, "Transaction saved.");
            // Clears fields in the new transactions tab after succesfully saving a new transaction
            ClearFields();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }
// ============== SEARCH FUNCTION SA TRANSACTIONS TAB ==============
private void searchTransaction(JTextField searchField) {
    String search = searchField.getText().trim().toLowerCase().toString();
    tableModel.setRowCount(0);
    for (Transaction acc: AccountingData.transactions) {
        if (acc.date.toLowerCase().contains(search) || acc.description.toLowerCase().contains(search) || acc.debitAccount.toLowerCase().contains(search) || acc.creditAccount.toLowerCase().contains(search)) {
            tableModel.addRow(new Object[]{acc.date, acc.description, acc.debitAccount, acc.creditAccount, acc.amount});
        }

    }
}

// ============== UPDATE TRANSACTIONS TABLE ==================
private void updateTransactionTable() {
    tableModel.setRowCount(0);
    for (Transaction acc: AccountingData.transactions) {
        tableModel.addRow(new Object[]{acc.date, acc.description, acc.debitAccount, acc.creditAccount, acc.amount});
    }
}
// ============== REMOVE TRANSACTIONS, USES THE RECALCULATE METHOD TO UPDATE ACCOUNT BALANCE ==================
    private void removeTransaction() {
        int selectedRow = transactionTable.getSelectedRow();
        // pop up message if haven't selected any transaction to delete
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Select a transaction to delete.");
            return;
        }
        // getting the string value of the date and description of the selected transaction, para i compare sa data nga naa sa AccountingData class
        String date = tableModel.getValueAt(selectedRow, 0).toString();
        String desc = tableModel.getValueAt(selectedRow, 1).toString();
        // Removing the data in the transactions array and the journalEntries array of the AccountingData object that is similar to the values of the selected transaction
        AccountingData.transactions.removeIf(t -> t.date.equals(date) && t.description.equals(desc));
        AccountingData.journalEntries.removeIf(j -> j.date.equals(date) && j.description.equals(desc));
        // Removing the row of the selected transactions in the transaction tab's table model
        tableModel.removeRow(selectedRow);

        recalculateAccountBalances();
        refreshAccountsTable();
        updateLedger(ledgerAccountDropdown.getSelectedItem().toString());
        updateBalanceSheet();

        JOptionPane.showMessageDialog(frame, "Transaction removed.");
    }
// ============ WAPA NI NAHUMAN INGON SI BRYLLE ===================
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
            {"Owner's Capital", "Equity"}, {"Sales Revenue", "Income"},
            {"Service Revenue", "Income"}, {"Cost of Goods Sold", "Expense"}, 
            {"Rent Expense", "Expense"}, {"Salaries Expense", "Expense"}, 
            {"Utilities Expense", "Expense"}
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
                else if ((acc.type.equals("Asset") || acc.type.equals("Expense")) && !isDebit)
                    acc.balance -= amount;
                else if ((acc.type.equals("Liability") || acc.type.equals("Equity")) && isDebit)
                    acc.balance -= amount;
            }
        }
    }
// ================ REFRESH ACCOUNTS TABLE ======================
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
