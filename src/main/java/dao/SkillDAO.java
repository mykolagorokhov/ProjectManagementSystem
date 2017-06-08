package dao;

import entity.Skill;

import java.sql.*;


/**
 * Created by MYKOLA.GOROKHOV on 02.06.2017.
 */
public class SkillDAO implements DAO<Skill> {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/home_work_1?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "english";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }


    public void create(Skill skill) {
        try (Connection c = getConnection()) {
            if (skill.getId() == null) {
                PreparedStatement ps = c.prepareStatement("INSERT INTO `skills` (`skill_name`) VALUES (?);");

                ps.setString(1, skill.getName());
                ps.execute();
            } else {
                PreparedStatement ps = c.prepareStatement("INSERT INTO `skills` (`id_skills`,`skill_name`) VALUES (?,?);");
                ps.setString(1, String.valueOf(skill.getId()));
                ps.setString(2, skill.getName());
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Skill read(int id) {
        try (Connection con = getConnection()) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM `skills` WHERE id_skills=" + id);

            while (rs.next()) {
                int id_skills = rs.getInt("id_skills");
                String skill_name = rs.getString("skill_name");
                
                Skill skill = new Skill(
                        id_skills,
                        skill_name);

                return skill;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Skill update(int id, Skill skill) {
        try (Connection con = getConnection()) {
            Skill oldSkill = read(id);
            PreparedStatement ps = con.prepareStatement("UPDATE `skills` set skill_name=? WHERE id_skills=?;");

            ps.setString(1, skill.getName());
            ps.setString(2, String.valueOf(skill.getId()));
            ps.execute();

            return read(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try (Connection con = getConnection()) {
            Statement statement = con.createStatement();
            statement.execute("DELETE FROM `skills` WHERE id_skills=" + id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
