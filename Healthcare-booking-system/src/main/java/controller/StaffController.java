/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author lekan
 */


import model.Repository;
import model.Staff;
import view.MainFrame;
import view.StaffPanel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StaffController {
    private MainFrame mainFrame;
    private Repository repository;
    private StaffPanel staffPanel;

    public StaffController(MainFrame mainFrame, Repository repository) {
        this.mainFrame = mainFrame;
        this.repository = repository;
        this.staffPanel = (StaffPanel) ((JTabbedPane) mainFrame.getContentPane().getComponent(0)).getComponentAt(5);
        
        setupEventHandlers();
        refreshView();
    }

    private void setupEventHandlers() {
        staffPanel.getRefreshButton().addActionListener(new RefreshHandler());
    }

    public void refreshView() {
        var model = staffPanel.getTableModel();
        model.setRowCount(0);

        for (Staff staff : repository.getAllStaff()) {
            model.addRow(new Object[]{
                staff.getStaffId(),
                staff.getFirstName(),
                staff.getLastName(),
                staff.getRole(),
                staff.getDepartment(),
                staff.getFacilityId(),
                staff.getPhoneNumber(),
                staff.getEmail(),
                staff.getEmploymentStatus(),
                staff.getStartDate()
            });
        }
    }

    private class RefreshHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refreshView();
        }
    }
}
