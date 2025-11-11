package services;

import java.util.List;
import java.util.Optional;

public interface ServiceInterface<T> {
    void create(T entity);
    void update(T entity);
    Optional<T> getById(int id);
    List<T> getAll();
    void deleteById(int id);
}
