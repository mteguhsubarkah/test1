package com.mycompany.myapp.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Arrays;


@Entity
@Table(name = "sound_wav")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SoundWav {
    @Id
    @Column(name="soundclip")
    private String soundclip;
    @Lob
    @Column(name = "sound_data")
    private byte[] soundData;

    @Column(name = "sound_data_content_type")
    private String soundDataContentType;
    @Lob
    @Column(name = "wave_form")
    private byte[] waveForm;

    @Column(name = "wave_form_content_type")
    private String waveFormContentType;
    @Lob
    @Column(name = "spectogram")
    private byte[] spectogram;

    @Column(name = "spectogram_content_type")
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
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnomalyItem)) {
            return false;
        }
        return soundclip != null && soundclip.equals(((SoundWav) o).soundclip);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SoundWav{" +
            "soundclip='" + soundclip + '\'' +
            ", soundData=" + Arrays.toString(soundData) +
            ", soundDataContentType='" + soundDataContentType + '\'' +
            ", waveForm=" + Arrays.toString(waveForm) +
            ", waveFormContentType='" + waveFormContentType + '\'' +
            ", spectogram=" + Arrays.toString(spectogram) +
            ", spectogramContentType='" + spectogramContentType + '\'' +
            '}';
    }
}
