/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salesmanager;

/**
 *
 * @author yilei
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
    }; // Arreglo para manejar productos de una tienda de maquillaje

    private int[][][] sales; // Almacena de esta manera: [día][producto][canal]

    private Files persistenceManager = new Files();

    public SaleManagement() {
        sales = new int[30][productNames.length][2];
    }//30 días del mes, productos, 2 tipos de canales

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
                sales[day][productIndex][channel] += quantity; // Acumula las ventas
                // Guardar la venta en un archivo de texto
                persistenceManager.saveSaleToFile(day, productName, channel, quantity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Obtener ventas totales de un producto en un día (físico + online)
    public String getDailySalesSummary(int day, String productName) {
        int productIndex = getProductIndex(productName);
        if (productIndex != -1 && day >= 0 && day < 30) {
            int physicalSales = sales[day][productIndex][0];
            int onlineSales = sales[day][productIndex][1];
            return "Día " + (day + 1) + ": " + productName + " - Tienda: "
                    + physicalSales + ", Online: " + onlineSales;
        }
        return "No se encontraron datos para el producto o el día especificado.";
    }

    // Obtener todos los nombres de productos
    public String[] getProductNames() {
        return productNames;
    }

    // Obtener el índice de un producto por su nombre
    private int getProductIndex(String productName) {
        for (int i = 0; i < productNames.length; i++) {
            if (productNames[i].equals(productName)) {
                return i;
            }
        }
        return -1;
    }

}
