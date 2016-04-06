package io.caster.simplemvp;

import org.junit.Before;
import org.junit.Test;

import io.caster.simplemvp.model.User;
import io.caster.simplemvp.presentation.UserPresenter;
import io.caster.simplemvp.presentation.UserPresenterImpl;
import io.caster.simplemvp.repository.UserRepository;
import io.caster.simplemvp.view.UserView;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
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

        presenter.setView(mockView );

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

        presenter.setView(mockView);

        // Verify repository interactions
        verify(mockUserRepository, times(1)).getUser(anyInt());

        // verify view interactions
        verify(mockView, times(1)).getUserId();
        verify(mockView, times(1)).showUserNotFoundMessage();
        verify(mockView, never()).displayFirstName(anyString());
        verify(mockView, never()).displayLastName(anyString());
    }

    @Test
    public void shouldShouldErrorMessageDuringSaveIfFirstOrLastNameIsMissing() {
        when(mockView.getUserId()).thenReturn(1);

        // Load the user
        presenter.setView(mockView);

        verify(mockView, times(1)).getUserId();

        // Set up the view mock
        when(mockView.getFirstName()).thenReturn(""); // empty string

        presenter.saveUser();

        verify(mockView, times(1)).getFirstName();
        verify(mockView, never()).getLastName();
        verify(mockView, times(1)).showUserNameIsRequired();

        // Now tell mockView to return a value for first name and an empty last name
        when(mockView.getFirstName()).thenReturn("Foo");
        when(mockView.getLastName()).thenReturn("");

        presenter.saveUser();

        verify(mockView, times(2)).getFirstName(); // Called two times now, once before, and once now
        verify(mockView, times(1)).getLastName();  // Only called once
        verify(mockView, times(2)).showUserNameIsRequired(); // Called two times now, once before and once now
    }

    @Test
    public void shouldBeAbleToSaveAValidUser() {
        when(mockView.getUserId()).thenReturn(1);

        // Load the user
        presenter.setView(mockView);

        verify(mockView, times(1)).getUserId();

        when(mockView.getFirstName()).thenReturn("Foo");
        when(mockView.getLastName()).thenReturn("Bar");

        presenter.saveUser();

        // Called two more times in the saveUser call.
        verify(mockView, times(2)).getFirstName();
        verify(mockView, times(2)).getLastName();

        assertThat(user.getFirstName(), is("Foo"));
        assertThat(user.getLastName(), is("Bar"));

        // Make sure the repository saved the user
        verify(mockUserRepository, times(1)).save(user);

        // Make sure that the view showed the user saved message
        verify(mockView, times(1)).showUserSavedMessage();
    }

    @Test
    public void shouldLoadUserDetailsWhenTheViewIsSet() {
        presenter.setView(mockView);
        verify(mockUserRepository, times(1)).getUser(anyInt());
        verify(mockView, times(1)).displayFirstName(anyString());
        verify(mockView, times(1)).displayLastName(anyString());
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenViewIsNull() {
        // Null out the view
        presenter.setView(null);

        // Try to load the screen which will force interactions with the view
        presenter.loadUserDetails();
    }
}
