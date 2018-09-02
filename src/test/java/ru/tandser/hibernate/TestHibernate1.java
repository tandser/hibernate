package ru.tandser.hibernate;

import org.junit.Test;
import ru.tandser.hibernate.models.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestHibernate1 extends AbstractTestHibernate {

    @Test
    public void test() throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("HelloWorldPU");
        UserTransaction      transaction;
        EntityManager        entityManager;

        try {

            // add messageA

            transaction = transactionManagerSetup.getUserTransaction();
            transaction.begin();
            entityManager = entityManagerFactory.createEntityManager();
            MessageA messageA = new MessageA("A: Hello World!");
            messageA.setMessageType(MessageType.LETTER);
            messageA.setAddress(new Address(new City("123456", "city"), "street"));
            messageA.setAmount(new Amount(BigDecimal.ZERO, Currency.getInstance("RUB")));
            CardA cardA = new CardA("cardA");
            messageA.getCardsA().add(cardA);
            messageA.getImagesA().add("image");
            cardA.setMessageA(messageA);
            entityManager.persist(cardA);
            entityManager.persist(messageA);
            transaction.commit();
            entityManager.close();

            // add messageB

            transaction = transactionManagerSetup.getUserTransaction();
            transaction.begin();
            entityManager = entityManagerFactory.createEntityManager();
            MessageB messageB = new MessageB("B: Hello World!");
            messageB.setMessageType(MessageType.LETTER);
            messageB.setAddress(new Address(new City("123456", "city"), "street"));
            messageB.setAmount(new Amount(BigDecimal.ZERO, Currency.getInstance("RUB")));
            CardB cardB = new CardB("cardB");
            messageB.getCardsB().add(cardB);
            messageB.getImagesB().add("image");
            cardB.setMessageB(messageB);
            entityManager.persist(cardB);
            entityManager.persist(messageB);
            transaction.commit();
            entityManager.close();

            transaction = transactionManagerSetup.getUserTransaction();
            transaction.begin();
            entityManager = entityManagerFactory.createEntityManager();
            List<MessageA> messagesA = entityManager.createQuery("SELECT m FROM MessageA m").getResultList();
            List<MessageB> messagesB = entityManager.createQuery("SELECT m FROM MessageB m").getResultList();
            assertEquals(1, messagesA.size());
            assertEquals(1, messagesB.size());
            assertEquals("A: Hello World!", messagesA.get(0).getText());
            assertEquals("B: Hello World!", messagesB.get(0).getText());
            messagesA.get(0).setText("A: Take me to your leader!");
            messagesB.get(0).setText("B: Take me to your leader!");
            transaction.commit();
            entityManager.close();
        } finally {
            transactionManagerSetup.rollback();
            entityManagerFactory.close();
        }
    }
}