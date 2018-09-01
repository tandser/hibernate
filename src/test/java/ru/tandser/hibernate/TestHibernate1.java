package ru.tandser.hibernate;

import org.junit.Test;
import ru.tandser.hibernate.models.Message;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestHibernate1 extends AbstractTestHibernate {

    @Test
    public void test() throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("HelloWorldPU");
        UserTransaction      transaction;
        EntityManager        entityManager;

        try {
            transaction = transactionManagerSetup.getUserTransaction();
            transaction.begin();
            entityManager = entityManagerFactory.createEntityManager();
            Message message = new Message("Hello World!");
            entityManager.persist(message);
            transaction.commit();
            entityManager.close();

            transaction = transactionManagerSetup.getUserTransaction();
            transaction.begin();
            entityManager = entityManagerFactory.createEntityManager();
            List<Message> messages = entityManager.createQuery("SELECT m FROM Message m").getResultList();
            assertEquals(1, messages.size());
            assertEquals("Hello World!", messages.get(0).getText());
            messages.get(0).setText("Take me to your leader!");
            transaction.commit();
            entityManager.close();
        } finally {
            transactionManagerSetup.rollback();
            entityManagerFactory.close();
        }
    }
}