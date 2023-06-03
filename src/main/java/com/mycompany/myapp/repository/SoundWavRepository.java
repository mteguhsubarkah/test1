package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SoundWav;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoundWavRepository extends JpaRepository<SoundWav, String> {

}
