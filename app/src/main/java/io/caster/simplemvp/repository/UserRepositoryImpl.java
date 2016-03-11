package io.caster.simplemvp.repository;

import io.caster.simplemvp.model.User;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public User getUser(int id) {
        // Normally this would go to a DB/etc
        User u = new User();
        u.setId(id);
        u.setFirstName("Captain");
        u.setLastName("Crunch");
        return u;
    }

    @Override
    public void save(User u) {
        // save the user...
    }
}
