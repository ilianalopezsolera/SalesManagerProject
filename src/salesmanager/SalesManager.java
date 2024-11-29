
package salesmanager;
import java.io.*;
import java.util.Scanner;


/**
 *
 * @author ilico
 */
public class SalesManager {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int option;
        Scanner sc = new Scanner(System.in);
        
        try(BufferedReader reader = new BufferedReader(new FileReader("FileManager.txt"))){
            
        }catch(IOException e){
            e.printStackTrace();
        }
        
        
        System.out.println("1. Registrar Venta. \n2. Mostrar Reporte \n3. Detectar Tendencias. \n4");
        option = sc.nextInt();
        
        switch(option){
            
        }
    }
    
    
    
}
