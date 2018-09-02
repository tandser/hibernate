package ru.tandser.hibernate;

import org.junit.Test;
import ru.tandser.hibernate.models.Details;
import ru.tandser.hibernate.models.DetailsA;
import ru.tandser.hibernate.models.DetailsB;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

public class DetailsTest extends AbstractTestHibernate {

    @Test
    public void test() throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("HelloWorldPU");
        UserTransaction      transaction;
        EntityManager        entityManager;

        try {
            transaction = transactionManagerSetup.getUserTransaction();
            transaction.begin();
            entityManager = entityManagerFactory.createEntityManager();
            Details detailsA = new DetailsA("info", "comment", "details");
            entityManager.persist(detailsA);
            transaction.commit();
            entityManager.close();

            transaction = transactionManagerSetup.getUserTransaction();
            transaction.begin();
            entityManager = entityManagerFactory.createEntityManager();
            Details detailsB = new DetailsB("info", "comment", "details");
            entityManager.persist(detailsB);
            transaction.commit();
            entityManager.close();
        } finally {
            transactionManagerSetup.rollback();
            entityManagerFactory.close();
        }
    }
}