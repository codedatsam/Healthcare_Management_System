/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author lekan
 */



import java.util.*;

public class Repository {
    private Map<String, Patient> patients;
    private Map<String, Clinician> clinicians;
    private Map<String, Facility> facilities;
    private Map<String, Appointment> appointments;
    private Map<String, Prescription> prescriptions;
    private Map<String, Referral> referrals;
    private Map<String, Staff> staff;

    public Repository() {
        patients = new HashMap<>();
        clinicians = new HashMap<>();
        facilities = new HashMap<>();
        appointments = new HashMap<>();
        prescriptions = new HashMap<>();
        referrals = new HashMap<>();
        staff = new HashMap<>();
    }

    // Patient methods
    public void addPatient(Patient patient) {
        patients.put(patient.getPatientId(), patient);
    }
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients.values());
    }
    public Patient getPatientById(String id) {
        return patients.get(id);
    }
    public void updatePatient(Patient patient) {
        patients.put(patient.getPatientId(), patient);
    }
    public void deletePatient(String patientId) {
        patients.remove(patientId);
    }

    // Clinician methods
    public void addClinician(Clinician clinician) {
        clinicians.put(clinician.getClinicianId(), clinician);
    }
    public List<Clinician> getAllClinicians() {
        return new ArrayList<>(clinicians.values());
    }
    public Clinician getClinicianById(String id) {
        return clinicians.get(id);
    }

    // Facility methods
    public void addFacility(Facility facility) {
        facilities.put(facility.getFacilityId(), facility);
    }
    public List<Facility> getAllFacilities() {
        return new ArrayList<>(facilities.values());
    }

    // Appointment methods
    public void addAppointment(Appointment appointment) {
        appointments.put(appointment.getAppointmentId(), appointment);
    }
    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments.values());
    }
    public void updateAppointment(Appointment appointment) {
        appointments.put(appointment.getAppointmentId(), appointment);
    }
    public void deleteAppointment(String appointmentId) {
        appointments.remove(appointmentId);
    }

    // Prescription methods
    public void addPrescription(Prescription prescription) {
        prescriptions.put(prescription.getPrescriptionId(), prescription);
    }
    public List<Prescription> getAllPrescriptions() {
        return new ArrayList<>(prescriptions.values());
    }
    public void updatePrescription(Prescription prescription) {
        prescriptions.put(prescription.getPrescriptionId(), prescription);
    }
    public void deletePrescription(String prescriptionId) {
        prescriptions.remove(prescriptionId);
    }

    // Referral methods
    public void addReferral(Referral referral) {
        referrals.put(referral.getReferralId(), referral);
    }
    public List<Referral> getAllReferrals() {
        return new ArrayList<>(referrals.values());
    }
    public void updateReferral(Referral referral) {
        referrals.put(referral.getReferralId(), referral);
    }

    // Staff methods
    public void addStaff(Staff staffMember) {
        staff.put(staffMember.getStaffId(), staffMember);
    }
    public List<Staff> getAllStaff() {
        return new ArrayList<>(staff.values());
    }
}
