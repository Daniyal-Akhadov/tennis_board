package by.daniyal.dao;

import java.util.Optional;

public interface Dao<Type, Index> {
    Type save(Type model);

    boolean update(Type model);

    boolean delete(Type model);

    Optional<Type> find(Index id);
}
