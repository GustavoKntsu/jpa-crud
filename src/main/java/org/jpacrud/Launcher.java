package org.jpacrud;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.model.Usuario;

public class Launcher {
    public static void main(String[] args) {
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("un-jpa");
       EntityManager em = emf.createEntityManager();
       em.getTransaction().begin();
       Usuario usuario = new Usuario();
       usuario.setNome("Joao Silva");
       usuario.setEmail("joaosilva@gmail.com");
       usuario.setSenha("123456");
       em.persist(usuario);
       em.getTransaction().commit();
       em.close();
    }
}
