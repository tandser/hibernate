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
import ru.tandser.hibernate.models.Message;

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

        metadataSources.addAnnotatedClass(Message.class);

        MetadataBuilder metadataBuilder = metadataSources.getMetadataBuilder();

        Metadata metadata = metadataBuilder.build();

        return metadata.buildSessionFactory();
    }

    @Test
    public void test() throws Exception {
        SessionFactory  sessionFactory = createSessionFactory();
        UserTransaction transaction;

        try {
            transaction = transactionManagerSetup.getUserTransaction();
            transaction.begin();
            Session session = sessionFactory.getCurrentSession();
            Message message = new Message("Hello World!");
            session.persist(message);
            transaction.commit();

            transaction = transactionManagerSetup.getUserTransaction();
            transaction.begin();
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
            Root<Message> root = criteriaQuery.from(Message.class);
            criteriaQuery.select(root);
            List<Message> messages = session.createQuery(criteriaQuery).getResultList();
            assertEquals(1, messages.size());
            assertEquals("Hello World!", messages.get(0).getText());
            transaction.commit();
        } finally {
            transactionManagerSetup.rollback();
        }
    }
}