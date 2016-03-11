package io.caster.simplemvp.repository;

import io.caster.simplemvp.model.User;

public interface UserRepository {
    User getUser(int id);
    void save(User u);
}
