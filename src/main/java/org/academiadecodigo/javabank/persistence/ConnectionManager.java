package org.academiadecodigo.javabank.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("batata"); ;

    private  EntityManager em;


    public static EntityManager getConnection (){
        return emf.createEntityManager();
    }

    public void close() {
        if (emf != null) {
            emf .close();
        }
    }

    public EntityManagerFactory getEMF() {
        return emf;
    }



}
