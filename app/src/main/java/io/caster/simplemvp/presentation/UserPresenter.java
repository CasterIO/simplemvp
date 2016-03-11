package io.caster.simplemvp.presentation;

import io.caster.simplemvp.view.UserView;

public interface UserPresenter extends LifecylePresenter {
    void loadUserDetails();
    void setView(UserView view);
    void saveUser();
}
