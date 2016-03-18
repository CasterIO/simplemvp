package io.caster.simplemvp.repository;

import io.caster.simplemvp.model.User;

public class InMemoryUserRepositoryImpl implements UserRepository {
    private User u;

    /**
     * Gets the user from memory.
     *
     * @param id The id of the user.
     * @return a {@link User}
     */
    @Override
    public User getUser(int id) {
        // Normally this would go to a DB/etc and fetch the user with an ID.
        // Here though, we're just doing something in memory, so we're kind of just
        // faking it out.
        if (u == null) {
            u = new User();
            u.setId(id);
            u.setFirstName("Captain");
            u.setLastName("Crunch");
        }
        return u;
    }

    /**
     * Save's the in-memory user.
     *
     * @param u The user.
     */
    @Override
    public void save(User u) {
        if(this.u == null) {
            this.u = getUser(0); // create the in memory user.
        }
        this.u.setId(u.getId());
        this.u.setFirstName(u.getFirstName());
        this.u.setLastName(u.getLastName());
    }
}
