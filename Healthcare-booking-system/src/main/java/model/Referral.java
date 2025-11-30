/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author lekan
 */


public class Referral {
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

    // Default constructor
    public Referral() {}

    // Full constructor
    public Referral(String referralId, String patientId, String referringClinicianId,
                   String referredToClinicianId, String referringFacilityId, 
                   String referredToFacilityId, String referralDate, String urgencyLevel,
                   String referralReason, String clinicalSummary, String requestedInvestigations,
                   String status, String appointmentId, String notes, String createdDate, 
                   String lastUpdated) {
        this.referralId = referralId;
        this.patientId = patientId;
        this.referringClinicianId = referringClinicianId;
        this.referredToClinicianId = referredToClinicianId;
        this.referringFacilityId = referringFacilityId;
        this.referredToFacilityId = referredToFacilityId;
        this.referralDate = referralDate;
        this.urgencyLevel = urgencyLevel;
        this.referralReason = referralReason;
        this.clinicalSummary = clinicalSummary;
        this.requestedInvestigations = requestedInvestigations;
        this.status = status;
        this.appointmentId = appointmentId;
        this.notes = notes;
        this.createdDate = createdDate;
        this.lastUpdated = lastUpdated;
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

    // Setters
    public void setReferralId(String referralId) { this.referralId = referralId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
    public void setReferringClinicianId(String referringClinicianId) { this.referringClinicianId = referringClinicianId; }
    public void setReferredToClinicianId(String referredToClinicianId) { this.referredToClinicianId = referredToClinicianId; }
    public void setReferringFacilityId(String referringFacilityId) { this.referringFacilityId = referringFacilityId; }
    public void setReferredToFacilityId(String referredToFacilityId) { this.referredToFacilityId = referredToFacilityId; }
    public void setReferralDate(String referralDate) { this.referralDate = referralDate; }
    public void setUrgencyLevel(String urgencyLevel) { this.urgencyLevel = urgencyLevel; }
    public void setReferralReason(String referralReason) { this.referralReason = referralReason; }
    public void setClinicalSummary(String clinicalSummary) { this.clinicalSummary = clinicalSummary; }
    public void setRequestedInvestigations(String requestedInvestigations) { this.requestedInvestigations = requestedInvestigations; }
    
    // IMPLEMENTED: setStatus method
    public void setStatus(String status) {
        this.status = status;
        this.lastUpdated = java.time.LocalDate.now().toString();
    }
    
    public void setAppointmentId(String appointmentId) { this.appointmentId = appointmentId; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }
    public void setLastUpdated(String lastUpdated) { this.lastUpdated = lastUpdated; }

    // IMPLEMENTED: generateReferralContent method
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
            safeString(referralId), safeString(patientId), safeString(referringClinicianId),
            safeString(referredToClinicianId), safeString(referringFacilityId),
            safeString(referredToFacilityId), safeString(referralDate), safeString(urgencyLevel),
            safeString(referralReason), safeString(clinicalSummary), safeString(requestedInvestigations),
            safeString(status), safeString(appointmentId), safeString(notes),
            safeString(createdDate), safeString(lastUpdated)
        );
    }

    // IMPLEMENTED: generateEmailContent method
    public String generateEmailContent() {
        return String.format(
            "EMAIL NOTIFICATION - NEW REFERRAL\n" +
            "=================================\n" +
            "From: Referral Management System\n" +
            "To: Specialist Department\n" +
            "Subject: New %s Referral - %s\n\n" +
            "Dear Specialist Team,\n\n" +
            "A new %s referral has been received with the following details:\n\n" +
            "Referral ID: %s\n" +
            "Patient ID: %s\n" +
            "Referring Clinician: %s\n" +
            "Urgency: %s\n" +
            "Clinical Summary: %s\n" +
            "Requested Investigations: %s\n\n" +
            "Please review this referral within the system and schedule an appointment.\n\n" +
            "Kind regards,\n" +
            "Referral Management System\n" +
            "Date: %s\n\n",
            safeString(urgencyLevel), safeString(referralId), 
            safeString(urgencyLevel).toLowerCase(),
            safeString(referralId), safeString(patientId), safeString(referringClinicianId),
            safeString(urgencyLevel), safeString(clinicalSummary), 
            safeString(requestedInvestigations), java.time.LocalDate.now()
        );
    }

    private String safeString(String value) {
        return value != null ? value : "N/A";
    }

    @Override
    public String toString() {
        return String.format("Referral[ID=%s, Patient=%s, Status=%s]", 
            safeString(referralId), safeString(patientId), safeString(status));
    }

    // Static factory method for creating new referrals
    public static Referral createNewReferral(String patientId, String referringClinicianId,
                                           String referredToClinicianId, String referringFacilityId,
                                           String referredToFacilityId, String urgencyLevel,
                                           String referralReason, String clinicalSummary,
                                           String requestedInvestigations) {
        String referralId = "R" + (System.currentTimeMillis() % 10000);
        return new Referral(
            referralId, patientId, referringClinicianId, referredToClinicianId,
            referringFacilityId, referredToFacilityId, 
            java.time.LocalDate.now().toString(), urgencyLevel,
            referralReason, clinicalSummary, requestedInvestigations,
            "New", "", "", java.time.LocalDate.now().toString(),
            java.time.LocalDate.now().toString()
        );
    }

    // Static method to create from CSV data
    public static Referral createFromCSV(String[] data) {
        if (data == null || data.length < 16) {
            System.err.println("Invalid CSV data for referral: insufficient fields");
            return null;
        }

        try {
            return new Referral(
                data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(),
                data[4].trim(), data[5].trim(), data[6].trim(), data[7].trim(),
                data[8].trim(), data[9].trim(), data[10].trim(), data[11].trim(),
                data[12].trim(), data[13].trim(), data[14].trim(), data[15].trim()
            
            );
        } catch (Exception e) {
            System.err.println("Error creating referral from CSV: " + e.getMessage());
            return null;
        }
    }
    
    
    
}