package ru.tandser.hibernate;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class AbstractTestHibernate {

    public static TransactionManagerSetup transactionManagerSetup;

    @BeforeClass
    public static void beforeClass() throws Exception {
        transactionManagerSetup = new TransactionManagerSetup(DatabaseProduct.POSTGRESQL, null);
    }

    @AfterClass
    public static void afterClass() {
        if (transactionManagerSetup != null) {
            transactionManagerSetup.stop();
        }
    }
}