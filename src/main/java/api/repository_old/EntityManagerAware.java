package api.repository_old;

import javax.persistence.EntityManager;


public interface EntityManagerAware {
    EntityManager getEntityManager();
}
