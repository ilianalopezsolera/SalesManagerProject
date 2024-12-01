package salesmanager;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 *
 * @author ilico
 */
public class SalesManager {

    private JFrame frame;
    private JComboBox<String> productComboBox;
    private JComboBox<String> channelComboBox;
    private JTextField quantityField;
    private JTextArea salesTextArea;
    private SaleManagement salesData;
    private RecursiveSalesAnalysis salesAnalysis;

    public SalesManager(SaleManagement salesData, RecursiveSalesAnalysis salesAnalysis) {
        this.salesData = salesData;
        this.salesAnalysis = salesAnalysis;
        initialize();
    }

    private void initialize() {
        // Configurar ventana
        frame = new JFrame("Mar Rosa Brand");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new FlowLayout());
        frame.getContentPane().setBackground(new Color(255, 228, 225));

        ImageIcon logoIcon = new ImageIcon("C:\\Users\\yilei\\Pictures\\MarRosaIcon.png");
        Image scaledImage = logoIcon.getImage().getScaledInstance(190, 90, Image.SCALE_SMOOTH);
        ImageIcon originalIcon = new ImageIcon(scaledImage);

        JLabel logoLabel = new JLabel(originalIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(logoLabel, BorderLayout.NORTH);

        // Crear componentes
        JLabel productLabel = new JLabel("Selecciona el producto:");
        productComboBox = new JComboBox<>(salesData.getProductNames());
        productLabel.setForeground(new Color(219, 112, 147));
        productComboBox = new JComboBox<>(salesData.getProductNames());
        productComboBox.setBackground(new Color(255, 182, 193));
        productComboBox.setForeground(Color.BLACK);

        JLabel channelLabel = new JLabel("Selecciona el canal de venta:");
        channelLabel.setForeground(new Color(219, 112, 147));
        channelComboBox = new JComboBox<>(new String[]{"Físico", "En Línea"});
        channelComboBox.setBackground(new Color(255, 182, 193));
        channelComboBox.setForeground(Color.BLACK);

        JLabel quantityLabel = new JLabel("Cantidad vendida:");
        quantityLabel.setForeground(new Color(219, 112, 147));
        quantityField = new JTextField(10);
        quantityField.setBackground(new Color(255, 240, 245));
        quantityField.setForeground(Color.BLACK);

        JButton registerSaleButton = new JButton("Registrar venta");
        registerSaleButton.setBackground(new Color(199, 21, 133));
        registerSaleButton.setForeground(Color.BLACK);
        JButton showAverageButton = new JButton("Promedio de Ventas");
        showAverageButton.setBackground(new Color(199, 21, 133));
        showAverageButton.setForeground(Color.BLACK);
        JButton detectTrendsButton = new JButton("Detectar Tendencias");
        detectTrendsButton.setBackground(new Color(199, 21, 133));
        detectTrendsButton.setForeground(Color.BLACK);

        salesTextArea = new JTextArea(10, 40);
        salesTextArea.setEditable(false);
        salesTextArea.setBackground(new Color(255, 240, 245));
        salesTextArea.setForeground(Color.BLACK);

        // Añadir los componentes al frame
        frame.add(productLabel);
        frame.add(productComboBox);
        frame.add(channelLabel);
        frame.add(channelComboBox);
        frame.add(quantityLabel);
        frame.add(quantityField);
        frame.add(registerSaleButton);
        frame.add(showAverageButton);
        frame.add(detectTrendsButton);
        frame.add(new JScrollPane(salesTextArea));

        // Acción para registrar ventas
        registerSaleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String productName = (String) productComboBox.getSelectedItem();
                    int channel = channelComboBox.getSelectedIndex();
                    int quantity = Integer.parseInt(quantityField.getText());
                    int day = Integer.parseInt(JOptionPane.showInputDialog(
                            frame, "Ingresa el día (1-30):"));

                    salesData.registerSale(day - 1, productName, channel,
                            quantity);
                    salesTextArea.append("Venta registrada: " + productName
                            + ", Canal: " + (channel == 0 ? "Físico" : "En Línea")
                            + ", Cantidad: " + quantity + ", Día: " + day + "\n");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame,
                            "Por favor ingresa un número válido para la "
                            + "cantidad y el día.", "Error de Entrada",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Acción para calcular el promedio de ventas
        showAverageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String productName = (String) productComboBox.getSelectedItem();
                    int startDay = Integer.parseInt(JOptionPane.showInputDialog(
                            frame, "Ingresa el día de inicio (1-30):"));
                    int endDay = Integer.parseInt(JOptionPane.showInputDialog(
                            frame, "Ingresa el día final (1-30):"));

                    double averageSales = salesAnalysis.calculateAverageSales(
                            productName, startDay - 1, endDay - 1);
                    salesTextArea.append("Promedio de ventas de " + productName
                            + " entre el día " + startDay + " y el día "
                            + endDay + ": " + averageSales + "\n");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Por favor ingresa un "
                            + "número válido para los días.", "Error de Entrada",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Acción para detectar tendencias
        detectTrendsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String productName = (String) productComboBox.getSelectedItem();
                salesTextArea.append("Detectando tendencias para el producto: "
                        + productName + "\n");
                salesAnalysis.detectTrends(productName);
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SaleManagement salesData = new SaleManagement();
        RecursiveSalesAnalysis salesAnalysis = new RecursiveSalesAnalysis(salesData);

        // Iniciar la interfaz gráfica
        new SalesManager(salesData, salesAnalysis);
    }

}
