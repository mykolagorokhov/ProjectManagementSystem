package dao;

import entity.Company;

import java.sql.*;


/**
 * Created by MYKOLA.GOROKHOV on 02.06.2017.
 */
public class CompanyDAO implements DAO<Company> {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/home_work_1?useSSL=false";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "english";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }


    public void create(Company companie) {
        try (Connection c = getConnection()) {
            if (companie.getId() == null) {
                PreparedStatement ps = c.prepareStatement("INSERT INTO `companies` (`companie_name`) VALUES (?);");

                ps.setString(1, companie.getName());
                ps.execute();
            } else {
                PreparedStatement ps = c.prepareStatement("INSERT INTO `companies` (`id_companies`,`companie_name`) VALUES (?,?);");
                ps.setString(1, String.valueOf(companie.getId()));
                ps.setString(2, companie.getName());
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Company read(int id) {
        try (Connection con = getConnection()) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM `companies` WHERE id_companies=" + id);


            while (rs.next()) {
                int id_companies = rs.getInt("id_companies");
                String companie_name = rs.getString("companie_name");
                
                Company companie = new Company(
                        id_companies,
                        companie_name);

                return companie;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Company update(int id, Company companie) {
        try (Connection con = getConnection()) {
            Company oldCompanie = read(id);
            PreparedStatement ps = con.prepareStatement("UPDATE `companies` set companie_name=? WHERE id_companies=?;");

            ps.setString(1, companie.getName());
            ps.setString(2, String.valueOf(companie.getId()));
            ps.execute();

            return read(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try (Connection con = getConnection()) {
            Statement statement = con.createStatement();
            statement.execute("DELETE FROM `companies` WHERE id_companies=" + id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
