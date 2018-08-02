package com.manish;

import android.app.Application;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;

import com.manish.db.table.Category;
import com.manish.db.table.MostOrderdProduct;
import com.manish.utils.Apputil;
import com.manish.utils.DataUtils;
import com.manish.viewmodel.DataViewModel;

import java.util.List;

public class ApplicationLoader extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    private static ApplicationLoader mInstance;

    public static synchronized ApplicationLoader getInstance() {
        return mInstance;
    }
}
