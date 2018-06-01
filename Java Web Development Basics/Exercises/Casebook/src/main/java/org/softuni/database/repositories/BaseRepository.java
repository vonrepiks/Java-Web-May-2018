package org.softuni.database.repositories;

import org.hibernate.TransactionException;
import org.softuni.database.RepositoryActionInvoker;
import org.softuni.database.RepositoryActionResult;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public abstract class BaseRepository implements UserRepository {
    private EntityManagerFactory entityManagerFactory;
    private EntityTransaction entityTransaction;
    protected EntityManager entityManager;

    protected BaseRepository() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("casebook");
    }

    private void initializeEntityManager() {
        this.entityManager = this.entityManagerFactory.createEntityManager();
    }

    private void initializeEntityTransaction() {
        this.entityTransaction = this.entityManager.getTransaction();
    }

    private void dismiss() {
        this.entityManager.close();
    }

    protected RepositoryActionResult executeAction(RepositoryActionInvoker repositoryActionInvoker) {
        this.initializeEntityManager();
        this.initializeEntityTransaction();

        RepositoryActionResult repositoryActionResult = new RepositoryActionResult();
        try {
            this.entityTransaction.begin();

            repositoryActionInvoker.invoke(repositoryActionResult);

            this.entityTransaction.commit();
        } catch (TransactionException te) {
            if (this.entityTransaction != null && this.entityTransaction.isActive()) {
                this.entityTransaction.rollback();
            }
            te.printStackTrace();
        }

        this.dismiss();

        return repositoryActionResult;
    }

    @Override
    public void close() {
        this.entityManagerFactory.close();
    }
}
