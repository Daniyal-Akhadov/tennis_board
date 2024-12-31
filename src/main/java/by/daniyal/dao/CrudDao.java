package by.daniyal.dao;

import by.daniyal.entity.Player;

public interface CrudDao<Type, Index> {
    Player save(Type value);
}
