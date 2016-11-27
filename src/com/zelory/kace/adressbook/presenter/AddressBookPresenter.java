package com.zelory.kace.adressbook.presenter;

import com.zelory.kace.adressbook.data.DataManager;
import com.zelory.kace.adressbook.data.model.AddressBook;
import rx.Observable;
import rx.schedulers.Schedulers;

import javax.swing.*;
import java.io.File;

public class AddressBookPresenter {
    private View view;

    public AddressBookPresenter(View view) {
        this.view = view;
    }

    public void loadAddressBook(File file) {
        view.showLoading();
        DataManager.getInstance()
                .loadAddressBook(file)
                .subscribeOn(Schedulers.io())
                .subscribe(addressBook -> SwingUtilities.invokeLater(() -> {
                    view.showAddressBook(addressBook);
                    view.dismissLoading();
                }), throwable -> SwingUtilities.invokeLater(() -> {
                    throwable.printStackTrace();
                    view.showError(throwable.getMessage());
                    view.dismissLoading();
                }));
    }

    public void saveAddressBook(AddressBook addressBook, String path) {
        view.showLoading();
        DataManager.getInstance()
                .save(addressBook, path)
                .subscribeOn(Schedulers.io())
                .subscribe(savedAddressBook -> SwingUtilities.invokeLater(() -> {
                    view.showAddressBook(savedAddressBook);
                    view.dismissLoading();
                }), throwable -> SwingUtilities.invokeLater(() -> {
                    throwable.printStackTrace();
                    view.showError(throwable.getMessage());
                    view.dismissLoading();
                }));
    }

    public void sortPersonsByName(AddressBook addressBook) {
        view.showLoading();
        Observable.from(addressBook.getPersons())
                .toSortedList((person1, person2) -> person1.getName().compareTo(person2.getName()))
                .doOnNext(addressBook::setPersons)
                .subscribeOn(Schedulers.computation())
                .subscribe(savedAddressBook -> SwingUtilities.invokeLater(() -> {
                    view.showAddressBook(addressBook);
                    view.dismissLoading();
                }), throwable -> SwingUtilities.invokeLater(() -> {
                    throwable.printStackTrace();
                    view.showError(throwable.getMessage());
                    view.dismissLoading();
                }));
    }

    public void sortPersonsByZip(AddressBook addressBook) {
        view.showLoading();
        Observable.from(addressBook.getPersons())
                .toSortedList((person1, person2) -> person1.getZip().compareTo(person2.getZip()))
                .doOnNext(addressBook::setPersons)
                .subscribeOn(Schedulers.computation())
                .subscribe(savedAddressBook -> SwingUtilities.invokeLater(() -> {
                    view.showAddressBook(addressBook);
                    view.dismissLoading();
                }), throwable -> SwingUtilities.invokeLater(() -> {
                    throwable.printStackTrace();
                    view.showError(throwable.getMessage());
                    view.dismissLoading();
                }));
    }

    public interface View {

        void showAddressBook(AddressBook addressBook);

        void showLoading();

        void dismissLoading();

        void showError(String errorMessage);
    }
}
