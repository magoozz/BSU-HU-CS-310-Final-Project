import java.sql.Timestamp;

public class Orders {
    private int order_id;
    private char item_code;
    private int quantity;
    private Timestamp order_timestamp;

    public Orders(int order_id, char item_code, int quantity, Timestamp order_timestamp) {
        this.order_id = order_id;
        this.item_code = item_code;
        this.quantity = quantity;
        this.order_timestamp = order_timestamp;
    }

    public String toString(){
        return String.format("(%s, %s, %s)", this.order_id, this.item_code, this.quantity, this.order_timestamp);
    }

    public int getOrderId() {
        return order_id;
    }
    
    public void setOrderId(int order_id) {
        this.order_id = order_id;
    }

    public char getItemCode() {
        return item_code;
    }
    
    public void setItemCode(char item_code) {
        this.item_code = item_code;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public Timestamp getOrderTimestamp() {
        return order_timestamp;
    }

    public void setOrderTimestamp(Timestamp order_timestamp) {
        this.order_timestamp = order_timestamp;
    }
}