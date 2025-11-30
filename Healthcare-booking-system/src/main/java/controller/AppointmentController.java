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
import model.Appointment;
import view.MainFrame;
import view.AppointmentPanel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppointmentController {
    private MainFrame mainFrame;
    private Repository repository;
    private AppointmentPanel appointmentPanel;

    public AppointmentController(MainFrame mainFrame, Repository repository) {
        this.mainFrame = mainFrame;
        this.repository = repository;
        this.appointmentPanel = (AppointmentPanel) ((JTabbedPane) mainFrame.getContentPane().getComponent(0)).getComponentAt(2);
        
        setupEventHandlers();
        refreshView();
    }

    private void setupEventHandlers() {
        appointmentPanel.getAddButton().addActionListener(new AddAppointmentHandler());
        appointmentPanel.getEditButton().addActionListener(new EditAppointmentHandler());
        appointmentPanel.getDeleteButton().addActionListener(new DeleteAppointmentHandler());
        appointmentPanel.getRefreshButton().addActionListener(new RefreshHandler());
    }

    public void refreshView() {
        var model = appointmentPanel.getTableModel();
        model.setRowCount(0);

        for (Appointment appointment : repository.getAllAppointments()) {
            model.addRow(new Object[]{
                appointment.getAppointmentId(),
                appointment.getPatientId(),
                appointment.getClinicianId(),
                appointment.getFacilityId(),
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime(),
                appointment.getDurationMinutes(),
                appointment.getAppointmentType(),
                appointment.getStatus(),
                appointment.getReasonForVisit()
            });
        }
    }

    private class AddAppointmentHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField idField = new JTextField("A" + (repository.getAllAppointments().size() + 1));
            JTextField patientIdField = new JTextField();
            JTextField clinicianIdField = new JTextField();
            JTextField facilityIdField = new JTextField();
            JTextField dateField = new JTextField();
            JTextField timeField = new JTextField();
            JTextField durationField = new JTextField();
            JTextField typeField = new JTextField();
            JTextField statusField = new JTextField("Scheduled");
            JTextArea reasonArea = new JTextArea(3, 20);

            Object[] message = {
                "Appointment ID:", idField,
                "Patient ID:", patientIdField,
                "Clinician ID:", clinicianIdField,
                "Facility ID:", facilityIdField,
                "Date (YYYY-MM-DD):", dateField,
                "Time (HH:MM):", timeField,
                "Duration (minutes):", durationField,
                "Type:", typeField,
                "Status:", statusField,
                "Reason for Visit:", new JScrollPane(reasonArea)
            };

            int option = JOptionPane.showConfirmDialog(
                mainFrame, message, "Add New Appointment", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                Appointment appointment = new Appointment(
                    idField.getText(), patientIdField.getText(), clinicianIdField.getText(),
                    facilityIdField.getText(), dateField.getText(), timeField.getText(),
                    durationField.getText(), typeField.getText(), statusField.getText(),
                    reasonArea.getText(), "", java.time.LocalDate.now().toString(),
                    java.time.LocalDate.now().toString()
                );
                repository.addAppointment(appointment);
                refreshView();
                JOptionPane.showMessageDialog(mainFrame, "Appointment added successfully!");
            }
        }
    }

    private class EditAppointmentHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = appointmentPanel.getAppointmentTable().getSelectedRow();
            if (selectedRow >= 0) {
                String appointmentId = (String) appointmentPanel.getTableModel().getValueAt(selectedRow, 0);
                JOptionPane.showMessageDialog(mainFrame, "Edit functionality for appointment: " + appointmentId);
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Please select an appointment to edit.");
            }
        }
    }

    private class DeleteAppointmentHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = appointmentPanel.getAppointmentTable().getSelectedRow();
            if (selectedRow >= 0) {
                String appointmentId = (String) appointmentPanel.getTableModel().getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(mainFrame,
                    "Are you sure you want to delete appointment " + appointmentId + "?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    repository.deleteAppointment(appointmentId);
                    refreshView();
                    JOptionPane.showMessageDialog(mainFrame, "Appointment deleted successfully!");
                }
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Please select an appointment to delete.");
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