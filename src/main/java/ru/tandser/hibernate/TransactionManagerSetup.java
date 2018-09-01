package ru.tandser.hibernate;

import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.jdbc.PoolingDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

public class TransactionManagerSetup {

    public static final String DATASOURCE_NAME = "DataSource";

    private static final Logger logger = LoggerFactory.getLogger(TransactionManagerSetup.class);

    protected Context           context;
    protected PoolingDataSource dataSource;
    public    DatabaseProduct   databaseProduct;

    {
        context = new InitialContext();
    }

    public TransactionManagerSetup(DatabaseProduct databaseProduct) throws Exception {
        this(databaseProduct, null);
    }

    public TransactionManagerSetup(DatabaseProduct databaseProduct, String connectionUrl) throws Exception {
        logger.debug("Starting database connection pool");

        TransactionManagerServices.getConfiguration().setServerId("myServer");
        TransactionManagerServices.getConfiguration().setDisableJmx(true);
        TransactionManagerServices.getConfiguration().setJournal("null");
        TransactionManagerServices.getConfiguration().setWarnAboutZeroResourceTransaction(false);

        dataSource = new PoolingDataSource();

        dataSource.setUniqueName(DATASOURCE_NAME);
        dataSource.setMinPoolSize(1);
        dataSource.setMaxPoolSize(5);
        dataSource.setPreparedStatementCacheSize(10);
        dataSource.setIsolationLevel("READ_COMMITTED");
        dataSource.setAllowLocalTransactions(true);

        this.databaseProduct = databaseProduct;

        databaseProduct.configuration.configure(dataSource, connectionUrl);

        dataSource.init();
    }

    public Context getNamingContext() {
        return context;
    }

    public UserTransaction getUserTransaction() {
        try {
            return (UserTransaction) getNamingContext().lookup("java:comp/UserTransaction");
        } catch (NamingException exc) {
            throw new RuntimeException(exc);
        }
    }

    public DataSource getDataSource() {
        try {
            return (DataSource) getNamingContext().lookup(DATASOURCE_NAME);
        } catch (NamingException exc) {
            throw new RuntimeException(exc);
        }
    }

    public void rollback() {
        UserTransaction transaction = getUserTransaction();

        try {
            if (transaction.getStatus() == Status.STATUS_ACTIVE ||
                    transaction.getStatus() == Status.STATUS_MARKED_ROLLBACK) {

                transaction.rollback();
            }
        } catch (SystemException exc) {
            logger.error("Rollback of transaction failed, trace follows!", exc);
        }
    }

    public void stop() {
        logger.info("Stopping database connection pool");

        dataSource.close();

        TransactionManagerServices.getTransactionManager().shutdown();
    }
}