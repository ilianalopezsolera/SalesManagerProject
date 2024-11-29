package salesmanager;

import java.util.Date;

/**
 *
 * @author ilico
 */
public class Sale {

    private String product;
    private String channel;
    private int quantity;
    private Date date;

    public Sale(String product, String channel, int quantity, Date date) {
        this.product = product;
        this.channel = channel;
        this.quantity = quantity;
        this.date = date;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Sale" + "product = " + product + ", channel = " + channel +
                ", quantity = " + quantity + ", date = " + date;
    }
    
}
