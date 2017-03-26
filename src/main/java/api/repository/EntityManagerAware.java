package api.repository;

import javax.persistence.EntityManager;

/**
 * Created by Vileven on 27.03.17.
 */
public interface EntityManagerAware {
    EntityManager getEntityManager();
}
