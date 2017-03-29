package api.DAO;


import api.model.Model;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface DAO<T extends Model<ID>, ID extends Serializable> {

    T persist(@NotNull T entity);
    T find(@NotNull ID id);
    default int update(@NotNull String jpqlQuery) {
        return this.update(jpqlQuery, null);
    }
    int update(@NotNull String jpqlQuery, @Nullable Map<String, Object> params);
    void delete(@NotNull ID id);
    void deleteAll();
    default List<T> select(@NotNull String jpqlQuery) {
        return this.select(jpqlQuery, null);
    }
    List<T> select(@NotNull String jpqlQuery, @Nullable Map<String, Object> params);

}
