package org.dao;

import org.model.Produto;
import org.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ProdutoDAO {

    // Salvar um novo produto
    public void cadastrar(Produto produto) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(produto);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback(); // Desfaz se der erro
            throw e;
        } finally {
            em.close();
        }
    }

    // Atualizar um produto existente
    public void atualizar(Produto produto) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(produto); // O merge atualiza ou salva se não existir
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // Remover um produto
    public void remover(Produto produto) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            // O merge garante que o objeto está "monitorado" pelo JPA antes de remover
            produto = em.merge(produto);
            em.remove(produto);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // Listar todos os produtos para a tabela
    public List<Produto> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // JPQL: Linguagem de consulta do JPA (focada no Objeto, não na tabela)
            return em.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
        } finally {
            em.close();
        }
    }
}