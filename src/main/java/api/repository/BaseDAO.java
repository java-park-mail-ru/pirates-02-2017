package api.repository;

import api.model.Model;

import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Vileven on 27.03.17.
 */
public interface BaseDAO<T extends Model<ID>, ID extends Serializable> extends EntityManagerAware {
    Class<T> getEntityClass();

    default void persist(T entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }

    default T find(ID id) {
        return getEntityManager().find(getEntityClass(), id);
    }

    default int updateByQueryWithParams(String jpqlQueryString, Map<String, Object> params) {
        TypedQuery<T> query = getEntityManager().createQuery(jpqlQueryString, getEntityClass());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList().size();
    }

    default void delete(ID id) {
        final T entity = getEntityManager().find(getEntityClass(), id);
        getEntityManager().remove(entity);
    }

    default List<T> findByQuery(String jpqlQueryString) {
        return findByQueryWithParams(jpqlQueryString, Collections.emptyMap());
    }

    default List<T> findByQueryWithParams(String jpqlQueryString, Map<String, Object> params) {
        TypedQuery<T> query = getEntityManager().createQuery(jpqlQueryString, getEntityClass());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }
}
