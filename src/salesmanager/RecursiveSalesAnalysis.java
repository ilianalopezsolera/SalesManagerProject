/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salesmanager;

/**
 *
 * @author yilei
 */
public class RecursiveSalesAnalysis {
    private SaleManagement saleManagement;

    public RecursiveSalesAnalysis(SaleManagement saleManagement) {
        this.saleManagement = saleManagement;
    }

    // Calcular promedio de ventas para un producto entre un rango de días (con recursión)
    public double calculateAverageSales(String productName, int startDay, 
            int endDay) {
        return calculateAverageSalesRecursive(productName, startDay, endDay, 0, 0);
    }

    private double calculateAverageSalesRecursive(String productName, int 
            startDay, int endDay, int totalSales, int count) {
        if (startDay > endDay) {
            return count == 0 ? 0 : (double) totalSales / count;  // Evitar división por 0 si no hay ventas
        }
        
        int dailySales = 0;
        try{
            dailySales= Integer.parseInt(saleManagement.getDailySalesSummary(
                    startDay, productName));
        }catch (NumberFormatException e) {
            System.out.println("Error");
        }
               
        return calculateAverageSalesRecursive(productName, startDay + 1, endDay, totalSales + dailySales, count + 1);  // Recursión
    }

    // Detectar tendencias de ventas para un producto (aumento o disminución)
    public void detectTrends(String productName) {
        detectTrendRecursive(productName, 0, -1);  // Inicia la recursión desde el primer día
    }

    private void detectTrendRecursive(String productName, int day, int prevSales) {
        if (day >= 30) return;  // Fin de los días (30 días en total)
        
        int totalSales = 0;
        try{
            totalSales= Integer.parseInt(saleManagement.getDailySalesSummary(
                    day, productName) );
        }catch (NumberFormatException e){
            System.out.println("Error");
        }
       
        
        // Comparar con las ventas del día anterior para detectar tendencias
        if (prevSales != -1) {
            if (totalSales > prevSales) {
                System.out.println("Día " + (day + 1) + ": Incremento de ventas.");
            } else if (totalSales < prevSales) {
                System.out.println("Día " + (day + 1) + ": Disminución de ventas.");
            }
        }
        
        // Llamada recursiva para el siguiente día
        detectTrendRecursive(productName, day + 1, totalSales);
    }
    
    
    
   
}
