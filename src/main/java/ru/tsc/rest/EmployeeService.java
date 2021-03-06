package ru.tsc.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.tsc.dao.*;
import ru.tsc.model.*;
import ru.tsc.model.dictionary.FunctionalCompetence;
import ru.tsc.util.LdapUtil;

import java.util.*;

import static ru.tsc.util.ResponseUtil.getResponseWithHeaderOk;

/**
 * @author Terekhin Nikita
 **/

@CrossOrigin
@RestController
@RequestMapping(value = "/employee")
public class EmployeeService {

    @Autowired
    private EmployeeDataManager employeeDataManager;

    @RequestMapping(value = "/role", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public Object getEmployeeRoleTest() {
        Map<String, Object> response = getResponseWithHeaderOk();
        response.put("data", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0]);
        return response;
    }

    @RequestMapping(value = "/employeeList", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public Object getEmployeeList() {
        Map<String, Object> response = getResponseWithHeaderOk();
        Map<String, Object> data = new HashMap<>();
        data.put("list", employeeDataManager.getEmployeeHeaderList());
        response.put("data", data);
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public Object getByLogin() {
        return checkUserExistenceAndGenerateResponse(
                SecurityContextHolder.getContext().getAuthentication().getName());
    }


    @RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public Object getUserByLogin(@PathVariable String username) {
        return checkUserExistenceAndGenerateResponse(username);
    }

    private Object checkUserExistenceAndGenerateResponse(String username) {
        if (employeeDataManager.isUserExist(username)) {
            employeeDataManager.createFromLDAP(LdapUtil.getAttributes(username));
        }
        Employee employee = employeeDataManager.readByLogin(username);
        Map<String, Object> response = getResponseWithHeaderOk();
        response.put("employee", employee);

        printJSON(employee, " ### object from JPA");
        return response;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public void updateEmployee(@RequestBody Employee employeeFromUser) {
        printJSON(employeeFromUser, " ### object from UI");
        Employee employeeFromBase = employeeDataManager.readByLogin(
                SecurityContextHolder.getContext().getAuthentication().getName());
        clearEmployeeFields(employeeFromBase);
        employeeDataManager.mergeEmployee(employeeFromUser);
    }

    @Transactional
    void removeProjectExp(List<ProjectExperience> projectExperience) {
        for (ProjectExperience experience : projectExperience) {
            for (ProjectCompInTech projectCompInTech : experience.getCompetenceInTechnology()) {
                employeeDataManager.removeProjectCompInTech(projectCompInTech);
            }
            employeeDataManager.removeProjectExp(experience);
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
