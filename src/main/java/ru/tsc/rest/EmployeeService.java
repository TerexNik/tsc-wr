package ru.tsc.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unboundid.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.tsc.dao.*;
import ru.tsc.model.*;
import ru.tsc.util.LdapUtil;

import java.util.*;

import static ru.tsc.util.ResponseUtill.getResponseWithHeaderOk;

/**
 * @author Terekhin Nikita
 **/

@RestController
@RequestMapping(value = "/employee")
public class EmployeeService {

    @Autowired
    private EmployeeDataManager employeeDataManager;

    @RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public Object getByLogin(@PathVariable String username) {
        if (employeeDataManager.isUserExist(username)) {
            employeeDataManager.createFromLDAP(LdapUtil.getAttributes(username));
        }
        Employee employee = employeeDataManager.readByLogin(username);
        Map<String, Object> response = getResponseWithHeaderOk();
        response.put("employee", employee);

        printJSON(employee, " ### object from JPA");

        return response;
    }

    @RequestMapping(value = "/{username}/update", method = RequestMethod.PUT)
    public void updateEmployee(@PathVariable String username, @RequestBody Employee employeeFromUser) {
        printJSON(employeeFromUser, " ### object from UI");
        Employee employeeFromBase = employeeDataManager.readByLogin(username);
        clearEmployeeFields(employeeFromBase);
        employeeDataManager.mergeEmployee(employeeFromUser);
    }

    /*private void clearProjectExp(Employee employeeFromUser, List<ProjectExperience> experiencesTMP) {
        experiencesTMP.addAll(employeeFromUser.getProjectExperience());
        employeeFromUser.setProjectExperience(new LinkedList<ProjectExperience>());
    }*/

    @Transactional
    void removeProjectExp(List<ProjectExperience> projectExperience) {
        for (ProjectExperience experience : projectExperience) {
            for (ProjectCompInTech projectCompInTech : experience.getCompetenceInTechnology()) {
                employeeDataManager.removeProjectCompInTech(projectCompInTech);
                //projectCompInTech.setId(0L);
            }
            employeeDataManager.removeProjectExp(experience);
            //experience.setId(0L);
        }
    }


    private void clearEmployeeFields(Employee employee) {
        removeEmployeeFuncComp(employee.getEmployeeFuncComp());
        removeEmployeeCompInTech(employee.getEmployeeCompInTech());
        removeProjectExp(employee.getProjectExperience());
    }

    @Transactional
    void removeEmployeeCompInTech(List<EmployeeCompInTech> employeeCompInTeches) {
        for (EmployeeCompInTech employeeCompInTech: employeeCompInTeches) {
            employeeDataManager.removeEmployeeCompInTech(employeeCompInTech);
        }
    }

    @Transactional
    void removeEmployeeFuncComp(List<EmployeeFuncComp> employeeFuncComps) {
        for (EmployeeFuncComp employeeFuncComp: employeeFuncComps) {
            employeeDataManager.removeEmployeeFuncComp(employeeFuncComp);
        }
    }

    private void printJSON(Object request, String header) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.out.println(header);
            String json = objectMapper.writeValueAsString(request);
            System.out.println(json);
        } catch (Exception e ) {
            System.out.println("Error printing JSON");
        }
    }
}
