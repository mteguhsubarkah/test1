package com.mycompany.myapp.service.dto;

import javax.persistence.Column;
import javax.persistence.Lob;

public class SoundWavDTO {
    private String soundclip;

    private byte[] soundData;

    private String soundDataContentType;

    private byte[] waveForm;

    private String waveFormContentType;

    private byte[] spectogram;

    private String spectogramContentType;

    public String getSoundclip() {
        return soundclip;
    }

    public void setSoundclip(String soundclip) {
        this.soundclip = soundclip;
    }

    public byte[] getSoundData() {
        return soundData;
    }

    public void setSoundData(byte[] soundData) {
        this.soundData = soundData;
    }

    public String getSoundDataContentType() {
        return soundDataContentType;
    }

    public void setSoundDataContentType(String soundDataContentType) {
        this.soundDataContentType = soundDataContentType;
    }

    public byte[] getWaveForm() {
        return waveForm;
    }

    public void setWaveForm(byte[] waveForm) {
        this.waveForm = waveForm;
    }

    public String getWaveFormContentType() {
        return waveFormContentType;
    }

    public void setWaveFormContentType(String waveFormContentType) {
        this.waveFormContentType = waveFormContentType;
    }

    public byte[] getSpectogram() {
        return spectogram;
    }

    public void setSpectogram(byte[] spectogram) {
        this.spectogram = spectogram;
    }

    public String getSpectogramContentType() {
        return spectogramContentType;
    }

    public void setSpectogramContentType(String spectogramContentType) {
        this.spectogramContentType = spectogramContentType;
    }
}
