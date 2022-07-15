package org.academiadecodigo.javabank.persistence;

import javax.persistence.EntityManager;

public class JpaTransactionManager implements TransactionManager {
    private JpaSessionManager sm;


    @Override

    public void beginRead() {
        sm.startSession();
    }

    @Override

    public void beginWrite() {
        sm.getCurrentSession().getTransaction().begin();
    }

    @Override
    public void commit() {
        if (sm.getCurrentSession().getTransaction().isActive()) {
            sm.getCurrentSession().getTransaction().commit();
        }
        sm.stopSession();
    }

    @Override

    public void rollback() {
        if (sm.getCurrentSession().getTransaction().isActive()) {
            sm.getCurrentSession().getTransaction().rollback();
        }
        sm.stopSession();
    }

    @Override
    public EntityManager getEm() {
        return sm.getCurrentSession();
    }

    @Override
    public EntityManager setEm() {
        return sm.getCurrentSession();
    }


    public void setSm(JpaSessionManager sm){
        this.sm = sm;
    }

}
