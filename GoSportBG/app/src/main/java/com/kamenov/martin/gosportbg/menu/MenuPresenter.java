package com.kamenov.martin.gosportbg.menu;

import com.kamenov.martin.gosportbg.base.contracts.BaseContracts;
import com.kamenov.martin.gosportbg.models.LocalUser;
import com.kamenov.martin.gosportbg.navigation.ActivityNavigationCommand;
import com.kamenov.martin.gosportbg.navigation.NavigationCommand;
import com.kamenov.martin.gosportbg.repositories.GenericCacheRepository;

/**
 * Created by Martin on 17.4.2018 г..
 */

public class MenuPresenter implements MenuContracts.IMenuPresenter {
    private MenuContracts.IMenuView mView;
    private NavigationCommand mNewEventCommand;
    private NavigationCommand mShowEventsCommand;

    public MenuPresenter(NavigationCommand newEventCommand, NavigationCommand showEventsCommand) {
        this.mNewEventCommand = newEventCommand;
        this.mShowEventsCommand = showEventsCommand;
    }
    @Override
    public void subscribe(BaseContracts.View view) {
        this.mView = (MenuContracts.IMenuView) view;
    }

    @Override
    public void unsubscribe() {
        this.mView = null;
    }

    @Override
    public void navigateToCreateNewEvents() {
        mNewEventCommand.navigate();
    }

    @Override
    public void navigateToShowEvents() {
        mShowEventsCommand.navigate();
    }

    @Override
    public void clearLocalUser() {
        GenericCacheRepository<LocalUser, Long> repo = mView.getGoSportApplication().getLocalUserRepository();

        repo.clearAll();
    }

    @Override
    public void logout() {
        clearLocalUser();
        mView.navigateToLogin();
    }
}