package controllers;

import java.util.List;
import java.util.Optional;

public interface ControllerInterface<T> {
    void create(T e);
    void update(T e);
    Optional<T> getById(int id);
    List<T> getAll();
    void deleteById(int id);
}
