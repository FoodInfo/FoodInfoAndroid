package com.xpn.foodinfo.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;
import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class BaseViewModel extends ViewModel implements Observable {

    protected CompositeDisposable subscriptions = new CompositeDisposable();
    private transient PropertyChangeRegistry mCallbacks;

    public BaseViewModel() {
    }

    @Override
    protected void onCleared() {
        subscriptions.dispose();
        super.onCleared();
    }
    protected void addSubscription(Disposable subscription) {
        subscriptions.add(subscription);
    }

    @Override
    public void addOnPropertyChangedCallback(@NonNull OnPropertyChangedCallback callback) {
        synchronized (this) {
            if (mCallbacks == null) {
                mCallbacks = new PropertyChangeRegistry();
            }
        }
        mCallbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(@NonNull OnPropertyChangedCallback callback) {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.remove(callback);
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    public void notifyChange() {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.notifyCallbacks(this, 0, null);
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with {@link Bindable} to generate a field in
     * <code>BR</code> to be used as <code>fieldId</code>.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    public void notifyPropertyChanged(int fieldId) {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.notifyCallbacks(this, fieldId, null);
    }
}
