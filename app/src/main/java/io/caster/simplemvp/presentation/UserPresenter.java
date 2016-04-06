package io.caster.simplemvp.presentation;

import io.caster.simplemvp.view.UserView;

public interface UserPresenter {
    void loadUserDetails();
    void setView(UserView view);
    void saveUser();
}
