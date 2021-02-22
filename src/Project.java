import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.Timestamp;

public class Project {

    public static Items createItems(char item_code, char description, double price, int item_id, int inventory_amount) throws SQLException {
        Connection connection = null;
        Items item = new Items(item_code, description, price, item_id, inventory_amount);

        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        String sql = String.format("INSERT INTO cars (item_code, description, price, item_id, inventory_amount) VALUES ('%s' , '%s', %s, %s);",
                item.getItemCode(),
                item.getDescription(),
                item.getPrice(),
                item.getItemId(),
                item.getInventoryAmount());
        sqlStatement.executeUpdate(sql);
        connection.close();

        return item;
    }

    public static Items createItemsUsingStoredProcedure(char item_code, char description, double price, int item_id, int inventory_amount)
            throws SQLException {

        Connection connection = null;
        Items item = new Items(item_code, description, price, item_id, inventory_amount);


        connection = MySqlDatabase.getDatabaseConnection();
        String sql = "CALL create_item(?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setFloat(1, item_code);
        preparedStatement.setFloat(2, description);
        preparedStatement.setDouble(3, price);
        preparedStatement.setInt(4, item_id);
        preparedStatement.setInt(5,  inventory_amount);

        preparedStatement.execute();
        connection.close();

        return item;
    }

    public static Orders createOrders(int order_id, char item_code, int quantity, Timestamp order_timestamp) throws SQLException {
        Connection connection = null;


        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        String sql = String.format("INSERT INTO orders (order_id, item_code, quantity) VALUES ('%s' , '%s' , '%s' , '%s');",
                order_id, item_code, quantity);

        sqlStatement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

        ResultSet resultSet = sqlStatement.getGeneratedKeys();
        resultSet.next();


        return new Orders(order_id , item_code, quantity, order_timestamp);


    }

    public static void updateOrders(int order_id, char item_code, int quantity, Timestamp order_timestamp) throws SQLException {
        Connection connection = null;

        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        String sql = String.format("UPDATE orders SET order_id = '%s', item_code = '%s' WHERE quantity = %s;",
        		order_id, item_code, quantity);

        sqlStatement.executeUpdate(sql);

        connection.close();
    }

    public static void deleteOrder(int order_id) throws SQLException {
        Connection connection = null;

        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        String sql = String.format("DELETE FROM orders WHERE order_id = %s;", order_id);
        sqlStatement.executeUpdate(sql);
        connection.close();
    }

    public static List<Items> getAllItems() throws SQLException {
        Connection connection = null;


        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        String sql = "SELECT * FROM items;";
        ResultSet resultSet = sqlStatement.executeQuery(sql);

        List<Items> items = new ArrayList<Items>();

        while (resultSet.next()) {
            char item_code = (char) resultSet.getFloat(1);
            char description = (char) resultSet.getFloat(2);
            double price = resultSet.getDouble(3);
            int item_id = resultSet.getInt(4);
            int inventory_amount = resultSet.getInt(5);
            

            Items item = new Items(item_code, description, price, item_id, inventory_amount);
            items.add(item);
        }
        resultSet.close();
        connection.close();
        return items;

    }

    public static void attemptToListItems() {
        try {
            List<Items> items = getAllItems();
            for (Items item : items) {
                System.out.println(item.toString());
            }
        } catch (SQLException sqlException) {
            System.out.println("Failed to get items");
            System.out.println(sqlException.getMessage());
        }
    }

    public static void attemptToCreateNewItem(char item_code, char description, double price, int item_id, int inventory_amount) {
        try {
            Items item = createItems(item_code, description, price, item_id, inventory_amount);
            System.out.println(item.toString());
        } catch (SQLException sqlException) {
            System.out.println("Failed to create item");
            System.out.println(sqlException.getMessage());
        }
    }

    public static void attemptToCreateNewItemUsingSP(char item_code, char description, double price, int item_id, int inventory_amount) {
        try {
            createItemsUsingStoredProcedure(item_code, description, price, item_id, inventory_amount);
        } catch (SQLException sqlException) {
            System.out.println("Failed to create item");
            System.out.println(sqlException.getMessage());
        }
    }

    public static void attemptToCreateNewOrder(int order_id, char item_code, int quantity, Timestamp order_timestamp) {
        try {
            Orders order = createOrders(order_id, item_code, quantity);
            System.out.println(order.toString());
        } catch (SQLException sqlException) {
            System.out.println("Failed to create order");
            System.out.println(sqlException.getMessage());
        }

    }

    public static void attemptToUpdateOrder(int order_id, char item_code, int quantity, Timestamp order_timestamp) {
        try {
            updateOrders(order_id , item_code , quantity);
        } catch (SQLException sqlException) {
            System.out.println("Failed to update order");
            System.out.println(sqlException.getMessage());
        }

    }

    public static void attemptToDeleteOrder(int order_id) {
        try {
            deleteOrder(order_id);
        } catch (SQLException sqlException) {
            System.out.println("Failed to delete order");
            System.out.println(sqlException.getMessage());
        }
    }

    public static void main(String[] args){

        if (args[0].equals("ListItems")) {
            attemptToListItems();
        } else if (args[0].equals("CreateItem")) {
            String item_code = args[1];
            String description = args[2];
            double price = Double.parseDouble(args[3]);
            int item_id = Integer.parseInt(args[4]);
            int inventory_amount = Integer.parseInt(args[5]);
            attemptToCreateNewItem(item_code, description, price, item_id, inventory_amount);
        } else if (args[0].equals("CreateItemSP")) {
            String item_code = args[1];
            String description = args[2];
            double price = Double.parseDouble(args[3]);
            int item_id = Integer.parseInt(args[4]);
            int inventory_amount = Integer.parseInt(args[5]);
            attemptToCreateNewItemUsingSP(item_code, description, price, item_id, inventory_amount);
        } else if (args[0].equals("CreateOrder")) {
            int order_id = Integer.parseInt(args[1]);
            String item_code = args[2];
            int quantity = Integer.parseInt(args[3]);
            attemptToCreateNewOrder(order_id, item_code, quantity);
        } else if (args[0].equals("UpdateOrder")) {
        	int order_id = Integer.parseInt(args[1]);
            String item_code = args[2];
            int quantity = Integer.parseInt(args[3]);
            attemptToUpdateOrder(order_id, item_code, quantity);
        } else if (args[0].equals("DeleteOrder")) {
            int order_id = Integer.parseInt(args[1]);
            attemptToDeleteOrder(order_id);
        }
    }

}
