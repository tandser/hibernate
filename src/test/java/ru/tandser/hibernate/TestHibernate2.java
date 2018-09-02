package ru.tandser.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.resource.transaction.backend.jta.internal.JtaTransactionCoordinatorBuilderImpl;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;
import ru.tandser.hibernate.models.MessageA;
import ru.tandser.hibernate.models.MessageB;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestHibernate2 extends AbstractTestHibernate {

    private static SessionFactory createSessionFactory() {
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();

        serviceRegistryBuilder.applySetting("hibernate.connection.datasource", "DataSource")
                              .applySetting("hibernate.format_sql",            "true")
                              .applySetting("hibernate.use_sql_comments",      "true")
                              .applySetting("hibernate.hbm2ddl.auto",          "create-drop");

        serviceRegistryBuilder.applySetting(Environment.TRANSACTION_COORDINATOR_STRATEGY,
                JtaTransactionCoordinatorBuilderImpl.class);

        ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);

        metadataSources.addAnnotatedClass(MessageA.class);
        metadataSources.addAnnotatedClass(MessageB.class);

        MetadataBuilder metadataBuilder = metadataSources.getMetadataBuilder();

        Metadata metadata = metadataBuilder.build();

        return metadata.buildSessionFactory();
    }

    @Test
    public void test() throws Exception {
        SessionFactory  sessionFactory = createSessionFactory();
        UserTransaction transaction;
        Session         session;

        try {

            // add messageA

            transaction = transactionManagerSetup.getUserTransaction();
            transaction.begin();
            session = sessionFactory.getCurrentSession();
            MessageA messageA = new MessageA("A: Hello World!");
            session.persist(messageA);
            transaction.commit();

            // add messageB

            transaction = transactionManagerSetup.getUserTransaction();
            transaction.begin();
            session = sessionFactory.getCurrentSession();
            MessageB messageB = new MessageB("B: Hello World!");
            session.persist(messageB);
            transaction.commit();

            transaction = transactionManagerSetup.getUserTransaction();
            transaction.begin();
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<MessageA> criteriaQueryA = criteriaBuilder.createQuery(MessageA.class);
            CriteriaQuery<MessageB> criteriaQueryB = criteriaBuilder.createQuery(MessageB.class);
            Root<MessageA> rootA = criteriaQueryA.from(MessageA.class);
            Root<MessageB> rootB = criteriaQueryB.from(MessageB.class);
            criteriaQueryA.select(rootA);
            criteriaQueryB.select(rootB);
            List<MessageA> messagesA = session.createQuery(criteriaQueryA).getResultList();
            List<MessageB> messagesB = session.createQuery(criteriaQueryB).getResultList();
            assertEquals(1, messagesA.size());
            assertEquals(1, messagesB.size());
            assertEquals("A: Hello World!", messagesA.get(0).getText());
            assertEquals("B: Hello World!", messagesB.get(0).getText());
            transaction.commit();
        } finally {
            transactionManagerSetup.rollback();
        }
    }
}