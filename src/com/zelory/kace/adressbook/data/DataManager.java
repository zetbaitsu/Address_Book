package com.zelory.kace.adressbook.data;

import com.google.gson.JsonSyntaxException;
import com.zelory.kace.adressbook.data.model.AddressBook;
import com.zelory.kace.adressbook.util.JsonParser;
import rx.Observable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataManager {
    private static DataManager INSTANCE;

    public static DataManager getInstance() {
        if (INSTANCE == null) {
            synchronized (DataManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataManager();
                }
            }
        }
        return INSTANCE;
    }

    private DataManager() {

    }

    public Observable<AddressBook> save(AddressBook addressBook, String path) {
        return Observable.create(subscriber -> {
            try {
                File file = !path.endsWith(".zkc") ? new File(path + ".zkc") : new File(path);
                FileWriter fileWriter = new FileWriter(file, false);
                fileWriter.write(JsonParser.getInstance().getParser().toJson(addressBook.getPersons()));
                fileWriter.close();
                addressBook.setName(file.getName());
                addressBook.setPath(file.getAbsolutePath());
                subscriber.onNext(addressBook);
            } catch (IOException e) {
                subscriber.onError(e);
            } finally {
                subscriber.onCompleted();
            }
        });
    }

    public Observable<AddressBook> loadAddressBook(File file) {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(new AddressBook(file));
            } catch (IOException e) {
                subscriber.onError(e);
            } catch (JsonSyntaxException e) {
                subscriber.onError(new Throwable("Invalid file format!"));
            } finally {
                subscriber.onCompleted();
            }
        });
    }
}
