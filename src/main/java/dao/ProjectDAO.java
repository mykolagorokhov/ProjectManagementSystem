package dao;

import entity.Project;

import java.sql.*;


/**
 * Created by MYKOLA.GOROKHOV on 02.06.2017.
 */
public class ProjectDAO implements DAO<Project> {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/home_work_1?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "english";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }


    public void create(Project project) {
        try (Connection c = getConnection()) {
            if (project.getId() == null) {
                PreparedStatement ps = c.prepareStatement("INSERT INTO `projects` (`project_name`, `projects_cost`) VALUES (?,?);");

                ps.setString(1, project.getName());
                ps.setString(2, String.valueOf(project.getCost()));
                ps.execute();
            } else {
                PreparedStatement ps = c.prepareStatement("INSERT INTO `projects` (`id_projects`,`project_name`, `projects_cost`) VALUES (?,?,?);");
                ps.setString(1, String.valueOf(project.getId()));
                ps.setString(2, project.getName());
                ps.setString(3, String.valueOf(project.getCost()));

                ps.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Project read(int id) {
        try (Connection con = getConnection()) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM `projects` WHERE id_projects=" + id);


            while (rs.next()) {
                int id_projects = rs.getInt("id_projects");
                String project_name = rs.getString("project_name");
                int project_cost = rs.getInt("projects_cost");
                Project project = new Project(
                        id_projects,
                        project_name,
                        project_cost);

                return project;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Project update(int id, Project project) {
        try (Connection con = getConnection()) {
            Project oldProject = read(id);
            PreparedStatement ps = con.prepareStatement("UPDATE `projects` set project_name=?, projects_cost=? WHERE id_projects=?;");

            ps.setString(1, project.getName());
            ps.setString(2, String.valueOf(project.getCost()));
            ps.setString(3, String.valueOf(project.getId()));
            ps.execute();

            return read(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try (Connection con = getConnection()) {
            Statement statement = con.createStatement();
            statement.execute("DELETE FROM `projects` WHERE id_projects=" + id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
