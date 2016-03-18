package io.caster.simplemvp;

import dagger.Module;
import dagger.Provides;
import io.caster.simplemvp.presentation.UserPresenter;
import io.caster.simplemvp.presentation.UserPresenterImpl;
import io.caster.simplemvp.repository.UserRepository;
import io.caster.simplemvp.repository.InMemoryUserRepositoryImpl;

@Module
public class AppModule {
    @Provides
    public UserRepository provideUserRepository() {
        return new InMemoryUserRepositoryImpl();
    }

    @Provides
    public UserPresenter provideUserPresenter(UserRepository userRepository) {
        return new UserPresenterImpl(userRepository);
    }
}
