public class Items {
    private char item_code;
    private char description;
    private double price;
    private int item_id;
    private int inventory_amount;

    public Items(char item_code, char description, double price, int item_id, int inventory_amount) {
        this.item_code = item_code;
        this.description = description;
        this.price = price;
        this.item_id = item_id;
        this.inventory_amount = inventory_amount;
    }


	public char getItemCode() {
        return item_code;
    }

    public char getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getItemId() {
        return item_id;
    }
    
    public int getInventoryAmount() {
    	return inventory_amount;
    }

    public String toString(){
        return String.format("(%s, %s, %s, %s)", item_code, description, price, item_id, inventory_amount);
    }



}
