package testsheepnz.panther.odm.calculator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SystemProperties {
    private String odmFileName;

    public SystemProperties() {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("system.properties");

            // load a properties file
            prop.load(input);

            odmFileName = prop.getProperty("odm_file");
        } catch (
                IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getOdmFileName() { return this.odmFileName; }
}
