
package salesmanager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class to handle the file that contains a record of sales.
 *
 * @author yilei
 */
public class Files {

    /**
     * This method allows save a register in the file
     *
     * @param day
     * @param productName
     * @param channel
     * @param quantity
     */
    public void saveSaleToFile(int day, String productName, int channel,
        int quantity) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(
            "SalesRegister.txt", true))) {
        String channelName;
        if (channel == 0) {
            channelName = "Físico";
        } else {
            channelName = "En Línea";
        }
        writer.write("Día " + (day + 1) + ": " + productName + ", Canal: "
                + channelName + ", Cantidad: " + quantity + "\n");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
