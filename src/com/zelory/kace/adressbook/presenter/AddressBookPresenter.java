package com.zelory.kace.adressbook.presenter;

import com.zelory.kace.adressbook.data.DataManager;
import com.zelory.kace.adressbook.data.model.AddressBook;
import com.zelory.kace.adressbook.util.FileWatcher;
import com.zelory.kace.adressbook.util.ReportUtil;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;
import rx.Observable;
import rx.Subscription;
import rx.schedulers.Schedulers;

import javax.swing.*;
import java.io.File;
import java.nio.file.WatchEvent;

public class AddressBookPresenter {
    private View view;
    private Subscription addressBookWatcher;

    public AddressBookPresenter(View view) {
        this.view = view;
    }

    public void loadAddressBook(File file) {
        view.showLoading();
        DataManager.getInstance()
                .loadAddressBook(file)
                .subscribeOn(Schedulers.io())
                .subscribe(addressBook -> SwingUtilities.invokeLater(() -> {
                    watchAddressBook(file);
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
                .doOnNext(saveAddressBook -> saveAddressBook.setModified(false))
                .subscribeOn(Schedulers.io())
                .subscribe(savedAddressBook -> SwingUtilities.invokeLater(() -> {
                    watchAddressBook(new File(path));
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
                .toSortedList((person1, person2) -> {
                    if (person1.getLastName().equalsIgnoreCase(person2.getLastName())) {
                        return person1.getFirstName().toLowerCase().compareTo(person2.getFirstName().toLowerCase());
                    }
                    return person1.getLastName().toLowerCase().compareTo(person2.getLastName().toLowerCase());
                })
                .doOnNext(addressBook::setPersons)
                .subscribeOn(Schedulers.computation())
                .subscribe(persons -> SwingUtilities.invokeLater(() -> {
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
                .toSortedList((person1, person2) -> {
                    if (person1.getZip().equalsIgnoreCase(person2.getZip())) {
                        if (person1.getLastName().equalsIgnoreCase(person2.getLastName())) {
                            return person1.getFirstName().toLowerCase().compareTo(person2.getFirstName().toLowerCase());
                        }
                        return person1.getLastName().toLowerCase().compareTo(person2.getLastName().toLowerCase());
                    }
                    return person1.getZip().toLowerCase().compareTo(person2.getZip().toLowerCase());
                })
                .doOnNext(addressBook::setPersons)
                .subscribeOn(Schedulers.computation())
                .subscribe(persons -> SwingUtilities.invokeLater(() -> {
                    view.showAddressBook(addressBook);
                    view.dismissLoading();
                }), throwable -> SwingUtilities.invokeLater(() -> {
                    throwable.printStackTrace();
                    view.showError(throwable.getMessage());
                    view.dismissLoading();
                }));
    }

    public void printAddressBook(AddressBook addressBook) {
        view.showLoading();
        Observable<JasperReportBuilder> reportObservable = Observable.create(subscriber -> {
            try {
                subscriber.onNext(ReportUtil.getInstance().createReport(addressBook).show(false));
                subscriber.onCompleted();
            } catch (DRException e) {
                subscriber.onError(e);
                subscriber.onCompleted();
            }
        });
        reportObservable.subscribeOn(Schedulers.computation())
                .subscribe(report -> SwingUtilities.invokeLater(() -> {
                    view.dismissLoading();
                }), throwable -> SwingUtilities.invokeLater(() -> {
                    throwable.printStackTrace();
                    view.showError(throwable.getMessage());
                    view.dismissLoading();
                }));
    }

    private void watchAddressBook(File file) {
        releaseAddressBookWatcher();
        addressBookWatcher = FileWatcher.getInstance().watch(file)
                .subscribeOn(Schedulers.io())
                .subscribe(event -> SwingUtilities.invokeLater(() -> {
                    view.onAddressBookModified(event);
                }), throwable -> SwingUtilities.invokeLater(() -> {
                    throwable.printStackTrace();
                    view.showError(throwable.getMessage());
                }));
    }

    private void releaseAddressBookWatcher() {
        if (addressBookWatcher != null) {
            addressBookWatcher.unsubscribe();
            addressBookWatcher = null;
        }
    }

    public void detachView() {
        releaseAddressBookWatcher();
        view = null;
    }

    public interface View {

        void showAddressBook(AddressBook addressBook);

        void onAddressBookModified(WatchEvent<?> event);

        void showLoading();

        void dismissLoading();

        void showError(String errorMessage);
    }
}
