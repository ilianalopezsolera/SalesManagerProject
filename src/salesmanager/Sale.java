package salesmanager;

/**
 * Class for sale. It contains attributes and constructors.
 *
 * @author Meylin Lopez
 * @author Yileidy Granados
 */
public class Sale {

    private String productName;
    private int quantity;
    private int day;
    private int channel;

    public Sale(String productName, int quantity, int day, int channel) {
        this.productName = productName;
        this.quantity = quantity;
        this.day = day;
        this.channel = channel;
    }

    /**
     * This method gets the name of the product
     *
     * @return
     */
    public String getProductName() {
        return productName;
    }

    /**
     * This method allows to change the name of a product
     *
     * @param productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * This method gets the quantity of sales of a product
     *
     * @return
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * This method allows to change the quantity of sales of a product
     *
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * This method gets the day of the month in which the sale was made
     *
     * @return
     */
    public int getDay() {
        return day;
    }

    /**
     * This method allows to change the quantity of sales of a product
     *
     * @param day
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * This method gets the type of channel in which the sale was made
     *
     * @return
     */
    public int getChannel() {
        return channel;
    }

    /**
     * This method allows to change the type of channel of a product
     *
     * @param channel
     */
    public void setChannel(int channel) {
        this.channel = channel;
    }

}
