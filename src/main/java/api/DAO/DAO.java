package api.DAO;


import api.model.Model;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface DAO<T extends Model<ID>, ID extends Serializable> {

    /**
     * Сохраняет запись
     * @param entity запись
     * @return запись
     */
    T persist(@NotNull T entity);


    /**
     * Находит запись по id
     * @param id идентификатор записи
     * @return запись
     */
    T find(@NotNull ID id);


    /**
     * Произвоит jpql-запрос (как правило, обновляющий данные в базе)
     * @param jpqlQuery Строка запроса
     * @return число изменённых строк
     */
    default int update(@NotNull String jpqlQuery) {
        return this.update(jpqlQuery, null);
    }


    /**
     * Произвоит jpql-запрос (как правило, обновляющий данные в базе)
     * @param jpqlQuery Строка запроса
     * @param params Параметры запроса
     * @return число изменённых строк
     */
    int update(@NotNull String jpqlQuery, @Nullable Map<String, Object> params);


    /**
     * Удаляет запись
     * @param id идентификатор
     */
    void delete(@NotNull ID id);


    /**
     * Удаляет все записи
     */
    void deleteAll();


    /**
     * Возвращает записи по запросу
     * @param jpqlQuery jpql-запрос
     * @return список записей
     */
    default List<T> select(@NotNull String jpqlQuery) {
        return this.select(jpqlQuery, null);
    }


    /**
     * Возвращает записи по запросу
     * @param jpqlQuery jpql-запрос
     * @param params параметры
     * @return список записей
     */
    List<T> select(@NotNull String jpqlQuery, @Nullable Map<String, Object> params);

}
