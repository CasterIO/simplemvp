package io.caster.simplemvp.view;

public interface UserView {
    int getUserId();

    void displayFirstName(String name);
    void displayLastName(String name);

    void showUserNotFoundMessage();
    void showUserSavedMessage();

    String getFirstName();
    String getLastName();

    void showUserNameIsRequired();
}
