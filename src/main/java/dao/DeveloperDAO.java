package dao;

import entity.Developer;

import java.sql.*;


/**
 * Created by MYKOLA.GOROKHOV on 02.06.2017.
 */
public class DeveloperDAO implements DAO<Developer> {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/home_work_1?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "english";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }


    public void create(Developer developer) {
        try (Connection c = getConnection()) {
            if (developer.getId() == null) {
                PreparedStatement ps = c.prepareStatement("INSERT INTO `developers` (`developer_name`,`developer_age`, `developer_salary`) VALUES (?,?,?);");

                ps.setString(1, developer.getName());
                ps.setString(2, String.valueOf(developer.getAge()));
                ps.setString(3, String.valueOf(developer.getSalary()));
                ps.execute();
            } else {
                PreparedStatement ps = c.prepareStatement("INSERT INTO `developers` (`id_developers`,`developer_name`,`developer_age`, `developer_salary`) VALUES (?,?,?,?);");
                ps.setString(1, String.valueOf(developer.getId()));
                ps.setString(2, developer.getName());
                ps.setString(3, String.valueOf(developer.getAge()));
                ps.setString(4, String.valueOf(developer.getSalary()));
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Developer read(int id) {
        try (Connection con = getConnection()) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM `developers` WHERE id_developers=" + id);


            while (rs.next()) {
                int id_developers = rs.getInt("id_developers");
                String developer_name = rs.getString("developer_name");
                int developer_age = rs.getInt("developer_age");
                int developer_salary = rs.getInt("developer_salary");

                Developer developer = new Developer(
                        id_developers,
                        developer_name,
                        developer_age,
                        developer_salary);

                return developer;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Developer update(int id, Developer developer) {
        try (Connection con = getConnection()) {
            Developer oldDeveloper = read(id);
            PreparedStatement ps = con.prepareStatement("UPDATE `developers` set developer_name=?,developer_age=?, developer_salary=? WHERE id_developers=?;");

            ps.setString(1, developer.getName());
            ps.setString(2, String.valueOf(developer.getAge()));
            ps.setString(3, String.valueOf(developer.getSalary()));
            ps.setString(4, String.valueOf(developer.getId()));
            ps.execute();

            return read(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try (Connection con = getConnection()) {
            Statement statement = con.createStatement();
            statement.execute("DELETE FROM `developers` WHERE id_developers=" + id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
