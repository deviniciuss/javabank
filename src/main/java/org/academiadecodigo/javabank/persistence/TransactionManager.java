package org.academiadecodigo.javabank.persistence;

import javax.persistence.EntityManager;

public interface TransactionManager {
    void beginRead();
    void beginWrite();
    void commit ();
    void rollback();
    EntityManager getEm();
    EntityManager setEm();
}
