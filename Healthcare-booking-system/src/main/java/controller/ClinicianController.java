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
import model.Clinician;
import view.MainFrame;
import view.ClinicianPanel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClinicianController {
    private MainFrame mainFrame;
    private Repository repository;
    private ClinicianPanel clinicianPanel;

    public ClinicianController(MainFrame mainFrame, Repository repository) {
        this.mainFrame = mainFrame;
        this.repository = repository;
        this.clinicianPanel = (ClinicianPanel) ((JTabbedPane) mainFrame.getContentPane().getComponent(0)).getComponentAt(1);
        
        setupEventHandlers();
        refreshView();
    }

    private void setupEventHandlers() {
        clinicianPanel.getRefreshButton().addActionListener(new RefreshHandler());
    }

    public void refreshView() {
        var model = clinicianPanel.getTableModel();
        model.setRowCount(0);

        for (Clinician clinician : repository.getAllClinicians()) {
            model.addRow(new Object[]{
                clinician.getClinicianId(),
                clinician.getFirstName(),
                clinician.getLastName(),
                clinician.getTitle(),
                clinician.getSpeciality(),
                clinician.getGmcNumber(),
                clinician.getPhoneNumber(),
                clinician.getEmail(),
                clinician.getWorkplaceId(),
                clinician.getEmploymentStatus()
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
