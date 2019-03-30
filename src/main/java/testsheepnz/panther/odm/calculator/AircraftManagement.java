package testsheepnz.panther.odm.calculator;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import org.junit.Test;

public class AircraftManagement {


    // Weights
    private double acWeight = 6000.0;
    private double acEquipmentWeight;
    private double acFuelRemaining;
    private double acMaxFuel;

    private double fuelUsedLastManoeuvre;

    // Other data
    private double acAltitude;

    // Fuel info
    private List<FuelRateData> fuelData;

    // Fuel info file
    private String fuelFile = "src/main/resources/data/fuel_data.txt";

    public AircraftManagement() {
        fuelData = new ArrayList<FuelRateData>();
        acWeight = 6000.0;
        acEquipmentWeight = 0.0;
        acFuelRemaining = 0.0;
        acMaxFuel = 3000.0;
        acAltitude = 0.0;
        fuelUsedLastManoeuvre = 0.0;

        initialiseFuelUsage();
    }

    // Return all up weight
    public double getAllUpWeight() {
        return acWeight + acEquipmentWeight + acFuelRemaining;
    }

    public double getRemainingFuel() {
        return acFuelRemaining;
    }

    // Climb / dive fuel usage
    public boolean climb(double newAltitude) {
        double fuelUsed = 1 * (newAltitude - acAltitude) / 100.0;
        fuelUsedLastManoeuvre = fuelUsed;
        acFuelRemaining -= fuelUsed;
        acAltitude = newAltitude;
        return true;
    }

    public boolean dive(double newAltitude) {
        acAltitude = newAltitude;
        fuelUsedLastManoeuvre = 0.0;
        return true;
    }

    // Fuel usage over a leg ...
    public boolean aircraftLeg(double speed, double distance) {
        double fuelUsage = 27.7; // Test data for now
        double fuelUsed;

        for (FuelRateData thisFuelData : fuelData) {
            if (thisFuelData.dataMatch(acAltitude, speed, getAllUpWeight())) {
                fuelUsage = thisFuelData.getFuelUsage();
            }
        }
        // System.out.println("Fuel usage " + fuelUsage);
        fuelUsed = fuelUsage * distance * 60.0 / speed;
        fuelUsedLastManoeuvre = fuelUsed;
        acFuelRemaining -= fuelUsed;

        return true;
    }

    // Just added this to help testability
    public double getFuelUsedLastManoeuvre() {
        return fuelUsedLastManoeuvre;
    }

    // Manage aircraft equipment
    public boolean addA2AMissile() {
        return addA2AMissile(1);
    }

    public boolean addA2AMissile(int num) {
        acEquipmentWeight += (num * 150.0);
        return true;
    }

    public boolean addReconPod() {
        acEquipmentWeight += 800.0;
        return true;
    }

    public boolean addDumbBomb() {
        return addDumbBomb(1);
    }

    public boolean addDumbBomb(int num) {
        acEquipmentWeight += (num * 600.0);
        return true;
    }

    public boolean addIntelliBomb() {
        acEquipmentWeight += 2000.0;
        return true;
    }

    public boolean addExternalFuelTanks() {
        acEquipmentWeight += 200.0;
        acMaxFuel += 3000.0;
        return true;
    }

    public boolean dropDumbBomb() {
        return dropDumbBomb(1);
    }

    public boolean dropDumbBomb(int num) {
        acEquipmentWeight -= (num * 600.0);
        return true;
    }

    public boolean dropIntelliBomb() {
        acEquipmentWeight -= 2000.0;
        return true;
    }

    // Refuel
    public boolean addFuel(double fuelTopUp) {
        if ((acFuelRemaining + fuelTopUp) > acMaxFuel) {
            return false;
        }

        if (acAltitude != 0.0 && acAltitude < 10000.0) {
            return false;
        }

        acFuelRemaining += fuelTopUp;
        return true;
    }

    // Set up fuel usage data ...
    private void initialiseFuelUsage() {
        int countData=0;

        try {
            File file = new File(fuelFile);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            // FileWriter writeToFile = new FileWriter(file);
            StringBuffer stringBuffer = new StringBuffer();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append("\n");
                FuelRateData lineData = new FuelRateData(line);
                //Only add if correct
                if(lineData.getValidData()) {
                    fuelData.add(lineData);
                    countData++;
                } else {
                    System.out.println("ERROR - fuel_data file corrupt");
                    System.exit(0);
                }

            }
            fileReader.close();
            //System.out.println("Contents of file:");
            //System.out.println(stringBuffer.toString());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR - fuel_data file not found");
        }

        if(countData != 180) {
            System.out.println("ERROR - fuel_data file missing entries");
            System.exit(0);
        }

    }


}
