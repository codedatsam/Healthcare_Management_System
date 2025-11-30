/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author lekan
 */



import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReferralService {
    private static ReferralService instance;
    private List<Referral> referralQueue;
    private SimpleDateFormat dateFormat;
    private int referralCounter;

    private ReferralService() {
        referralQueue = new ArrayList<>();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        referralCounter = 11; // Start from R011
    }

    public static synchronized ReferralService getInstance() {
        if (instance == null) {
            instance = new ReferralService();
        }
        return instance;
    }

    public Referral createReferral(String patientId, String referringClinicianId,
                                 String referredToClinicianId, String referringFacilityId,
                                 String referredToFacilityId, String urgencyLevel,
                                 String referralReason, String clinicalSummary,
                                 String requestedInvestigations) {
        
        Referral referral = Referral.createNewReferral(
            patientId, referringClinicianId, referredToClinicianId,
            referringFacilityId, referredToFacilityId, urgencyLevel,
            referralReason, clinicalSummary, requestedInvestigations
        );
        
        referralQueue.add(referral);
        return referral;
    }

    public void processReferral(Referral referral) {
        try {
            // Generate and save referral document
            String referralContent = referral.generateReferralContent();
            saveToFile(referralContent, "data/referrals_output.txt");
            
            // Generate and save email content
            String emailContent = referral.generateEmailContent();
            saveToFile(emailContent, "data/referral_emails.txt");
            
            // Update audit trail
            updateAuditTrail(referral, "PROCESSED");
            
            System.out.println("Successfully processed referral: " + referral.getReferralId());
            
        } catch (IOException e) {
            System.err.println("Error processing referral: " + e.getMessage());
            throw new RuntimeException("Failed to process referral", e);
        }
    }

    public void generateEmailAndFiles(Referral referral) {
        try {
            // Generate email content
            String emailContent = referral.generateEmailContent();
            saveToFile(emailContent, "data/referral_emails.txt");
            
            // Generate referral document
            String referralContent = referral.generateReferralContent();
            saveToFile(referralContent, "data/referrals_output.txt");
            
            // Update audit trail
            updateAuditTrail(referral, "EMAIL_GENERATED");
            
        } catch (IOException e) {
            System.err.println("Error generating email and files: " + e.getMessage());
            throw new RuntimeException("Failed to generate email and files", e);
        }
    }

    private void saveToFile(String content, String filePath) throws IOException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(content);
            writer.write("\n"); // Add newline after each entry
        }
    }

    private void updateAuditTrail(Referral referral, String action) throws IOException {
        String timestamp = dateFormat.format(new Date());
        String auditEntry = String.format(
            "AUDIT | %s | Referral: %s | Patient: %s | Action: %s\n",
            timestamp, referral.getReferralId(), referral.getPatientId(), action
        );
        saveToFile(auditEntry, "data/audit_trail.txt");
    }

    public List<Referral> getReferralQueue() {
        return new ArrayList<>(referralQueue);
    }

    public void updateReferralStatus(Referral referral, String newStatus) {
        referral.setStatus(newStatus);
        try {
            updateAuditTrail(referral, "STATUS_CHANGED_TO_" + newStatus.toUpperCase());
        } catch (IOException e) {
            System.err.println("Error updating audit trail: " + e.getMessage());
        }
    }

    public String generateReferralReport() {
        StringBuilder report = new StringBuilder();
        report.append("REFERRAL MANAGEMENT SYSTEM - REPORT\n");
        report.append("Generated on: ").append(new Date()).append("\n\n");
        report.append("Total Referrals: ").append(referralQueue.size()).append("\n\n");
        
        report.append(String.format("%-12s %-10s %-15s %-10s %-12s %-50s\n",
            "Referral ID", "Patient ID", "Urgency", "Status", "Date", "Reason"));
        report.append("-".repeat(120)).append("\n");
        
        for (Referral referral : referralQueue) {
            String truncatedReason = referral.getReferralReason().length() > 45 ? 
                referral.getReferralReason().substring(0, 45) + "..." : 
                referral.getReferralReason();
                
            report.append(String.format("%-12s %-10s %-15s %-10s %-12s %-50s\n",
                referral.getReferralId(),
                referral.getPatientId(),
                referral.getUrgencyLevel(),
                referral.getStatus(),
                referral.getReferralDate(),
                truncatedReason));
        }
        
        return report.toString();
    }

