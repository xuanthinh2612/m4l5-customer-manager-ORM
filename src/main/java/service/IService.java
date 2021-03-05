package service;

import java.util.List;

public interface IService<T> {

    List<T> findAll();

    T findById(int id);

    void create(T t);

    void update(T t);

    void delete(int id);

}
