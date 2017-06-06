package dao;

import entity.*;

import java.sql.*;

/**
 * Created by MYKOLA.GOROKHOV on 03.06.2017.
 */
public class ForeignKeyDAO implements DAO<ForeignKey> {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/home_work_1?useSSL=false";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "english";

    public ForeignKeyDAO(String tableName) {
        this.tableName = tableName;
    }

    private String tableName;

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    private String getTableColumName(int i) {
        try (Connection c = getConnection()) {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM " + tableName);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            return metaData.getColumnName(i);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void create(ForeignKey foreignKey) {
        try (Connection c = getConnection()) {

            // PreparedStatement почему-то ставит неправельные кавычки ????
//            PreparedStatement ps = c.prepareStatement("INSERT INTO ? (?,?) VALUES (?, ?);");
//
//            ps.setString(1, tableName);
//            ps.setString(2, getTableColumName(1));
//            ps.setString(3, getTableColumName(2));
//            ps.setInt(4, foreignKey.getFirstColum());
//            ps.setInt(5, foreignKey.getSecondColum());



            PreparedStatement ps = c.prepareStatement("INSERT INTO `" +
                    tableName +
                    "` (`" + getTableColumName(1) +
                    "`, `" + getTableColumName(2) +
                    "`) VALUES (?, ?);");

            ps.setInt(1, foreignKey.getFirstColum());
            ps.setInt(2, foreignKey.getSecondColum());
//            System.out.println(ps.toString());
//            System.out.println(tableName);
//            System.out.println(getTableColumName(1));
//            System.out.println(getTableColumName(2));
            ps.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ForeignKey read(int id) {
        try (Connection con = getConnection()) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM `" + tableName + "` WHERE " + getTableColumName(1) + "=" + id);

            while (rs.next()) {
                int firstColum = rs.getInt(getTableColumName(1));
                int secondColum = rs.getInt(getTableColumName(2));
                return new ForeignKey(firstColum, secondColum);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public ForeignKey update(int id, ForeignKey foreignKey) {
        try (Connection con = getConnection()) {
//            ForeignKey oldForignKey = read(id);
            PreparedStatement ps = con.prepareStatement("UPDATE `" + tableName + "` set " + getTableColumName(2) + "=? WHERE " + getTableColumName(1) + "=?;");

            ps.setString(1, String.valueOf(foreignKey.getSecondColum()));
            ps.setString(2, String.valueOf(foreignKey.getFirstColum()));
            ps.execute();

            return read(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void delete(int id) {
        try (Connection con = getConnection()) {
            Statement statement = con.createStatement();
            statement.execute("DELETE FROM `" + tableName + "` WHERE " + getTableColumName(1) + "=" + id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
