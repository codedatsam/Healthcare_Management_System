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
import model.Prescription;
import model.ReferralService;
import view.MainFrame;
import view.PrescriptionPanel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrescriptionController {
    private MainFrame mainFrame;
    private Repository repository;
    private PrescriptionPanel prescriptionPanel;
    private ReferralService referralService;

    public PrescriptionController(MainFrame mainFrame, Repository repository) {
        this.mainFrame = mainFrame;
        this.repository = repository;
        this.referralService = ReferralService.getInstance();
        this.prescriptionPanel = (PrescriptionPanel) ((JTabbedPane) mainFrame.getContentPane().getComponent(0)).getComponentAt(3);
        
        setupEventHandlers();
        refreshView();
    }

    private void setupEventHandlers() {
        prescriptionPanel.getAddButton().addActionListener(new AddPrescriptionHandler());
        prescriptionPanel.getEditButton().addActionListener(new EditPrescriptionHandler());
        prescriptionPanel.getDeleteButton().addActionListener(new DeletePrescriptionHandler());
        prescriptionPanel.getRefreshButton().addActionListener(new RefreshHandler());
        prescriptionPanel.getGenerateButton().addActionListener(new GenerateOutputHandler());
    }

    public void refreshView() {
        var model = prescriptionPanel.getTableModel();
        model.setRowCount(0);

        for (Prescription prescription : repository.getAllPrescriptions()) {
            model.addRow(new Object[]{
                prescription.getPrescriptionId(),
                prescription.getPatientId(),
                prescription.getClinicianId(),
                prescription.getMedicationName(),
                prescription.getDosage(),
                prescription.getFrequency(),
                prescription.getDurationDays(),
                prescription.getStatus(),
                prescription.getPharmacyName()
            });
        }
    }

    private class AddPrescriptionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField idField = new JTextField("RX" + (repository.getAllPrescriptions().size() + 1));
            JTextField patientIdField = new JTextField();
            JTextField clinicianIdField = new JTextField();
            JTextField medicationField = new JTextField();
            JTextField dosageField = new JTextField();
            JTextField frequencyField = new JTextField();
            JTextField durationField = new JTextField();
            JTextField pharmacyField = new JTextField();
            JTextArea instructionsArea = new JTextArea(3, 20);

            Object[] message = {
                "Prescription ID:", idField,
                "Patient ID:", patientIdField,
                "Clinician ID:", clinicianIdField,
                "Medication Name:", medicationField,
                "Dosage:", dosageField,
                "Frequency:", frequencyField,
                "Duration (days):", durationField,
                "Pharmacy Name:", pharmacyField,
                "Instructions:", new JScrollPane(instructionsArea)
            };

            int option = JOptionPane.showConfirmDialog(
                mainFrame, message, "Add New Prescription", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                Prescription prescription = new Prescription(
                    idField.getText(), patientIdField.getText(), clinicianIdField.getText(),
                    "", java.time.LocalDate.now().toString(), medicationField.getText(),
                    dosageField.getText(), frequencyField.getText(), durationField.getText(),
                    "", instructionsArea.getText(), pharmacyField.getText(), "Issued",
                    java.time.LocalDate.now().toString(), ""
                );
                repository.addPrescription(prescription);
                refreshView();
                JOptionPane.showMessageDialog(mainFrame, "Prescription added successfully!");
            }
        }
    }

    private class EditPrescriptionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = prescriptionPanel.getPrescriptionTable().getSelectedRow();
            if (selectedRow >= 0) {
                String prescriptionId = (String) prescriptionPanel.getTableModel().getValueAt(selectedRow, 0);
                JOptionPane.showMessageDialog(mainFrame, "Edit functionality for prescription: " + prescriptionId);
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Please select a prescription to edit.");
            }
        }
    }

    private class DeletePrescriptionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = prescriptionPanel.getPrescriptionTable().getSelectedRow();
            if (selectedRow >= 0) {
                String prescriptionId = (String) prescriptionPanel.getTableModel().getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(mainFrame,
                    "Are you sure you want to delete prescription " + prescriptionId + "?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    repository.deletePrescription(prescriptionId);
                    refreshView();
                    JOptionPane.showMessageDialog(mainFrame, "Prescription deleted successfully!");
                }
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Please select a prescription to delete.");
            }
        }
    }

    private class RefreshHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refreshView();
        }
    }

    private class GenerateOutputHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                for (Prescription prescription : repository.getAllPrescriptions()) {
                    referralService.generatePrescriptionFile(prescription);
                }
                JOptionPane.showMessageDialog(mainFrame, 
                    "Prescription output files generated successfully in data/prescriptions_output.txt");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, 
                    "Error generating prescription files: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
