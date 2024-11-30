/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salesmanager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author yilei
 */
public class Files {
    /**
     * This method allows save a register in the file
     * @param day
     * @param productName
     * @param channel
     * @param quantity 
     */
    public void saveSaleToFile(int day, String productName, int channel,
            int quantity) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(
                "SalesRegister.txt", true))) {
            String channelName = (channel == 0) ? "Físico" : "En Línea";
            writer.write("Día " + (day + 1) + ": " + productName + ", Canal: "
                    + channelName + ", Cantidad: " + quantity + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
