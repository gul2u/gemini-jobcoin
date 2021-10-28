package com.gemini.jobcoin.services.mixer;

import lombok.SneakyThrows;

import java.util.TimerTask;

public class MixerScannerTask extends TimerTask {
    private final Mixer mixer;

    public MixerScannerTask(Mixer mixer) {
        this.mixer = mixer;
    }

    @SneakyThrows
    @Override
    public void run() {
        mixer.monitorTransactions();
    }
}
