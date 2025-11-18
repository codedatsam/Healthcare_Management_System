/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author lekan
 */


import java.util.Date;

public class Referral {
    private static Referral instance;
    private String referralId;
    private String patientId;
    private String referringClinicianId;
    private String referredToClinicianId;
    private String referringFacilityId;
    private String referredToFacilityId;
    private String referralDate;
    private String urgencyLevel;
    private String referralReason;
    private String clinicalSummary;
    private String requestedInvestigations;
    private String status;
    private String appointmentId;
    private String notes;
    private String createdDate;
    private String lastUpdated;

    private Referral() {
        // Private constructor to prevent instantiation
    }

    public static synchronized Referral getInstance() {
        if (instance == null) {
            instance = new Referral();
        }
        return instance;
    }

    // Method to create a new referral instance with data
    public static Referral createReferral(String referralId, String patientId, String referringClinicianId,
                                        String referredToClinicianId, String referringFacilityId, 
                                        String referredToFacilityId, String referralDate, String urgencyLevel,
                                        String referralReason, String clinicalSummary, String requestedInvestigations,
                                        String status, String appointmentId, String notes, String createdDate, 
                                        String lastUpdated) {
        Referral referral = getInstance();
        referral.referralId = referralId;
        referral.patientId = patientId;
        referral.referringClinicianId = referringClinicianId;
        referral.referredToClinicianId = referredToClinicianId;
        referral.referringFacilityId = referringFacilityId;
        referral.referredToFacilityId = referredToFacilityId;
        referral.referralDate = referralDate;
        referral.urgencyLevel = urgencyLevel;
        referral.referralReason = referralReason;
        referral.clinicalSummary = clinicalSummary;
        referral.requestedInvestigations = requestedInvestigations;
        referral.status = status;
        referral.appointmentId = appointmentId;
        referral.notes = notes;
        referral.createdDate = createdDate;
        referral.lastUpdated = lastUpdated;
        return referral;
    }

    // Getters
    public String getReferralId() { return referralId; }
    public String getPatientId() { return patientId; }
    public String getReferringClinicianId() { return referringClinicianId; }
    public String getReferredToClinicianId() { return referredToClinicianId; }
    public String getReferringFacilityId() { return referringFacilityId; }
    public String getReferredToFacilityId() { return referredToFacilityId; }
    public String getReferralDate() { return referralDate; }
    public String getUrgencyLevel() { return urgencyLevel; }
    public String getReferralReason() { return referralReason; }
    public String getClinicalSummary() { return clinicalSummary; }
    public String getRequestedInvestigations() { return requestedInvestigations; }
    public String getStatus() { return status; }
    public String getAppointmentId() { return appointmentId; }
    public String getNotes() { return notes; }
    public String getCreatedDate() { return createdDate; }
    public String getLastUpdated() { return lastUpdated; }

    public String generateReferralContent() {
        return String.format(
            "REFERRAL RECORD\n" +
            "===============\n" +
            "Referral ID: %s\n" +
            "Patient ID: %s\n" +
            "Referring Clinician: %s\n" +
            "Referred To Clinician: %s\n" +
            "Referring Facility: %s\n" +
            "Referred To Facility: %s\n" +
            "Referral Date: %s\n" +
            "Urgency Level: %s\n" +
            "Reason: %s\n" +
            "Clinical Summary: %s\n" +
            "Requested Investigations: %s\n" +
            "Status: %s\n" +
            "Appointment ID: %s\n" +
            "Notes: %s\n" +
            "Created: %s\n" +
            "Last Updated: %s\n" +
            "--- END OF REFERRAL ---\n\n",
            referralId, patientId, referringClinicianId, referredToClinicianId,
            referringFacilityId, referredToFacilityId, referralDate, urgencyLevel,
            referralReason, clinicalSummary, requestedInvestigations, status,
            appointmentId, notes, createdDate, lastUpdated
        );
    }

    public String generateEmailContent() {
        return String.format(
            "EMAIL NOTIFICATION - NEW REFERRAL\n" +
            "=================================\n" +
            "Subject: New %s Referral - %s\n\n" +
            "Dear Specialist Team,\n\n" +
            "A new %s referral has been received:\n\n" +
            "Patient: %s\n" +
            "Referring GP: %s\n" +
            "Urgency: %s\n" +
            "Clinical Summary: %s\n" +
            "Requested Investigations: %s\n\n" +
            "Please review this referral within the system.\n\n" +
            "Kind regards,\n" +
            "Referral Management System\n\n",
            urgencyLevel, referralId, urgencyLevel.toLowerCase(),
            patientId, referringClinicianId, urgencyLevel,
            clinicalSummary, requestedInvestigations
        );
    }
}
