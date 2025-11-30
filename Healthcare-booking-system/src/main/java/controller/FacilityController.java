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
import model.Facility;
import view.MainFrame;
import view.FacilityPanel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FacilityController {
    private MainFrame mainFrame;
    private Repository repository;
    private FacilityPanel facilityPanel;

    public FacilityController(MainFrame mainFrame, Repository repository) {
        this.mainFrame = mainFrame;
        this.repository = repository;
        this.facilityPanel = (FacilityPanel) ((JTabbedPane) mainFrame.getContentPane().getComponent(0)).getComponentAt(6);
        
        setupEventHandlers();
        refreshView();
    }

    private void setupEventHandlers() {
        facilityPanel.getRefreshButton().addActionListener(new RefreshHandler());
    }

    public void refreshView() {
        var model = facilityPanel.getTableModel();
        model.setRowCount(0);

        for (Facility facility : repository.getAllFacilities()) {
            model.addRow(new Object[]{
                facility.getFacilityId(),
                facility.getFacilityName(),
                facility.getFacilityType(),
                facility.getAddress(),
                facility.getPostcode(),
                facility.getPhoneNumber(),
                facility.getEmail(),
                facility.getManagerName(),
                facility.getCapacity()
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