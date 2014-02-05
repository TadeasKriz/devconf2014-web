package com.tadeaskriz;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author <a href="mailto:tadeas.kriz@brainwashstudio.com">Tadeas Kriz</a>
 */
public class Tasks {

    @Inject
    private EntityManager entityManager;

    public Task save(Task task) {
        task = entityManager.merge(task);
        return task;
    }

    public List<Task> getTasks(final int offset, final int limit) {
        return entityManager.createQuery("SELECT task FROM Task task", Task.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public Task taskById(final Long id) {
        return entityManager.createQuery("SELECT task FROM Task task WHERE task.id = :id", Task.class)
                .setParameter("id", id)
                .getSingleResult();

    }

    public void deleteTaskById(final Long id) {
        Task task = taskById(id);

        deleteTask(task);
    }

    public void deleteTask(Task task) {
        entityManager.remove(task);
    }

}
