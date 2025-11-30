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

public class ClinicianPanel extends JPanel {
    private JTable clinicianTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;

    public ClinicianPanel() {
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        String[] columns = {
            "Clinician ID", "First Name", "Last Name", "Title", "Speciality", 
            "GMC Number", "Phone", "Email", "Workplace", "Status"
        };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        clinicianTable = new JTable(tableModel);
        clinicianTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        refreshButton = new JButton("Refresh");
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(clinicianTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public JTable getClinicianTable() { return clinicianTable; }
    public JButton getRefreshButton() { return refreshButton; }
    public DefaultTableModel getTableModel() { return tableModel; }
}
