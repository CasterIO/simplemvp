package io.caster.simplemvp;

import javax.inject.Singleton;

import dagger.Component;
import io.caster.simplemvp.view.fragment.UserFragment;

@Singleton
@Component(modules = { AppModule.class })
public interface AppComponent {
    void inject(UserFragment target);
}
