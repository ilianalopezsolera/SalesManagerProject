package salesmanager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class implements recursive methods. Calculate average sales over a
 * specific period of time and analyze trends
 *
 * @author Yilei Granados
 * @author Meylin Lopez
 */
public class RecursiveSalesAnalysis {

    private SaleManagement saleManagement;

    public RecursiveSalesAnalysis(SaleManagement saleManagement) {
        this.saleManagement = saleManagement;
    }

    /**
     * Calculates the average sales between two specified days recursively. This
     * method initializes the recursive process.
     *
     * @param startDay The starting day (inclusive) for the average calculation.
     * @param endDay The ending day (exclusive) for the average calculation.
     * @return The average sales between the specified days. Returns 0.0 if no
     * sales are recorded.
     */
    public double calculateAverageSales(int startDay, int endDay) {
        return calculateAverageSales(startDay, endDay, 0, 0);
    }

    /**
     * Calculates the average sales between two specified days using recursion.
     * This method processes the sales data for each day within the range and
     * accumulates the values.
     *
     * @param startDay The current day being processed in the recursion.
     * @param endDay The ending day (exclusive) for the average calculation.
     * @param totalSales Accumulator for the sum of sales processed so far.
     * @param count Accumulator for the count of days processed so far.
     * @return The average sales between the specified days. Returns 0.0 if no
     * sales are recorded.
     */
    public double calculateAverageSales(int startDay, int endDay, int totalSales,
            int count) {
        // Base de la recursión
        if (startDay >= endDay) {
            if (count == 0) {
                return 0.0;
            } else {
                return (double) totalSales / count;
            }
        }

        try {
            // Obtener las ventas totales del día actual para todos 
            // los productos
            int dailySales = saleManagement.getTotalSales(startDay, null);

            // Llamada recursiva con acumulación de ventas y días
            return calculateAverageSales(startDay + 1, endDay, totalSales
                    + dailySales, count + 1);
        } catch (Exception e) {
            System.out.println("Error al procesar el día " + startDay + ": "
                    + e.getMessage());
            // Continuar al siguiente día
            return calculateAverageSales(startDay + 1, endDay, totalSales,
                    count);
        }
    }

    /**
     * Initializes the recursive process for detecting sales trends of a
     * specific product over a 30-day period. This method starts the trend
     * detection by invoking the `detectTrendRecursive` method.
     *
     * @param productName The name of the product to analyze trends for.
     * @return A string summarizing the detected trends over the 30-day period,
     * including any increases, decreases, or periods of constant sales.
     */
    public String detectTrends(String productName) {
        StringBuilder trends = new StringBuilder();
        detectTrendRecursive(productName, 0, -1, 0, "Indefinido", trends);
        return trends.toString();
    }

    /**
     * Recursively detects sales trends for a specific product over a 30-day
     * period. If no sales are registered on a given day, it keeps the previous
     * trend until there is a significant change in sales (increase, decrease,
     * or constant).
     *
     * @param productName The name of the product to analyze sales trends for.
     * @param day The current day being analyzed (0-based index for days).
     * @param prevSales The total sales from the previous day. Used to detect
     * trends.
     * @param streak The current streak of consecutive days with the same trend.
     * @param trend The current trend (e.g., "Increase", "Decrease", or
     * "Constant").
     * @param trends A StringBuilder used to accumulate the descriptions of
     * detected trends.
     */
    public void detectTrendRecursive(String productName, int day, int prevSales,
            int streak, String trend, StringBuilder trends) {

        // Si hemos recorrido los 30 días, terminamos la recursión
        if (day >= 30) {
            if (streak > 1) {
                trends.append("Tendencia continua: " + streak + " días consecutivos de " + trend + " ventas.\n");
            }
            return;
        }

        // Obtener las ventas del día para el producto especificado
        int totalSales = getTotalSalesForDay(productName, day);

        // Mostrar las ventas para el día en depuración
        System.out.println("Día " + (day + 1) + " - Ventas: " + totalSales);

        // Si no hay ventas para ese día y producto, tratamos de mantener la tendencia anterior
        if (totalSales == 0) {
            if (streak > 1) {
                trends.append("Tendencia continua: " + streak + " días consecutivos de " + trend + " ventas.\n");
            }
            trends.append("Día " + (day + 1) + ": No se registraron ventas.\n"); // Mostrar el día sin ventas
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

        // Llamar de nuevo a la recursión para el siguiente día
        detectTrendRecursive(productName, day + 1, totalSales, streak, trend, trends);
    }

    /**
     * Retrieves the total sales for a specific product on a given day.
     *
     * @param productName The name of the product to retrieve sales for.
     * @param day The day to retrieve sales for (0-based index for days).
     * @return The total sales for the product on the specified day. Returns 0
     * if no sales are found.
     */
    public int getTotalSalesForDay(String productName, int day) {
        int totalSales = 0;

        // Leer el archivo de ventas y buscar las ventas para el día y
        // producto específico
        try (BufferedReader reader = new BufferedReader(new FileReader("SalesRegister.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Comprobar si la línea corresponde al producto y al día que
                // estamos buscando
                if (line.startsWith("Día " + (day + 1) + ":")
                        && line.contains(productName)) {
                    String[] parts = line.split(", ");
                    String recordedProduct = parts[0].split(": ")[1];
                    int quantity = Integer.parseInt(parts[2].split(": ")[1]);

                    if (recordedProduct.equals(productName)) {
                        totalSales += quantity;  // Sumar las ventas
                        // de ese producto
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de ventas: "
                    + e.getMessage());
        }

        return totalSales;
    }
}
