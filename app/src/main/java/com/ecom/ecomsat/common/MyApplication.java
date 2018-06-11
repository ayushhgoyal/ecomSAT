package com.ecom.ecomsat.common;

import android.app.Application;

import com.ecom.ecomsat.common.di.ApiComponent;
import com.ecom.ecomsat.common.di.ApiModule;
import com.ecom.ecomsat.common.di.DaggerApiComponent;

import io.realm.Realm;

/**
 * Created by ayushgoyal on 08/06/18.
 */

@SuppressWarnings("deprecation")
public class MyApplication extends Application {
    public ApiComponent getDaggerApiComponent() {
        return daggerApiComponent;
    }

    private ApiComponent daggerApiComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initDagger();
        initRealm();

    }

    private void initRealm() {
        Realm.init(this);
    }

    private void initDagger() {
        daggerApiComponent = DaggerApiComponent.builder()
                .apiModule(new ApiModule()).build();


    }
}
