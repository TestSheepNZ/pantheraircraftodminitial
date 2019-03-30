package testsheepnz.panther.odm.calculator.test;

import org.junit.Test;
import testsheepnz.panther.odm.calculator.AircraftManagement;

import static org.junit.Assert.*;

public class AircraftManagementTests {

    @Test
    public void reconPanther() {
        AircraftManagement thisAircraft = new AircraftManagement();

        thisAircraft.addA2AMissile(8);
        thisAircraft.addReconPod();

        // Aircraft Weight + equipment should be 6000 + 2000
        assertTrue("Aircraft weight should be 6000 + 2000 kg", (6000 + 2000) == thisAircraft.getAllUpWeight());

    }

    @Test
    public void dumbLoadedPanther() {
        AircraftManagement thisAircraft = new AircraftManagement();

        thisAircraft.addA2AMissile(4);
        thisAircraft.addDumbBomb(3);
        thisAircraft.addExternalFuelTanks();

        // Aircraft Weight + equipment should be 6000 + 2600
        assertTrue("Aircraft weight should be 6000 + 2600 kg", (6000 + 2600) == thisAircraft.getAllUpWeight());

        // Now drop 2 bombs ... recalculate all up weight
        // Aircraft Weight + equipment should be 6000 + 1400
        thisAircraft.dropDumbBomb(2);
        assertTrue("Aircraft weight should be 6000 + 1400 kg", (6000 + 1400) == thisAircraft.getAllUpWeight());
    }

    @Test
    public void climbDiveClimb() {
        AircraftManagement thisAircraft = new AircraftManagement();

        // Add some fuel
        thisAircraft.addFuel(1000.0);

        // Climb to 30,000 - confirm that 300kg fuel is used
        thisAircraft.climb(30000);
        assertTrue("300kg fuel used in climb", (300.0 == thisAircraft.getFuelUsedLastManoeuvre()));

        // Dive to 10,000
        thisAircraft.dive(10000);
        assertTrue("No fuel used in dive", (0.0 == thisAircraft.getFuelUsedLastManoeuvre()));

        // Climb to 30,000 - confirm that 300kg fuel is used
        thisAircraft.climb(30000);
        assertTrue("200kg fuel used in climb", (200.0 == thisAircraft.getFuelUsedLastManoeuvre()));
    }

    @Test
    public void exampleRoundingUpData() {
        AircraftManagement thisAircraft = new AircraftManagement();

        // Add loadout - I fudged this to get the weigh in the example
        thisAircraft.addA2AMissile(8);
        thisAircraft.addDumbBomb(4);
        thisAircraft.addExternalFuelTanks();

        // Add some fuel
        thisAircraft.addFuel(3000.0 - 420.0);

        // Climb to 28000 ft
        thisAircraft.climb(28000);

        // Confirm AUW is 12100 as in example
        assertTrue("AUW is 12100", (12100 == thisAircraft.getAllUpWeight()));

        // Find ammout of fuel used going 400nm at 440kts
        thisAircraft.aircraftLeg(440.0, 400.0);
        assertEquals("1161kg fuel used", 1161.81, thisAircraft.getFuelUsedLastManoeuvre(), 0.01);

        // Find ammout of fuel used going 200nm at 440kts
        thisAircraft.aircraftLeg(440.0, 200.0);
        assertEquals("542kg fuel used", 542.72, thisAircraft.getFuelUsedLastManoeuvre(), 0.01);
    }

    @Test
    public void workedExample() {
        AircraftManagement thisAircraft = new AircraftManagement();

        // Add loadout - I fudged this to get the weigh in the example
        thisAircraft.addA2AMissile(8);
        thisAircraft.addDumbBomb(4);
        thisAircraft.addExternalFuelTanks();

        // Add some fuel
        thisAircraft.addFuel(5000.0);

        // Confirm all up weight 14,800kg
        assertTrue("Aircraft weight should be 14,800 kg", 14800.0 == thisAircraft.getAllUpWeight());

        // Climb to 20,000
        thisAircraft.climb(20000.0);
        assertEquals("200kg fuel used", 200.0, thisAircraft.getFuelUsedLastManoeuvre(), 0.01);

        // Cruise to Palmerston North at 400 kts, should use 265.5kg fuel
        thisAircraft.aircraftLeg(400.0, 75.0);
        assertEquals("182.25kg fuel used", 182.25, thisAircraft.getFuelUsedLastManoeuvre(), 0.01);

        // Descend to 200ft, travel 86nm to Waioru at 600kts, and will use 626.94 kg of fuel
        thisAircraft.dive(200.0);
        thisAircraft.aircraftLeg(600.0, 86.0);
        assertEquals("626.94kg fuel used", 626.94, thisAircraft.getFuelUsedLastManoeuvre(), 0.01);

        // Drop bombs
        // Climb to 1000 ft - should use 8 kg fuel
        thisAircraft.dropDumbBomb(4);
        thisAircraft.climb(1000.0);
        assertEquals("8kg fuel used", 8.0, thisAircraft.getFuelUsedLastManoeuvre(), 0.01);

        // Fly to Wanganui, 500kts, 57nm
        thisAircraft.aircraftLeg(500.0, 57.0);

        // Take on fuel
        thisAircraft.climb(32000.0);
        assertTrue("Refuelling happened", thisAircraft.addFuel(2000.0));

        // Climb to 32,000 ft ... fly to Sydney
        thisAircraft.climb(32000.0);
        thisAircraft.aircraftLeg(500.0, 1188.0);
    }

}
