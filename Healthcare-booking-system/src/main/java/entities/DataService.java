/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author lekan
 */

import java.io.*;
import java.util.*;

public class DataService {
    private Repository repository;

    public DataService(Repository repository) {
        this.repository = repository;
    }

    public void loadAllData() {
        loadPatients("data/patients.csv");
        loadClinicians("data/clinicians.csv");
        loadFacilities("data/facilities.csv");
        loadAppointments("data/appointments.csv");
        loadPrescriptions("data/prescriptions.csv");
        loadReferrals("data/referrals.csv");
        loadStaff("data/staff.csv");
        System.out.println("All data loaded successfully!");
    }

    private void loadPatients(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] data = parseCSVLine(line);
                if (data.length >= 14) {
                    Patient patient = new Patient(
                        data[0], data[1], data[2], data[3], data[4], data[5],
                        data[6], data[7], data[8], data[9], data[10], data[11],
                        data[12], data[13]
                    );
                    repository.addPatient(patient);
                }
            }
            System.out.println("Loaded " + repository.getAllPatients().size() + " patients");
        } catch (IOException e) {
            System.err.println("Error loading patients: " + e.getMessage());
        }
    }

    private void loadClinicians(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] data = parseCSVLine(line);
                if (data.length >= 12) {
                    Clinician clinician = new Clinician(
                        data[0], data[1], data[2], data[3], data[4], data[5],
                        data[6], data[7], data[8], data[9], data[10], data[11]
                    );
                    repository.addClinician(clinician);
                }
            }
            System.out.println("Loaded " + repository.getAllClinicians().size() + " clinicians");
        } catch (IOException e) {
            System.err.println("Error loading clinicians: " + e.getMessage());
        }
    }

    private void loadFacilities(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] data = parseCSVLine(line);
                if (data.length >= 11) {
                    Facility facility = new Facility(
                        data[0], data[1], data[2], data[3], data[4], data[5],
                        data[6], data[7], data[8], data[9], data[10]
                    );
                    repository.addFacility(facility);
                }
            }
            System.out.println("Loaded " + repository.getAllFacilities().size() + " facilities");
        } catch (IOException e) {
            System.err.println("Error loading facilities: " + e.getMessage());
        }
    }

    private void loadAppointments(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] data = parseCSVLine(line);
                if (data.length >= 13) {
                    Appointment appointment = new Appointment(
                        data[0], data[1], data[2], data[3], data[4], data[5],
                        data[6], data[7], data[8], data[9], data[10], data[11], data[12]
                    );
                    repository.addAppointment(appointment);
                }
            }
            System.out.println("Loaded " + repository.getAllAppointments().size() + " appointments");
        } catch (IOException e) {
            System.err.println("Error loading appointments: " + e.getMessage());
        }
    }

    private void loadPrescriptions(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] data = parseCSVLine(line);
                if (data.length >= 15) {
                    Prescription prescription = new Prescription(
                        data[0], data[1], data[2], data[3], data[4], data[5],
                        data[6], data[7], data[8], data[9], data[10], data[11],
                        data[12], data[13], data[14]
                    );
                    repository.addPrescription(prescription);
                }
            }
            System.out.println("Loaded " + repository.getAllPrescriptions().size() + " prescriptions");
        } catch (IOException e) {
            System.err.println("Error loading prescriptions: " + e.getMessage());
        }
    }

    private void loadReferrals(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] data = parseCSVLine(line);
                if (data.length >= 17) {
                    Referral referral = Referral.createReferral(
                        data[0], data[1], data[2], data[3], data[4], data[5],
                        data[6], data[7], data[8], data[9], data[10], data[11],
                        data[12], data[13], data[14], data[15], data[16]
                    );
                    repository.addReferral(referral);
                }
            }
            System.out.println("Loaded " + repository.getAllReferrals().size() + " referrals");
        } catch (IOException e) {
            System.err.println("Error loading referrals: " + e.getMessage());
        }
    }

    private void loadStaff(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] data = parseCSVLine(line);
                if (data.length >= 12) {
                    Staff staff = new Staff(
                        data[0], data[1], data[2], data[3], data[4], data[5],
                        data[6], data[7], data[8], data[9], data[10], data[11]
                    );
                    repository.addStaff(staff);
                }
            }
            System.out.println("Loaded " + repository.getAllStaff().size() + " staff members");
        } catch (IOException e) {
            System.err.println("Error loading staff: " + e.getMessage());
        }
    }

    private String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder field = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(field.toString());
                field.setLength(0);
            } else {
                field.append(c);
            }
        }
        result.add(field.toString());
        return result.toArray(new String[0]);
    }
}
