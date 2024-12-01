/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salesmanager;

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
    public void detectTrends(String productName) {
        detectTrendRecursive(productName, 0, -1, 0, "");  // Inicia la recursión desde el primer día
    }

    private void detectTrendRecursive(String productName, int day, int prevSales, int streak, String trend) {
        if (day >= 30) {
            // Al final del mes, imprime las tendencias acumuladas
            if (streak > 1) {
                System.out.println("Tendencia continua: " + streak + " días consecutivos de " + trend + " ventas.");
            }
            return;  // Fin de los días (30 días en total)
        }

        int totalSales = 0;
        try {
            // Obtener las ventas del día para el producto especificado
            totalSales = Integer.parseInt(saleManagement.getDailySalesSummary(day, productName));
        } catch (NumberFormatException e) {
            System.out.println("Error al obtener ventas del día " + (day + 1) + " para el producto: " + productName);
        }

        // Detectar cambios en las ventas: aumento, disminución o constante
        if (prevSales != -1) {
            if (totalSales > prevSales) {
                if (trend.equals("Incremento")) {
                    streak++;  // Continuar la racha de incremento
                } else {
                    if (streak > 1) {
                        System.out.println("Tendencia continua: " + streak + " días consecutivos de " + trend + " ventas.");
                    }
                    streak = 1;  // Nueva racha de incremento
                    trend = "Incremento";
                }
            } else if (totalSales < prevSales) {
                if (trend.equals("Disminución")) {
                    streak++;  // Continuar la racha de disminución
                } else {
                    if (streak > 1) {
                        System.out.println("Tendencia continua: " + streak + " días consecutivos de " + trend + " ventas.");
                    }
                    streak = 1;  // Nueva racha de disminución
                    trend = "Disminución";
                }
            } else {
                // Ventas constantes
                if (streak > 1) {
                    System.out.println("Tendencia continua: " + streak + " días consecutivos de " + trend + " ventas.");
                }
                streak = 1;  // Racha de ventas constantes
                trend = "Constante";
            }
        }

        // Llamada recursiva para el siguiente día
        detectTrendRecursive(productName, day + 1, totalSales, streak, trend);
    }
}
