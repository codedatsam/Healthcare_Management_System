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
import model.Patient;
import view.MainFrame;
import view.PatientPanel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientController {
    private MainFrame mainFrame;
    private Repository repository;
    private PatientPanel patientPanel;

    public PatientController(MainFrame mainFrame, Repository repository) {
        this.mainFrame = mainFrame;
        this.repository = repository;
        this.patientPanel = (PatientPanel) ((JTabbedPane) mainFrame.getContentPane().getComponent(0)).getComponentAt(0);
        
        setupEventHandlers();
        refreshView();
    }

    private void setupEventHandlers() {
        patientPanel.getAddButton().addActionListener(new AddPatientHandler());
        patientPanel.getEditButton().addActionListener(new EditPatientHandler());
        patientPanel.getDeleteButton().addActionListener(new DeletePatientHandler());
        patientPanel.getRefreshButton().addActionListener(new RefreshHandler());
    }

    public void refreshView() {
        var model = patientPanel.getTableModel();
        model.setRowCount(0);

        for (Patient patient : repository.getAllPatients()) {
            model.addRow(new Object[]{
                patient.getPatientId(),
                patient.getNhsNumber(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getDateOfBirth(),
                patient.getPhoneNumber(),
                patient.getEmail(),
                patient.getGpSurgeryId()
            });
        }
    }

    private class AddPatientHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField idField = new JTextField();
            JTextField nhsField = new JTextField();
            JTextField firstNameField = new JTextField();
            JTextField lastNameField = new JTextField();
            JTextField dobField = new JTextField();
            JTextField genderField = new JTextField();
            JTextField phoneField = new JTextField();
            JTextField emailField = new JTextField();
            JTextField addressField = new JTextField();
            JTextField postcodeField = new JTextField();
            JTextField gpField = new JTextField();

            Object[] message = {
                "Patient ID:", idField,
                "NHS Number:", nhsField,
                "First Name:", firstNameField,
                "Last Name:", lastNameField,
                "Date of Birth (YYYY-MM-DD):", dobField,
                "Gender:", genderField,
                "Phone:", phoneField,
                "Email:", emailField,
                "Address:", addressField,
                "Postcode:", postcodeField,
                "GP Surgery ID:", gpField
            };

            int option = JOptionPane.showConfirmDialog(
                mainFrame, message, "Add New Patient", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                Patient patient = new Patient(
                    idField.getText(), firstNameField.getText(), lastNameField.getText(),
                    dobField.getText(), nhsField.getText(), 
                    phoneField.getText(), emailField.getText(), addressField.getText(),
                    gpField.getText()
                );
                repository.addPatient(patient);
                refreshView();
                JOptionPane.showMessageDialog(mainFrame, "Patient added successfully!");
            }
        }
    }

    private class EditPatientHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = patientPanel.getPatientTable().getSelectedRow();
            if (selectedRow >= 0) {
                String patientId = (String) patientPanel.getTableModel().getValueAt(selectedRow, 0);
                Patient patient = repository.getPatientById(patientId);
                if (patient != null) {
                    // Similar to add but with existing data
                    JOptionPane.showMessageDialog(mainFrame, "Edit functionality to be implemented");
                }
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Please select a patient to edit.");
            }
        }
    }

    private class DeletePatientHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = patientPanel.getPatientTable().getSelectedRow();
            if (selectedRow >= 0) {
                String patientId = (String) patientPanel.getTableModel().getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(mainFrame,
                    "Are you sure you want to delete patient " + patientId + "?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    repository.deletePatient(patientId);
                    refreshView();
                    JOptionPane.showMessageDialog(mainFrame, "Patient deleted successfully!");
                }
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Please select a patient to delete.");
            }
        }
    }

    private class RefreshHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refreshView();
        }
    }
}