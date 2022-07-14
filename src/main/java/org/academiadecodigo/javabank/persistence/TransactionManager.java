package org.academiadecodigo.javabank.persistence;

import org.academiadecodigo.javabank.App;

import javax.persistence.EntityManager;
import javax.transaction.*;

public class TransactionManager {
    private SessionManager sm;



    public void setSm(SessionManager sm) {
        this.sm = sm;
    }

    public void beginRead()  {

        sm.startSession();
    }
    public void beginWrite()  {
        sm.getCurrentSession().getTransaction().begin();

    }

    public void commit() {
        if (sm.getCurrentSession().getTransaction().isActive()) {
            sm.getCurrentSession().getTransaction().commit();
        }
        sm.stopSession();
    }

    public void rollback() throws IllegalStateException, SecurityException, SystemException {
        if (sm.getCurrentSession().getTransaction().isActive()) {
            sm.getCurrentSession().getTransaction().rollback();
        }
        sm.stopSession();
    }

    public SessionManager getSm() {
        return sm;
    }

    public EntityManager getEm() {
        return sm.getCurrentSession();
    }

}
