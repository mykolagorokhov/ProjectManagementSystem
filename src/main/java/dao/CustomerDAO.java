package dao;

import entity.Customer;

import java.sql.*;


/**
 * Created by MYKOLA.GOROKHOV on 02.06.2017.
 */
public class CustomerDAO implements DAO<Customer> {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/home_work_1?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    //  Database credentials
    static final String USER = "root";
    static final String PASS = "english";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }


    public void create(Customer customer) {
        try (Connection c = getConnection()) {
            if (customer.getId() == null) {
                PreparedStatement ps = c.prepareStatement("INSERT INTO `customers` (`customer_name`) VALUES (?);");

                ps.setString(1, customer.getName());
                ps.execute();
            } else {
                PreparedStatement ps = c.prepareStatement("INSERT INTO `customers` (`id_customers`,`customer_name`) VALUES (?,?);");
                ps.setString(1, String.valueOf(customer.getId()));
                ps.setString(2, customer.getName());
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Customer read(int id) {
        try (Connection con = getConnection()) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM `customers` WHERE id_customers=" + id);


            while (rs.next()) {
                int id_customers = rs.getInt("id_customers");
                String customer_name = rs.getString("customer_name");
                
                Customer customer = new Customer(
                        id_customers,
                        customer_name);

                return customer;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Customer update(int id, Customer customer) {
        try (Connection con = getConnection()) {
            Customer oldCustomer = read(id);
            PreparedStatement ps = con.prepareStatement("UPDATE `customers` set customer_name=? WHERE id_customers=?;");

            ps.setString(1, customer.getName());
            ps.setString(2, String.valueOf(customer.getId()));
            ps.execute();

            return read(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try (Connection con = getConnection()) {
            Statement statement = con.createStatement();
            statement.execute("DELETE FROM `customers` WHERE id_customers=" + id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
