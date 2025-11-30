/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author lekan
 */

import java.awt.GridLayout;
import model.Repository;
import model.Referral;
import model.ReferralService;
import view.MainFrame;
import view.ReferralPanel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReferralController {
    private MainFrame mainFrame;
    private Repository repository;
    private ReferralPanel referralPanel;
    private ReferralService referralService;

    public ReferralController(MainFrame mainFrame, Repository repository) {
        this.mainFrame = mainFrame;
        this.repository = repository;
        this.referralService = ReferralService.getInstance();
        this.referralPanel = getReferralPanel();
        
        if (referralPanel != null) {
            setupEventHandlers();
            refreshView();
        } else {
            System.err.println("ReferralPanel not found!");
        }
    }

    private ReferralPanel getReferralPanel() {
        try {
            JTabbedPane tabbedPane = (JTabbedPane) mainFrame.getContentPane().getComponent(0);
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                if ("Referrals".equals(tabbedPane.getTitleAt(i))) {
                    return (ReferralPanel) tabbedPane.getComponentAt(i);
                }
            }
        } catch (Exception e) {
            System.err.println("Error accessing ReferralPanel: " + e.getMessage());
        }
        return null;
    }

    private void setupEventHandlers() {
        referralPanel.getCreateButton().addActionListener(new CreateReferralHandler());
        referralPanel.getEditButton().addActionListener(new EditReferralHandler());
        referralPanel.getDeleteButton().addActionListener(new DeleteReferralHandler());
        referralPanel.getProcessButton().addActionListener(new ProcessReferralHandler());
        referralPanel.getGenerateEmailButton().addActionListener(new GenerateEmailHandler());
        referralPanel.getRefreshButton().addActionListener(new RefreshHandler());
        referralPanel.getViewDetailsButton().addActionListener(new ViewDetailsHandler());
    }

    public void refreshView() {
        if (referralPanel == null) return;
        
        referralPanel.clearTable();
        
        // Load data from both repository and service
        int totalReferrals = 0;
        
        // Add referrals from repository (loaded from CSV)
        for (Referral referral : repository.getAllReferrals()) {
            addReferralToTable(referral);
            totalReferrals++;
        }
        
        // Add referrals from service (newly created)
        for (Referral referral : referralService.getReferralQueue()) {
            // Check if not already added from repository
            if (!isReferralInTable(referral.getReferralId())) {
                addReferralToTable(referral);
                totalReferrals++;
            }
        }
        
        System.out.println("Loaded " + totalReferrals + " referrals into table");
        referralPanel.getReferralTable().repaint();
    }

    private void addReferralToTable(Referral referral) {
        String truncatedReason = referral.getReferralReason();
        if (truncatedReason.length() > 50) {
            truncatedReason = truncatedReason.substring(0, 50) + "...";
        }
        
        referralPanel.addTableRow(new Object[]{
            referral.getReferralId(),
            referral.getPatientId(),
            referral.getReferringClinicianId(),
            referral.getReferredToClinicianId(),
            referral.getUrgencyLevel(),
            referral.getStatus(),
            referral.getReferralDate(),
            truncatedReason
        });
    }

    private boolean isReferralInTable(String referralId) {
        for (int i = 0; i < referralPanel.getTableModel().getRowCount(); i++) {
            String tableReferralId = (String) referralPanel.getTableModel().getValueAt(i, 0);
            if (referralId.equals(tableReferralId)) {
                return true;
            }
        }
        return false;
    }

    private Referral getSelectedReferral() {
        int selectedRow = referralPanel.getReferralTable().getSelectedRow();
        if (selectedRow >= 0) {
            String referralId = (String) referralPanel.getTableModel().getValueAt(selectedRow, 0);
            
            // Search in repository
            for (Referral referral : repository.getAllReferrals()) {
                if (referral.getReferralId().equals(referralId)) {
                    return referral;
                }
            }
            
            // Search in service queue
            for (Referral referral : referralService.getReferralQueue()) {
                if (referral.getReferralId().equals(referralId)) {
                    return referral;
                }
            }
        }
        return null;
    }

    // Button Handlers
    private class CreateReferralHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showCreateReferralDialog();
        }
    }

    private class EditReferralHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Referral referral = getSelectedReferral();
            if (referral != null) {
                showEditReferralDialog(referral);
            } else {
                showNoSelectionMessage("edit");
            }
        }
    }

    private class DeleteReferralHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Referral referral = getSelectedReferral();
            if (referral != null) {
                int confirm = JOptionPane.showConfirmDialog(
                    mainFrame,
                    "Are you sure you want to delete referral " + referral.getReferralId() + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (confirm == JOptionPane.YES_OPTION) {
                    // Remove from service queue
                    referralService.getReferralQueue().remove(referral);
                    refreshView();
                    JOptionPane.showMessageDialog(mainFrame, "Referral deleted successfully!");
                }
            } else {
                showNoSelectionMessage("delete");
            }
        }
    }

    private class ProcessReferralHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Referral referral = getSelectedReferral();
            if (referral != null) {
                String[] statusOptions = {"New", "In Progress", "Completed", "Cancelled"};
                String newStatus = (String) JOptionPane.showInputDialog(
                    mainFrame,
                    "Select new status for referral " + referral.getReferralId() + ":",
                    "Process Referral",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    statusOptions,
                    referral.getStatus()
                );
                
                if (newStatus != null && !newStatus.equals(referral.getStatus())) {
                    referralService.updateReferralStatus(referral, newStatus);
                    refreshView();
                    JOptionPane.showMessageDialog(
                        mainFrame,
                        "Referral " + referral.getReferralId() + " status updated to: " + newStatus
                    );
                }
            } else {
                showNoSelectionMessage("process");
            }
        }
    }

    private class GenerateEmailHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Referral referral = getSelectedReferral();
            if (referral != null) {
                try {
                    referralService.generateEmailAndFiles(referral);
                    
                    // Show success message with preview
                    String emailContent = referral.generateEmailContent();
                    JTextArea textArea = new JTextArea(emailContent, 15, 50);
                    textArea.setEditable(false);
                    textArea.setCaretPosition(0);
                    
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    
                    JOptionPane.showMessageDialog(
                        mainFrame,
                        scrollPane,
                        "Email Generated Successfully - " + referral.getReferralId(),
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                        mainFrame,
                        "Error generating email: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                showNoSelectionMessage("generate email for");
            }
        }
    }

    private class RefreshHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refreshView();
            JOptionPane.showMessageDialog(mainFrame, "Referral data refreshed!");
        }
    }

    private class ViewDetailsHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Referral referral = getSelectedReferral();
            if (referral != null) {
                showReferralDetails(referral);
            } else {
                showNoSelectionMessage("view details for");
            }
        }
    }

    // Dialog Methods
    private void showCreateReferralDialog() {
        JTextField patientIdField = new JTextField();
        JTextField referringClinicianField = new JTextField();
        JTextField referredToClinicianField = new JTextField();
        JComboBox<String> urgencyCombo = new JComboBox<>(new String[]{"Routine", "Urgent", "Non-urgent"});
        JTextArea reasonArea = new JTextArea(3, 30);
        JTextArea summaryArea = new JTextArea(5, 30);
        JTextField investigationsField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("Patient ID:"));
        panel.add(patientIdField);
        panel.add(new JLabel("Referring Clinician ID:"));
        panel.add(referringClinicianField);
        panel.add(new JLabel("Referred To Clinician ID:"));
        panel.add(referredToClinicianField);
        panel.add(new JLabel("Urgency Level:"));
        panel.add(urgencyCombo);
        panel.add(new JLabel("Referral Reason:"));
        panel.add(new JScrollPane(reasonArea));
        panel.add(new JLabel("Clinical Summary:"));
        panel.add(new JScrollPane(summaryArea));
        panel.add(new JLabel("Requested Investigations:"));
        panel.add(investigationsField);

        int result = JOptionPane.showConfirmDialog(
            mainFrame, panel, "Create New Referral", JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                Referral newReferral = referralService.createReferral(
                    patientIdField.getText(),
                    referringClinicianField.getText(),
                    referredToClinicianField.getText(),
                    "S001", // Default referring facility
                    "H001", // Default referred to facility
                    (String) urgencyCombo.getSelectedItem(),
                    reasonArea.getText(),
                    summaryArea.getText(),
                    investigationsField.getText()
                );

                // Process the referral (generate files)
                referralService.processReferral(newReferral);
                refreshView();
                
                JOptionPane.showMessageDialog(
                    mainFrame,
                    "Referral created successfully!\nReferral ID: " + newReferral.getReferralId(),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    mainFrame,
                    "Error creating referral: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void showEditReferralDialog(Referral referral) {
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"New", "In Progress", "Completed", "Cancelled"});
        statusCombo.setSelectedItem(referral.getStatus());
        
        JTextArea notesArea = new JTextArea(referral.getNotes(), 3, 30);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("Referral ID:"));
        panel.add(new JLabel(referral.getReferralId()));
        panel.add(new JLabel("Patient ID:"));
        panel.add(new JLabel(referral.getPatientId()));
        panel.add(new JLabel("Status:"));
        panel.add(statusCombo);
        panel.add(new JLabel("Notes:"));
        panel.add(new JScrollPane(notesArea));

        int result = JOptionPane.showConfirmDialog(
            mainFrame, panel, "Edit Referral - " + referral.getReferralId(), JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            referral.setStatus((String) statusCombo.getSelectedItem());
            refreshView();
            JOptionPane.showMessageDialog(mainFrame, "Referral updated successfully!");
        }
    }

    private void showReferralDetails(Referral referral) {
        String details = String.format(
            "REFERRAL DETAILS\n\n" +
            "Referral ID: %s\n" +
            "Patient ID: %s\n" +
            "Referring Clinician: %s\n" +
            "Referred To Clinician: %s\n" +
            "Referring Facility: %s\n" +
            "Referred To Facility: %s\n" +
            "Referral Date: %s\n" +
            "Urgency Level: %s\n" +
            "Status: %s\n\n" +
            "REASON:\n%s\n\n" +
            "CLINICAL SUMMARY:\n%s\n\n" +
            "REQUESTED INVESTIGATIONS:\n%s",
            referral.getReferralId(), referral.getPatientId(),
            referral.getReferringClinicianId(), referral.getReferredToClinicianId(),
            referral.getReferringFacilityId(), referral.getReferredToFacilityId(),
            referral.getReferralDate(), referral.getUrgencyLevel(),
            referral.getStatus(), referral.getReferralReason(),
            referral.getClinicalSummary(), referral.getRequestedInvestigations()
        );

        JTextArea textArea = new JTextArea(details, 20, 50);
        textArea.setEditable(false);
        textArea.setCaretPosition(0);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        JOptionPane.showMessageDialog(
            mainFrame,
            scrollPane,
            "Referral Details - " + referral.getReferralId(),
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showNoSelectionMessage(String action) {
        JOptionPane.showMessageDialog(
            mainFrame,
            "Please select a referral to " + action + ".",
            "No Selection",
            JOptionPane.WARNING_MESSAGE
        );
    }
}