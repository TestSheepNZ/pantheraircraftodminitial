package testsheepnz.panther.odm.calculator.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import testsheepnz.panther.odm.calculator.FuelRateData;

public class FuelRateDataTests {

    @Test
    public void readValidDataLine () {
        String dataStr = "|32000.0| 400.0| 9000.0| 7.3|";
        FuelRateData fuelData = new FuelRateData(dataStr);

        assertTrue("Fuel data provided processed", fuelData.getValidData());
    }


    @Test
    public void rejectInvalidDataLine () {
        String dataStr = "|32000.0| 400.0| 9000.0| 7.3|23.2|";
        FuelRateData fuelData = new FuelRateData(dataStr);

        assertFalse("Incorrect fuel data rejected", fuelData.getValidData());
    }
}