public void generatePrescriptionFile(Prescription prescription) throws IOException {
    String prescriptionContent = generatePrescriptionContent(prescription);
    saveToFile(prescriptionContent, "data/prescriptions_output.txt");
    
    // Also update audit trail
    updatePrescriptionAuditTrail(prescription, "PRESCRIPTION_GENERATED");
}

private String generatePrescriptionContent(Prescription prescription) {
    return String.format(
        "PRESCRIPTION RECORD\n" +
        "===================\n" +
        "Prescription ID: %s\n" +
        "Patient ID: %s\n" +
        "Clinician ID: %s\n" +
        "Appointment ID: %s\n" +
        "Prescription Date: %s\n" +
        "Medication: %s\n" +
        "Dosage: %s\n" +
        "Frequency: %s\n" +
        "Duration: %s days\n" +
        "Quantity: %s\n" +
        "Instructions: %s\n" +
        "Pharmacy: %s\n" +
        "Status: %s\n" +
        "Issue Date: %s\n" +
        "Collection Date: %s\n" +
        "--- END OF PRESCRIPTION ---\n\n",
        prescription.getPrescriptionId(),
        prescription.getPatientId(),
        prescription.getClinicianId(),
        prescription.getAppointmentId() != null ? prescription.getAppointmentId() : "N/A",
        prescription.getPrescriptionDate(),
        prescription.getMedicationName(),
        prescription.getDosage(),
        prescription.getFrequency(),
        prescription.getDurationDays(),
        prescription.getQuantity(),
        prescription.getInstructions(),
        prescription.getPharmacyName(),
        prescription.getStatus(),
        prescription.getIssueDate() != null ? prescription.getIssueDate() : "N/A",
        prescription.getCollectionDate() != null ? prescription.getCollectionDate() : "Not collected"
    );
}

private void updatePrescriptionAuditTrail(Prescription prescription, String action) throws IOException {
    String timestamp = dateFormat.format(new Date());
    String auditEntry = String.format(
        "AUDIT | %s | Prescription: %s | Patient: %s | Medication: %s | Action: %s\n",
        timestamp, prescription.getPrescriptionId(), prescription.getPatientId(),
        prescription.getMedicationName(), action
    );
    saveToFile(auditEntry, "data/prescription_audit_trail.txt");
}

// Optional: Method to generate pharmacy instruction file
public void generatePharmacyInstructions(Prescription prescription) throws IOException {
    String pharmacyContent = String.format(
        "PHARMACY DISPENSING INSTRUCTIONS\n" +
        "===============================\n" +
        "Prescription ID: %s\n" +
        "Patient: %s\n" +
        "Medication: %s %s\n" +
        "Directions: %s, %s for %s days\n" +
        "Quantity: %s\n" +
        "Special Instructions: %s\n" +
        "Prescribing Clinician: %s\n" +
        "Date Issued: %s\n" +
        "Status: %s\n" +
        "Pharmacy: %s\n\n",
        prescription.getPrescriptionId(),
        prescription.getPatientId(),
        prescription.getMedicationName(),
        prescription.getDosage(),
        prescription.getFrequency(),
        prescription.getDosage(),
        prescription.getDurationDays(),
        prescription.getQuantity(),
        prescription.getInstructions(),
        prescription.getClinicianId(),
        prescription.getIssueDate() != null ? prescription.getIssueDate() : prescription.getPrescriptionDate(),
        prescription.getStatus(),
        prescription.getPharmacyName()
    );
    
    saveToFile(pharmacyContent, "data/pharmacy_instructions.txt");
}

// Optional: Method to generate patient prescription copy
public void generatePatientCopy(Prescription prescription) throws IOException {
    String patientCopy = String.format(
        "PATIENT PRESCRIPTION COPY\n" +
        "========================\n" +
        "Dear Patient,\n\n" +
        "Here are your prescription details:\n\n" +
        "Medication: %s %s\n" +
        "How to take: %s, %s\n" +
        "Duration: %s days\n" +
        "Total quantity: %s\n" +
        "Important instructions: %s\n\n" +
        "Prescribed by: %s\n" +
        "Date: %s\n" +
        "Pharmacy: %s\n\n" +
        "Please take as directed and contact your doctor if you have any concerns.\n\n",
        prescription.getMedicationName(),
        prescription.getDosage(),
        prescription.getFrequency(),
        prescription.getDosage(),
        prescription.getDurationDays(),
        prescription.getQuantity(),
        prescription.getInstructions(),
        prescription.getClinicianId(),
        prescription.getPrescriptionDate(),
        prescription.getPharmacyName()
    );
    
    saveToFile(patientCopy, "data/patient_prescription_copies.txt");
}
}