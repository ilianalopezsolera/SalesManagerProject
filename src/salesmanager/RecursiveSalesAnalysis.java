/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salesmanager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class implements recursive methods. Calculate average sales over a
 * specific period of time and analyze trends
 *
 * @author yilei
 */
public class RecursiveSalesAnalysis {

    private SaleManagement saleManagement;

    public RecursiveSalesAnalysis(SaleManagement saleManagement) {
        this.saleManagement = saleManagement;
    }

    public double calculateAverageSales(int startDay, int endDay) {
        return calculateAverageSales(startDay, endDay, 0, 0);
    }

    public double calculateAverageSales(int startDay, int endDay, int totalSales, int count) {
        // Base de la recursión
        if (startDay >= endDay) {
            if (count == 0) {
                return 0.0;
            } else {
                return (double) totalSales / count;
            }
        }

        try {
            // Obtener las ventas totales del día actual para todos los productos
            int dailySales = saleManagement.getTotalSales(startDay, null); // null para obtener ventas de todos los productos

            // Llamada recursiva con acumulación de ventas y días
            return calculateAverageSales(startDay + 1, endDay, totalSales + dailySales, count + 1);
        } catch (Exception e) {
            System.out.println("Error al procesar el día " + startDay + ": " + e.getMessage());
            // Continuar al siguiente día
            return calculateAverageSales(startDay + 1, endDay, totalSales, count);
        }
    }

    // Detectar tendencias de ventas para un producto (aumento o disminución)
    public String detectTrends(String productName) {
        StringBuilder trends = new StringBuilder();
        detectTrendRecursive(productName, 0, -1, 0, "", trends);  // Inicia la recursión
        return trends.toString();
    }

    public void detectTrendRecursive(String productName, int day, int prevSales, int streak, String trend, StringBuilder trends) {
        if (day >= 30) {
            // Al final del mes, imprime las tendencias acumuladas
            if (streak > 1) {
                trends.append("Tendencia continua: " + streak + " días consecutivos de " + trend + " ventas.\n");
            }
            return;
        }

        // Obtener las ventas del día para el producto especificado
        int totalSales = getTotalSalesForDay(productName, day);

        // Si no hay ventas para ese día y producto, simplemente pasamos al siguiente día
        if (totalSales == 0) {
            detectTrendRecursive(productName, day + 1, prevSales, streak, trend, trends);
            return;
        }

        // Detectar cambios en las ventas: aumento, disminución o constante
        if (prevSales != -1) {
            if (totalSales > prevSales) {
                // Aumento de ventas
                if (trend.equals("Incremento")) {
                    streak++;  // Continuar la racha de incremento
                } else {
                    if (streak > 1) {
                        trends.append("Tendencia continua: " + streak + " días consecutivos de " + trend + " ventas.\n");
                    }
                    streak = 1;  // Nueva racha de incremento
                    trend = "Incremento";
                    trends.append("Día " + (day + 1) + ": Incremento de ventas.\n"); // Mostrar incremento
                }
            } else if (totalSales < prevSales) {
                // Disminución de ventas
                if (trend.equals("Disminución")) {
                    streak++;  // Continuar la racha de disminución
                } else {
                    if (streak > 1) {
                        trends.append("Tendencia continua: " + streak + " días consecutivos de " + trend + " ventas.\n");
                    }
                    streak = 1;  // Nueva racha de disminución
                    trend = "Disminución";
                    trends.append("Día " + (day + 1) + ": Disminución de ventas.\n"); // Mostrar disminución
                }
            } else {
                // Ventas constantes
                if (trend.equals("Constante")) {
                    streak++;  // Continuar la racha de ventas constantes
                } else {
                    if (streak > 1) {
                        trends.append("Tendencia continua: " + streak + " días consecutivos de " + trend + " ventas.\n");
                    }
                    streak = 1;  // Nueva racha de constante
                    trend = "Constante";
                    trends.append("Día " + (day + 1) + ": Ventas constantes.\n"); // Mostrar constante
                }
            }
        }

        detectTrendRecursive(productName, day + 1, totalSales, streak, trend, trends);
    }
        // Método que obtiene las ventas para un producto específico en un día dado
    private int getTotalSalesForDay(String productName, int day) {
        int totalSales = 0;

        // Leer el archivo de ventas y buscar las ventas para el día y producto específico
        try (BufferedReader reader = new BufferedReader(new FileReader("SalesRegister.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Comprobar si la línea corresponde al producto y al día que estamos buscando
                if (line.startsWith("Día " + (day + 1) + ":") && line.contains(productName)) {
                    String[] parts = line.split(", ");
                    String recordedProduct = parts[0].split(": ")[1];
                    int quantity = Integer.parseInt(parts[2].split(": ")[1]);

                    if (recordedProduct.equals(productName)) {
                        totalSales += quantity;  // Sumar las ventas de ese producto
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de ventas: " + e.getMessage());
        }

        return totalSales;
    }
}
