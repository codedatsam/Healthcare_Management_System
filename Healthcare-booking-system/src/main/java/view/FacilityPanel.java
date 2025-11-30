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

public class FacilityPanel extends JPanel {
    private JTable facilityTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;

    public FacilityPanel() {
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        String[] columns = {
            "Facility ID", "Name", "Type", "Address", "Postcode", 
            "Phone", "Email", "Manager", "Capacity"
        };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        facilityTable = new JTable(tableModel);
        facilityTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        refreshButton = new JButton("Refresh");
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(facilityTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public JTable getFacilityTable() { return facilityTable; }
    public JButton getRefreshButton() { return refreshButton; }
    public DefaultTableModel getTableModel() { return tableModel; }
}