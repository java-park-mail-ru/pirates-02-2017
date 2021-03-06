package api.DAO;


import api.model.Model;
import org.hibernate.boot.model.naming.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface DAO<T extends Model<K>, K extends Serializable> {

    T persist(@NotNull T entity);
    T find(@NotNull K id);
    default int update(@NotNull String jpqlQuery) {
        return this.update(jpqlQuery, null);
    }
    int update(@NotNull String jpqlQuery, @Nullable Map<String, Object> params);
    void delete(@NotNull K id);
    void deleteAll();
    default List<T> select(@NotNull String jpqlQuery) {
        return this.select(jpqlQuery, null);
    }
    List<T> select(@NotNull String jpqlQuery, @Nullable Map<String, Object> params);

}
