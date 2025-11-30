/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author lekan
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReferralPanel extends JPanel {
    private JTable referralTable;
    private DefaultTableModel tableModel;
    private JButton createButton, editButton, deleteButton, processButton;
    private JButton generateEmailButton, refreshButton, viewDetailsButton;
    private JPanel buttonPanel;

    public ReferralPanel() {
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        // Create table model with proper columns
        String[] columns = {
            "Referral ID", "Patient ID", "Referring Clinician", 
            "Referred To", "Urgency", "Status", "Date", "Reason"
        };
        
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class; // All columns contain strings
            }
        };
        
        // Create table
        referralTable = new JTable(tableModel);
        referralTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        referralTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        referralTable.getTableHeader().setReorderingAllowed(false);
        
        // Set column widths
        referralTable.getColumnModel().getColumn(0).setPreferredWidth(80);  // Referral ID
        referralTable.getColumnModel().getColumn(1).setPreferredWidth(80);  // Patient ID
        referralTable.getColumnModel().getColumn(2).setPreferredWidth(120); // Referring Clinician
        referralTable.getColumnModel().getColumn(3).setPreferredWidth(120); // Referred To
        referralTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Urgency
        referralTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Status
        referralTable.getColumnModel().getColumn(6).setPreferredWidth(100); // Date
        referralTable.getColumnModel().getColumn(7).setPreferredWidth(200); // Reason
        
        // Create buttons
        createButton = new JButton("Create New Referral");
        editButton = new JButton("Edit Referral");
        deleteButton = new JButton("Delete Referral");
        processButton = new JButton("Process Referral");
        generateEmailButton = new JButton("Generate Email & Files");
        refreshButton = new JButton("Refresh");
        viewDetailsButton = new JButton("View Details");
        
        // Set tooltips
        createButton.setToolTipText("Create a new referral");
        editButton.setToolTipText("Edit selected referral");
        deleteButton.setToolTipText("Delete selected referral");
        processButton.setToolTipText("Process selected referral");
        generateEmailButton.setToolTipText("Generate email content and save to files");
        refreshButton.setToolTipText("Refresh the table data");
        viewDetailsButton.setToolTipText("View detailed information about selected referral");
    }

    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create main panel with table
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        
        // Table in scroll pane
        JScrollPane scrollPane = new JScrollPane(referralTable);
        scrollPane.setPreferredSize(new Dimension(1200, 400));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Referrals"));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Create button panel with two rows
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 4, 10, 10));
        
        // First row of buttons
        buttonPanel.add(createButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(processButton);
        
        // Second row of buttons
        buttonPanel.add(generateEmailButton);
        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(new JLabel()); // Empty space
        
        // Add button panel to main panel
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel, BorderLayout.CENTER);
    }

    // Getters for buttons and table
    public JButton getCreateButton() { return createButton; }
    public JButton getEditButton() { return editButton; }
    public JButton getDeleteButton() { return deleteButton; }
    public JButton getProcessButton() { return processButton; }
    public JButton getGenerateEmailButton() { return generateEmailButton; }
    public JButton getRefreshButton() { return refreshButton; }
    public JButton getViewDetailsButton() { return viewDetailsButton; }
    public JTable getReferralTable() { return referralTable; }
    public DefaultTableModel getTableModel() { return tableModel; }
    
    // Method to clear table data
    public void clearTable() {
        tableModel.setRowCount(0);
    }
    
    // Method to add a row to the table
    public void addTableRow(Object[] rowData) {
        tableModel.addRow(rowData);
    }
}