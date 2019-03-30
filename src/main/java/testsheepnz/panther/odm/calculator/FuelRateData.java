package testsheepnz.panther.odm.calculator;


public class FuelRateData {
    private int acHeight;
    private int acSpeed;
    private int acWeight;
    private double fuelUsagePerMin;
    private Boolean validData;

    @Deprecated
    public FuelRateData(double height, double speed, double weight, double fuelPerMin) {
        acHeight = correctHeight(height);
        acSpeed = correctSpeed(speed);
        acWeight = correctWeight(weight);
        validData = true;

        fuelUsagePerMin = fuelPerMin;
    }

    public FuelRateData(String lineData) {
        double height = 0.0, speed = 0.0, weight = 0.0, fuelPerMin = 0.0;
        validData = true;

        String[] fuelData = lineData.split("\\|");
        if (fuelData.length == 5) {
            height = convertStringNumber(fuelData[1]);
            speed = convertStringNumber(fuelData[2]);
            weight = convertStringNumber(fuelData[3]);
            fuelPerMin = convertStringNumber(fuelData[4]);
        } else {
            validData = false;
        }

        if ( validData.booleanValue() && height >= 0.0 && speed >= 400.0 && weight >= 6000.0 && fuelPerMin > 0.0)
        {
            acHeight = correctHeight(height);
            acSpeed = correctSpeed(speed);
            acWeight = correctWeight(weight);
            fuelUsagePerMin = fuelPerMin;
        }
        else
        {
            validData = false;
        }
    }

    private double convertStringNumber(String strNum) {
        double retValue;

        try {
            retValue = Double.parseDouble(strNum);
        } catch (NumberFormatException e) {
            validData = false;
            return -1.0;
        }
        return retValue;
    }

    public boolean dataMatch(double height, double speed, double weight) {
        boolean matchVal;

        matchVal = (correctHeight(height) == acHeight) && (correctSpeed(speed) == acSpeed) && (correctWeight(weight) == acWeight);

        if (matchVal == true) {
            correctHeight(height);
            correctSpeed(speed);
        }

        return matchVal;
    }

    public Boolean getValidData(){
        return validData;
    }

    public double getFuelUsage() {
        return fuelUsagePerMin;
    }

    private int correctHeight(double height) {
        int retHeight = 32000;

        if (height < 500.0) {
            retHeight = 0;
        } else if (height < 2000.0) {
            retHeight = 500;
        } else if (height < 8000.0) {
            retHeight = 2000;
        } else if (height < 16000.0) {
            retHeight = 8000;
        } else if (height < 32000.0) {
            retHeight = 16000;
        }

        return retHeight;
    }

    private int correctWeight(double weight) {
        int retWeight = 6000;

        if (weight > 15000.0) {
            retWeight = 16000;
        } else if (weight > 14000.0) {
            retWeight = 15000;
        } else if (weight > 13000.0) {
            retWeight = 14000;
        } else if (weight > 12000.0) {
            retWeight = 13000;
        } else if (weight > 11000.0) {
            retWeight = 12000;
        } else if (weight > 10000.0) {
            retWeight = 11000;
        } else if (weight > 9000.0) {
            retWeight = 10000;
        } else if (weight > 8000.0) {
            retWeight = 9000;
        } else if (weight > 7000.0) {
            retWeight = 8000;
        } else if (weight > 6000.0) {
            retWeight = 7000;
        }

        return retWeight;
    }

    private int correctSpeed(double speed) {
        int retSpeed = 400;

        if (speed > 500.0) {
            retSpeed = 600;
        } else if (speed > 400.0) {
            retSpeed = 500;
        }

        return retSpeed;
    }
}
