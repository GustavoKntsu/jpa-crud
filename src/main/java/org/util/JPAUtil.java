package org.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    // O nome "jpa-crud-unit" deve ser IGUAL ao que estará no seu persistence.xml depois
    private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("jpa-crud-unit");

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }
}