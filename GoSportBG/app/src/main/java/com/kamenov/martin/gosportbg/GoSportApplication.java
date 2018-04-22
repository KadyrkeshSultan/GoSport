package com.kamenov.martin.gosportbg;

import android.app.Application;

import com.kamenov.martin.gosportbg.models.DaoMaster;
import com.kamenov.martin.gosportbg.models.DaoSession;
import com.kamenov.martin.gosportbg.models.SettingsConfiguration;
import com.kamenov.martin.gosportbg.repositories.GenericCacheRepository;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Martin on 17.4.2018 г..
 */

public class GoSportApplication extends Application {
    private DaoSession mDaoSession;
    private GenericCacheRepository<SettingsConfiguration, Long> mSettingsConfigurationRepository;

    public GoSportApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "gosport-db");
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    public GenericCacheRepository<SettingsConfiguration, Long> getSettingsConfigurationRepository() {
        if (mSettingsConfigurationRepository == null) {
            mSettingsConfigurationRepository = new GenericCacheRepository<>(mDaoSession.getSettingsConfigurationDao());
        }

        return mSettingsConfigurationRepository;
    }
}
