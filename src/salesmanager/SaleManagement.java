package salesmanager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class allows for better handling of sales through arrangements. Record
 * sales in a file and add up the number of sales.
 *
 * @author Yilei Granados
 * @author Meylin Lopez
 */
public class SaleManagement {

    private String[] productNames = {
        "Base líquida",
        "Polvo Compacto",
        "Corrector",
        "Bronceador",
        "Rubor",
        "Delineador de ojos",
        "Máscara de pestañas",
        "Gel para cejas",
        "Tinta de labios"
    }; // Arreglo para manejar productos de la tienda 

    private int[][][] sales; // Almacena de esta manera: [día][producto][canal]

    private Files persistenceManager = new Files();

    SaleManagement() {
        sales = new int[30][productNames.length][2];
    }

    /**
     * This method allows to register a sale. The index will help the graphical
     * interface and save the sale.
     *
     * @param day
     * @param productName
     * @param channel
     * @param quantity
     */
    public void registerSale(int day, String productName, int channel,
            int quantity) {
        try {
            int productIndex = getProductIndex(productName);
            if (productIndex != -1 && day >= 0 && day < 30) {
                sales[day][productIndex][channel] += quantity;
                persistenceManager.saveSaleToFile(day, productName, channel,
                        quantity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method calculates all sales recorded in a file for a specific
     * product and day according to the type of channel.
     *
     * @param day
     * @param productName
     * @return
     */
    public String getDailySalesSummary(int day, String productName) {
        int totalPhysicalSales = 0;
        int totalOnlineSales = 0;

        try (BufferedReader reader = new BufferedReader
        (new FileReader("SalesRegister.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Día " + day + ":")) {
                    String[] parts = line.split(", ");
                    String recordedProduct = parts[0].split(": ")[1];
                    String channel = parts[1].split(": ")[1];
                    int quantity = Integer.parseInt(parts[2].split(": ")[1]);

                    if (recordedProduct.equals(productName)) {
                        if (channel.equalsIgnoreCase("Físico")) {
                            totalPhysicalSales += quantity;
                        } else if (channel.equalsIgnoreCase("En línea")) {
                            totalOnlineSales += quantity;
                        }
                    }
                }
            }
        } catch (IOException e) {
            return "Error al leer el archivo de ventas: " + e.getMessage();
        }

        if (totalPhysicalSales == 0 && totalOnlineSales == 0) {
            return "No se encontraron ventas para el producto o el día "
                    + "especificado.";
        }

        return "Día " + day + ": " + productName + " - Tienda: "
                + totalPhysicalSales + ", En línea: " + totalOnlineSales;
    }

    /**
     * Retrieves the total sales for a specific day and product from the sales
     * register file.
     *
     * @param day The day for which sales are to be retrieved.
     * @param productName The name of the product to filter sales. If null,
     * sales for all products will be retrieved.
     * @return The total sales for the specified day and product. Returns 0 if
     * no sales are found or if an error occurs.
     */
    public int getTotalSales(int day, String productName) {
        int totalSales = 0;

        try (BufferedReader reader = new BufferedReader
        (new FileReader("SalesRegister.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Día " + day + ":")) {
                    String[] parts = line.split(", ");
                    String recordedProduct = parts[0].split(": ")[1];
                    int quantity = Integer.parseInt(parts[2].split(": ")[1]);

                    totalSales += quantity;

                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de ventas: "
                    + e.getMessage());
        }

        return totalSales; // Total de ventas (puede ser 0 si no hay datos)
    }

    /**
     * Retrieves the total sales for a specific product on a given day by
     * summing both physical and online sales.
     *
     * This method first identifies the index of the specified product, and if
     * the product is found, it calculates the total sales for that day by
     * adding the physical and online sales data.
     *
     * @param productName The name of the product for which sales data is being
     * retrieved.
     * @param day The day (0-based index) for which sales data is to be fetched.
     * @return The total sales for the specified product on the given day,
     * including both physical and online sales. If the product is not found, it
     * returns 0.
     */
    public int getTotalSalesForDay(String productName, int day) {
        int productIndex = getProductIndex(productName);
        if (productIndex != -1) {
            // Sumar ventas físicas y en línea
            return sales[day][productIndex][0] + sales[day][productIndex][1];
        }
        return 0;
    }

    /**
     * This method allows you to get the product names for the interface.
     *
     * @return
     */
    public String[] getProductNames() {
        return productNames;
    }

    /**
     * This method allows you to obtain the index of the product by name for the
     * graphical interface
     *
     * @param productName
     * @return
     */
    public int getProductIndex(String productName) {
        for (int i = 0; i < productNames.length; i++) {
            if (productNames[i].equals(productName)) {
                return i;
            }
        }
        return -1;
    }

}
