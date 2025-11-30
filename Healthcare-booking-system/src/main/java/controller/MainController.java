/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author lekan
 */


import model.*;

import view.MainFrame;
import view.*;
import javax.swing.*;

public class MainController {
    private MainFrame mainFrame;
    private Repository repository;
    private DataService dataService;
    
    private PatientController patientController;
    private ClinicianController clinicianController;
    private AppointmentController appointmentController;
    private PrescriptionController prescriptionController;
    private ReferralController referralController;
    private StaffController staffController;
    private FacilityController facilityController;

    public MainController() {
        initializeComponents();
        setupControllers();
        loadInitialData();
        showApplication();
    }

    private void initializeComponents() {
        repository = new Repository();
        dataService = new DataService(repository);
        mainFrame = new MainFrame();
        
        // Create and add panels
        mainFrame.addPanel("Patients", new PatientPanel());
        mainFrame.addPanel("Clinicians", new ClinicianPanel());
        mainFrame.addPanel("Appointments", new AppointmentPanel());
        mainFrame.addPanel("Prescriptions", new PrescriptionPanel());
        mainFrame.addPanel("Referrals", new ReferralPanel());
        mainFrame.addPanel("Staff", new StaffPanel());
        mainFrame.addPanel("Facilities", new FacilityPanel());
    }

    private void setupControllers() {
        patientController = new PatientController(mainFrame, repository);
        clinicianController = new ClinicianController(mainFrame, repository);
        appointmentController = new AppointmentController(mainFrame, repository);
        prescriptionController = new PrescriptionController(mainFrame, repository);
        referralController = new ReferralController(mainFrame, repository);
        staffController = new StaffController(mainFrame, repository);
        facilityController = new FacilityController(mainFrame, repository);
    }

    private void loadInitialData() {
        dataService.loadAllData();
        refreshAllViews();
    }

    private void refreshAllViews() {
        patientController.refreshView();
        clinicianController.refreshView();
        appointmentController.refreshView();
        prescriptionController.refreshView();
        referralController.refreshView();
        staffController.refreshView();
        facilityController.refreshView();
    }

    public void showApplication() {
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainController();
        });
    }
}
