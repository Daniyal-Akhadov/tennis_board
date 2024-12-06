package by.daniyal.dao;

public interface Dao<Type, Index> {
    Type save(Type model);

    Type update(Type model);

    Type delete(Type model);

    Type find(Index id);
}
