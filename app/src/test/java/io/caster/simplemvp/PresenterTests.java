package io.caster.simplemvp;

import org.junit.Before;
import org.junit.Test;

import io.caster.simplemvp.model.User;
import io.caster.simplemvp.presentation.UserPresenter;
import io.caster.simplemvp.presentation.UserPresenterImpl;
import io.caster.simplemvp.repository.UserRepository;
import io.caster.simplemvp.view.UserView;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;


public class PresenterTests {

    UserRepository mockUserRepository;
    UserView mockView;
    UserPresenter presenter;
    User user;

    @Before
    public void setup() {
        mockUserRepository = mock(UserRepository.class);

        user = new User();
        user.setId(1);
        user.setFirstName("Mighty");
        user.setLastName("Mouse");
        when(mockUserRepository.getUser(anyInt())).thenReturn(user);

        mockView = mock(UserView.class);

        presenter = new UserPresenterImpl(mockUserRepository);
        presenter.setView(mockView);

    }

    @Test
    public void noInteractionsWithViewShouldTakePlaceIfUserIsNull() {
        presenter.saveUser();

        // user object is not initialized, lets verify no interactions take place
        verifyZeroInteractions(mockView);
    }

    @Test
    public void shouldBeABleToLoadTheUserFromTheRepositoryWhenValidUserIsPresent() {
        when(mockView.getUserId()).thenReturn(1);

        presenter.loadUserDetails();

        // Verify repository interactions
        verify(mockUserRepository, times(1)).getUser(anyInt());

        // Verify view interactions
        verify(mockView, times(1)).getUserId();
        verify(mockView, times(1)).displayFirstName("Mighty");
        verify(mockView, times(1)).displayLastName("Mouse");
        verify(mockView, never()).showUserNotFoundMessage();
    }

    @Test
    public void shouldShowErrorMessageOnViewWhenUserIsNotPresent() {
        when(mockView.getUserId()).thenReturn(1);

        // Return null when we ask the repo for a user.
        when(mockUserRepository.getUser(anyInt())).thenReturn(null);

        presenter.loadUserDetails();

        // Verify repository interactions
        verify(mockUserRepository, times(1)).getUser(anyInt());

        // verify view interactions
        verify(mockView, times(1)).getUserId();
        verify(mockView, times(1)).showUserNotFoundMessage();
        verify(mockView, never()).displayFirstName(anyString());
        verify(mockView, never()).displayLastName(anyString());
    }


}
