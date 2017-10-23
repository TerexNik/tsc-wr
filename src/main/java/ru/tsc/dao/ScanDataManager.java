package ru.tsc.dao;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tsc.model.Scan;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
@DependsOn(value = "jpaTransactionManager")
public class ScanDataManager {

    @PersistenceContext(unitName = "tscPersistentUnit")
    private EntityManager entityManager;

    @Transactional
    public Scan getScan(Long id) {
        try {
            return entityManager.createQuery("select a from Scan a where a.id = :id", Scan.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public void addScan(Scan scan) {
//        entityManager.persist(scan);
        entityManager.merge(scan);
    }

    @Transactional
    public void mergeScan(Scan scan) {
        entityManager.merge(scan);
    }

//    @Transactional
//    public void removeScan(Scan scan) {
//        entityManager.remove(scan);
//    }

}
