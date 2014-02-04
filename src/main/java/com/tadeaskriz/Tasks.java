package com.tadeaskriz;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:tadeas.kriz@brainwashstudio.com">Tadeas Kriz</a>
 */
public class Tasks {

    @PersistenceUnit(unitName = "tasks")
    private EntityManagerFactory entityManagerFactory;

    public Task save(final Task task) {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.merge(task);
            entityTransaction.commit();
        } catch (final Exception e) {
            entityTransaction.rollback();
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
        return task;
    }

    public List<Task> getTasks(final int offset, final int limit) {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT task FROM Task task", Task.class)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public Task taskById(final Long id) {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT task FROM Task task WHERE task.id = :id", Task.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } finally {
            entityManager.close();
        }
    }

    public void deleteTaskById(final Long id) {
        Task task = taskById(id);

        deleteTask(task);
    }

    public void deleteTask(Task task) {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.remove(task);
            entityTransaction.commit();
        } finally {
            entityManager.close();
        }
    }

}
