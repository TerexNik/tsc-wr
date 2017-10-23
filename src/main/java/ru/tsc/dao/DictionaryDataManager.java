package ru.tsc.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tsc.model.dictionary.*;
import org.springframework.context.annotation.DependsOn;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@DependsOn(value = "jpaTransactionManager")
public class DictionaryDataManager {

    @PersistenceContext(unitName = "tscPersistentUnit")
    private EntityManager entityManager;

    @Transactional
    public List<Company> getCompanies() {
        try {
            return entityManager.createQuery("select a from Company a", Company.class)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public List<CompetenceInTechnology> getCompetenceInTechnologies() {
        try {
            return entityManager.createQuery("select a from CompetenceInTechnology a", CompetenceInTechnology.class)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public List<Customer> getCustomers() {
        try {
            return entityManager.createQuery("select a from Customer a", Customer.class)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public List<FunctionalCompetence> getFunctionalCompetences() {
        try {
            return entityManager.createQuery("select a from FunctionalCompetence a", FunctionalCompetence.class)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
        public List<Industry> getIndustries() {
            try {
                return entityManager.createQuery("select a from Industry a", Industry.class)
                        .getResultList();
            } catch (NoResultException e) {
                return null;
            }
    }

    @Transactional
        public List<Product> getProducts() {
            try {
                return entityManager.createQuery("select a from Product a", Product.class)
                        .getResultList();
            } catch (NoResultException e) {
                return null;
            }
    }

    @Transactional
        public List<Project> getProjects() {
            try {
                return entityManager.createQuery("select a from Project a", Project.class)
                        .getResultList();
            } catch (NoResultException e) {
                return null;
            }
    }

    @Transactional
        public List<ProjectRole> getProjectRoles() {
            try {
                return entityManager.createQuery("select a from ProjectRole a", ProjectRole.class)
                        .getResultList();
            } catch (NoResultException e) {
                return null;
            }
    }

    @Transactional
    public void mergeProject(Project project) {
        entityManager.merge(project);
    }

    @Transactional
    public void removeProject(Project project) {
        entityManager.remove(project);
    }

    @Transactional
    public void mergeFuncComp(FunctionalCompetence functionalCompetence) {
        entityManager.merge(functionalCompetence);
    }

    @Transactional
    public void mergeCompInTech(CompetenceInTechnology competenceInTechnology) {
        entityManager.merge(competenceInTechnology);
    }

    @Transactional
    public void removeFuncComp(FunctionalCompetence functionalCompetence) {
        entityManager.remove(functionalCompetence);
    }

    @Transactional
    public void removeCompInTech(CompetenceInTechnology competenceInTechnology) {
        entityManager.remove(competenceInTechnology);
    }
}
