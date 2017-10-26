package ru.tsc.dao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tsc.model.*;
import org.springframework.context.annotation.DependsOn;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author Terekhin Nikita
 **/

@Repository
@DependsOn(value = "jpaTransactionManager")
public class EmployeeDataManager {
    @PersistenceContext(unitName = "tscPersistentUnit")
    private EntityManager entityManager;

    @Transactional
    public Employee readByLogin(String login) {
        try {
            return entityManager.createQuery("select e from Employee e where lower(e.login) = :login", Employee.class)
                    .setParameter("login", login.toLowerCase())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Employee> getEmployeeList() {
        Query query = entityManager.createQuery("select e from Employee e", Employee.class);
        List<Employee> resultList = query.getResultList();
        return resultList;
    }


    public List<EmployeeHeader> getEmployeeHeaderList() {
        //id, fio, login, position, businessUnit
        Query query = entityManager.createQuery("select e from Employee e", Employee.class);
        List<Employee> resultList = query.getResultList();

        List<EmployeeHeader> result = new ArrayList<>(resultList.size());
        if (resultList != null && resultList.size() > 0) {
            for (Employee employee : resultList) {
                result.add(new EmployeeHeader(employee));
            }
        }
        return result;
    }

    public class EmployeeHeader {
        @Getter @Setter private String id;

        @Getter @Setter private String fio;

        @Getter @Setter private String login;

        @Getter @Setter private String position;

        @Getter @Setter private String businessUnit;

        EmployeeHeader(Employee employee) {
            this.id = employee.getId();
            this.fio = employee.getFio();
            this.login = employee.getLogin();
            this.position = employee.getPosition();
            this.businessUnit = employee.getBusinessUnit();
        }
    }

    @Transactional
    public void persistEmployee(Employee employee) {
        entityManager.persist(employee);
    }

    @Transactional
    public void mergeEmployee(Employee employee) {
        entityManager.merge(employee);
    }

    @Transactional
    public void refreshEmployee(Employee employee) {
        entityManager.refresh(employee);
    }

    @Transactional
    public void addEmployee(Employee employee) {
        entityManager.persist(employee);
    }

    @Transactional
    public void createFromLDAP(HashMap<String,String> attr) {
        Employee employee = new Employee();
        for (String key : attr.keySet()) {
            checkEmployeeFields(key, attr.get(key), employee);
        }
        addEmployee(employee);
    }

    @Transactional
    public void removeProjectExperience(ProjectExperience projectExperience) {
        entityManager.remove(projectExperience);
    }

    @Transactional
    public void removeProjectCompInTech(ProjectCompInTech projectCompInTech) {
        System.out.println("removeProjectCompInTech: "+(projectCompInTech != null ? projectCompInTech.getId() : "null param"));
        //entityManager.remove(projectCompInTech);
        entityManager.createQuery("delete from ProjectCompInTech e " +
                "where e.id = :id")
                .setParameter("id", projectCompInTech.getId())
                .executeUpdate();

    }

    @Transactional
    public void removeEmployeeFuncComp(EmployeeFuncComp employeeFuncComp) {
        entityManager.createQuery("delete from EmployeeFuncComp e " +
                "where e.id = :id")
        .setParameter("id", employeeFuncComp.getId()).executeUpdate();
    }

    @Transactional
    public void removeEmployeeCompInTech(EmployeeCompInTech employeeCompInTech) {
        entityManager.createQuery("delete from EmployeeCompInTech e " +
                "where e.id = :id")
                .setParameter("id", employeeCompInTech.getId()).executeUpdate();
    }

    @Transactional
    public void mergeProjectExp(ProjectExperience experience) {
        entityManager.merge(experience);
    }

    @Transactional
    public void removeProjectExp(ProjectExperience experience) {
        System.out.println("removeProjectExp: "+(experience != null ? experience.getId() : "null param"));
        entityManager.createQuery("delete from ProjectExperience e " +
                "where e.id = :id")
                .setParameter("id", experience.getId())
                .executeUpdate();
    }

    public void addProjectExp(ProjectExperience experience,Employee employee) {
        entityManager.merge(employee);
    }

    private void checkEmployeeFields(String key, String value, Employee employee) {
        if (key.equals("st")) employee.setFio(value);
        if (key.equals("department")) employee.setDepartment(value);
        if (key.equals("title")) employee.setPosition(value);
        if (key.equals("userprincipalname")) employee.setLogin(value.split("@")[0]);
        if (key.equals("st")) employee.setFio(value);
        if (key.equals("depnames")){
            if (value.equals("null")) {
                employee.setBusinessUnit("BU-NAN");
            } else {
                employee.setBusinessUnit(value);
            }
        }
    }

    public boolean isUserExist(String login) {
        return readByLogin(login) == null;
    }
}
