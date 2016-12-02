package com.zelory.kace.adressbook.util;

import rx.Observable;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class FileWatcher {

    private static FileWatcher INSTANCE;

    public static FileWatcher getInstance() {
        if (INSTANCE == null) {
            synchronized (FileWatcher.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FileWatcher();
                }
            }
        }
        return INSTANCE;
    }


    private FileWatcher() {

    }

    public Observable<WatchEvent<?>> watch(File file) {
        return Observable.create(subscriber -> {
            try {
                String fileName = file.getName();
                Path dir = Paths.get(file.getParent());
                FileSystem fileSystem = dir.getFileSystem();
                WatchService watcher = fileSystem.newWatchService();
                dir.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);

                while (true) {
                    WatchKey key = watcher.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        Path changed = (Path) event.context();
                        if (changed.endsWith(fileName)) {
                            subscriber.onNext(event);
                        }
                    }
                    if (!key.reset()) {
                        break;
                    }
                }
            } catch (IOException e) {
                subscriber.onError(e);
            } catch (InterruptedException ignored) {

            } finally {
                subscriber.onCompleted();
            }
        });
    }
}