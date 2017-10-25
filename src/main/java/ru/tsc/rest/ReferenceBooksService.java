package ru.tsc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tsc.dao.DictionaryDataManager;
import ru.tsc.model.dictionary.CompetenceInTechnology;
import ru.tsc.model.dictionary.FunctionalCompetence;
import ru.tsc.model.dictionary.Project;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static ru.tsc.util.ResponseUtil.getResponseWithHeaderOk;

@CrossOrigin
@RestController
@RequestMapping(value = "/reference")
public class ReferenceBooksService {

    @Autowired
    DictionaryDataManager dictionaryDataManager;

    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public Map<String, Object> readProject() {
        List projects = dictionaryDataManager.getProjects();
        Map<String, Object> response = getResponseWithHeaderOk();
        response.put("project", projects);
        return response;
    }

    @RequestMapping(value = "/project", method = RequestMethod.PUT)
    public void addOrUpdateProject(@RequestBody Project project) {
        dictionaryDataManager.mergeProject(project);
    }

    @RequestMapping(value = "/project/delete")
    public void removeProject(@RequestBody Project project) {
        dictionaryDataManager.removeProject(project);
    }

    @RequestMapping(value = "/functional-competence", method = RequestMethod.GET)
    public Map<String, Object> readFuncComp() {
        List functionalCompetences = dictionaryDataManager.getFunctionalCompetences();
        Map<String, Object> response = getResponseWithHeaderOk();
        response.put("functionalCompetence", functionalCompetences);
        return response;
    }


    @RequestMapping(value = "/functional-competence", method = RequestMethod.PUT)
    public void addOrUpdateFuncComp(@RequestBody FunctionalCompetence functionalCompetence) {
        dictionaryDataManager.mergeFuncComp(functionalCompetence);
    }

    @RequestMapping(value = "/functional-competence/delete", method = RequestMethod.PUT)
    public void removeFuncComp(@RequestBody FunctionalCompetence functionalCompetence) {
        dictionaryDataManager.removeFuncComp(functionalCompetence);
    }

    @RequestMapping(value = "/comp-in-tech", method = RequestMethod.GET)
    public Map<String, Object> readCompInTech() {
        List competenceInTechnologies = dictionaryDataManager.getCompetenceInTechnologies();
        Map<String, Object> response = getResponseWithHeaderOk();
        response.put("competenceInTechnology", competenceInTechnologies);
        return response;
    }


    @RequestMapping(value = "/comp-in-tech", method = RequestMethod.PUT)
    public void addOrUpdateFuncComp(@RequestBody CompetenceInTechnology competenceInTechnology) {
        dictionaryDataManager.mergeCompInTech(competenceInTechnology);
    }

    @RequestMapping(value = "/comp-in-tech/delete", method = RequestMethod.PUT)
    public void removeFuncComp(@RequestBody CompetenceInTechnology competenceInTechnology) {
        dictionaryDataManager.removeCompInTech(competenceInTechnology);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public Object getAllReferences() {
        Map<String, Object> response = getResponseWithHeaderOk();
        Map<String, List> dictionaries = getDictionaries();
        response.put("dictionaries", dictionaries);
        return response;
    }

    private Map<String, List> getDictionaries() {
        Map<String, List> dictionaries = new LinkedHashMap<>();
        dictionaries.put("company", dictionaryDataManager.getCompanies());
        dictionaries.put("competenceInTechnology", dictionaryDataManager.getCompetenceInTechnologies());
        dictionaries.put("customer", dictionaryDataManager.getCustomers());
        dictionaries.put("functionalCompetence", dictionaryDataManager.getFunctionalCompetences());
        dictionaries.put("industry", dictionaryDataManager.getIndustries());
        dictionaries.put("product", dictionaryDataManager.getProducts());
        dictionaries.put("project", dictionaryDataManager.getProjects());
        dictionaries.put("projectRole", dictionaryDataManager.getProjectRoles());
        return dictionaries;
    }
}
