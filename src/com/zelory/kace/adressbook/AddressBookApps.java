package com.zelory.kace.adressbook;

import com.zelory.kace.adressbook.ui.AddressBookFrame;

import java.util.concurrent.atomic.AtomicInteger;

public class AddressBookApps {
    private static AddressBookApps INSTANCE;

    private AtomicInteger frameCount;

    public static AddressBookApps getInstance() {
        if (INSTANCE == null) {
            synchronized (AddressBookApps.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AddressBookApps();
                }
            }
        }
        return INSTANCE;
    }

    private AddressBookApps() {
        frameCount = new AtomicInteger(0);
    }

    public static void main(String[] args) {
        new AddressBookFrame().setVisible(true);
    }

    public void incrementFrameCount() {
        frameCount.incrementAndGet();
    }

    public void decrementFrameCount() {
        frameCount.decrementAndGet();
    }

    public int getFrameCount() {
        return frameCount.get();
    }
}
