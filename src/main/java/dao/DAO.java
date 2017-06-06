package dao;

/**
 * Created by MYKOLA.GOROKHOV on 02.06.2017.
 */
public interface DAO<T> {
        /*CRUD operations
    C- create
    R - read
    U  - update
    D - delete
    */

    void create(T t);

    T read(int id);

    T update(int id, T t);

    void delete(int id);

}
