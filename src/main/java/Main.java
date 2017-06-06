import dao.*;
import entity.*;

/**
 * Created by MYKOLA.GOROKHOV on 02.06.2017.
 */
public class Main {
    public static void main(String[] args) {
        DeveloperDAO developerDAO = new DeveloperDAO();
//        developerDAO.create(new Developer("dd", 20, 2000));
//
//        developerDAO.delete(17);
//        developerDAO.create(new Developer(17, "dd", 20, 2000));
//        System.out.println(developerDAO.read(17).toString());
//
//        System.out.println(developerDAO.update(17, new Developer(17, "dd", 30, 5000)).toString());
//
//-----------------------------------------------------------------------------
        SkillDAO skillDAO = new SkillDAO();
//        skillDAO.create(new Skill("asm"));
//
//        skillDAO.delete(15);
//        skillDAO.create(new Skill(15, "make coffee"));
//        System.out.println(skillDAO.read(15).toString());
//
//        System.out.println(skillDAO.update(15, new Skill(15, "Pascal")).toString());
//-----------------------------------------------------------------------------
        CompanyDAO companyDAO = new CompanyDAO();
//        companyDAO.create(new Company("Company3"));
//
//        companyDAO.delete(15);
//        companyDAO.create(new Company(15, "Company15"));
//        System.out.println(companyDAO.read(15).toString());
//
//        System.out.println(companyDAO.update(15, new Company(15, "Company222")).toString());
//-----------------------------------------------------------------------------
        CustomerDAO customerDAO = new CustomerDAO();
//        customerDAO.create(new Customer("Customer3"));
//
//        customerDAO.delete(15);
//        customerDAO.create(new Customer(15, "Customer15"));
//        System.out.println(customerDAO.read(15).toString());
//
//        System.out.println(customerDAO.update(15, new Customer(15, "Customer22")).toString());
//-----------------------------------------------------------------------------
        ProjectDAO projectDAO = new ProjectDAO();
//        projectDAO.create(new Project("Project3",50000));
//
//        projectDAO.delete(15);
//        projectDAO.create(new Project(15, "Project15",60000));
//        System.out.println(projectDAO.read(15).toString());
//
//        System.out.println(projectDAO.update(15, new Project(15, "Project22",20000)).toString());
//-----------------------------------------------------------------------------
// Пример: Создать разработчика, добавить ему навыки. Создать проект, и добавить в данный проект разработчиков.
// Разрешается использовать все возможности JDBC

        ForeignKeyDAO developersHasSkillsDAO = new ForeignKeyDAO("developers_has_skills");
        ForeignKeyDAO companiesHasDevelopersDAO = new ForeignKeyDAO("companies_has_developers");
        ForeignKeyDAO companiesHasProjectsDAO = new ForeignKeyDAO("companies_has_projects");
        ForeignKeyDAO projectsHasCustomersDAO = new ForeignKeyDAO("projects_has_customers");
        ForeignKeyDAO projectsHasDeveloperDAO = new ForeignKeyDAO("projects_has_developers");


//        Создать разработчика
        developerDAO.create(new Developer(33, "ResultDevel0per", 33, 33333));
//        добавить ему навыки.
        developersHasSkillsDAO.create(new ForeignKey(33, 1));
        developersHasSkillsDAO.create(new ForeignKey(33, 2));
        developersHasSkillsDAO.create(new ForeignKey(33, 3));

//        Создать проект
        projectDAO.create(new Project(33, "ResultPr0ject", 100000));
//        и добавить в данный проект разработчиков.
        projectsHasDeveloperDAO.create(new ForeignKey(33, 1));
        projectsHasDeveloperDAO.create(new ForeignKey(33, 2));
        projectsHasDeveloperDAO.create(new ForeignKey(33, 33));

    }
}
