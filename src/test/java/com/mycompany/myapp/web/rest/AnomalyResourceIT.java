package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SoundWavApp;
import com.mycompany.myapp.domain.Anomaly;
import com.mycompany.myapp.repository.AnomalyRepository;
import com.mycompany.myapp.service.AnomalyService;
import com.mycompany.myapp.service.dto.AnomalyDTO;
import com.mycompany.myapp.service.mapper.AnomalyMapper;
import com.mycompany.myapp.service.dto.AnomalyCriteria;
import com.mycompany.myapp.service.AnomalyQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.AnomalyType;
/**
 * Integration tests for the {@link AnomalyResource} REST controller.
 */
@SpringBootTest(classes = SoundWavApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AnomalyResourceIT {

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_MACHINE = "AAAAAAAAAA";
    private static final String UPDATED_MACHINE = "BBBBBBBBBB";

    private static final String DEFAULT_SENSOR = "AAAAAAAAAA";
    private static final String UPDATED_SENSOR = "BBBBBBBBBB";

    private static final AnomalyType DEFAULT_ANOMALY = AnomalyType.Mild;
    private static final AnomalyType UPDATED_ANOMALY = AnomalyType.Moderate;

    private static final String DEFAULT_SOUNDCLIP = "AAAAAAAAAA";
    private static final String UPDATED_SOUNDCLIP = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private AnomalyRepository anomalyRepository;

    @Autowired
    private AnomalyMapper anomalyMapper;

    @Autowired
    private AnomalyService anomalyService;

    @Autowired
    private AnomalyQueryService anomalyQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnomalyMockMvc;

    private Anomaly anomaly;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Anomaly createEntity(EntityManager em) {
        Anomaly anomaly = new Anomaly()
            .timestamp(DEFAULT_TIMESTAMP)
            .machine(DEFAULT_MACHINE)
            .sensor(DEFAULT_SENSOR)
            .anomaly(DEFAULT_ANOMALY)
            .soundclip(DEFAULT_SOUNDCLIP)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return anomaly;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Anomaly createUpdatedEntity(EntityManager em) {
        Anomaly anomaly = new Anomaly()
            .timestamp(UPDATED_TIMESTAMP)
            .machine(UPDATED_MACHINE)
            .sensor(UPDATED_SENSOR)
            .anomaly(UPDATED_ANOMALY)
            .soundclip(UPDATED_SOUNDCLIP)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return anomaly;
    }

    @BeforeEach
    public void initTest() {
        anomaly = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnomaly() throws Exception {
        int databaseSizeBeforeCreate = anomalyRepository.findAll().size();
        // Create the Anomaly
        AnomalyDTO anomalyDTO = anomalyMapper.toDto(anomaly);
        restAnomalyMockMvc.perform(post("/api/anomalies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anomalyDTO)))
            .andExpect(status().isCreated());

        // Validate the Anomaly in the database
        List<Anomaly> anomalyList = anomalyRepository.findAll();
        assertThat(anomalyList).hasSize(databaseSizeBeforeCreate + 1);
        Anomaly testAnomaly = anomalyList.get(anomalyList.size() - 1);
        assertThat(testAnomaly.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testAnomaly.getMachine()).isEqualTo(DEFAULT_MACHINE);
        assertThat(testAnomaly.getSensor()).isEqualTo(DEFAULT_SENSOR);
        assertThat(testAnomaly.getAnomaly()).isEqualTo(DEFAULT_ANOMALY);
        assertThat(testAnomaly.getSoundclip()).isEqualTo(DEFAULT_SOUNDCLIP);
        assertThat(testAnomaly.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testAnomaly.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createAnomalyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = anomalyRepository.findAll().size();

        // Create the Anomaly with an existing ID
        anomaly.setId(1L);
        AnomalyDTO anomalyDTO = anomalyMapper.toDto(anomaly);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnomalyMockMvc.perform(post("/api/anomalies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anomalyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Anomaly in the database
        List<Anomaly> anomalyList = anomalyRepository.findAll();
        assertThat(anomalyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnomalies() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList
        restAnomalyMockMvc.perform(get("/api/anomalies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anomaly.getId().intValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))))
            .andExpect(jsonPath("$.[*].machine").value(hasItem(DEFAULT_MACHINE)))
            .andExpect(jsonPath("$.[*].sensor").value(hasItem(DEFAULT_SENSOR)))
            .andExpect(jsonPath("$.[*].anomaly").value(hasItem(DEFAULT_ANOMALY.toString())))
            .andExpect(jsonPath("$.[*].soundclip").value(hasItem(DEFAULT_SOUNDCLIP)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))));
    }
    
    @Test
    @Transactional
    public void getAnomaly() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get the anomaly
        restAnomalyMockMvc.perform(get("/api/anomalies/{id}", anomaly.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anomaly.getId().intValue()))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)))
            .andExpect(jsonPath("$.machine").value(DEFAULT_MACHINE))
            .andExpect(jsonPath("$.sensor").value(DEFAULT_SENSOR))
            .andExpect(jsonPath("$.anomaly").value(DEFAULT_ANOMALY.toString()))
            .andExpect(jsonPath("$.soundclip").value(DEFAULT_SOUNDCLIP))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)));
    }


    @Test
    @Transactional
    public void getAnomaliesByIdFiltering() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        Long id = anomaly.getId();

        defaultAnomalyShouldBeFound("id.equals=" + id);
        defaultAnomalyShouldNotBeFound("id.notEquals=" + id);

        defaultAnomalyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAnomalyShouldNotBeFound("id.greaterThan=" + id);

        defaultAnomalyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAnomalyShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAnomaliesByTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where timestamp equals to DEFAULT_TIMESTAMP
        defaultAnomalyShouldBeFound("timestamp.equals=" + DEFAULT_TIMESTAMP);

        // Get all the anomalyList where timestamp equals to UPDATED_TIMESTAMP
        defaultAnomalyShouldNotBeFound("timestamp.equals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    public void getAllAnomaliesByTimestampIsNotEqualToSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where timestamp not equals to DEFAULT_TIMESTAMP
        defaultAnomalyShouldNotBeFound("timestamp.notEquals=" + DEFAULT_TIMESTAMP);

        // Get all the anomalyList where timestamp not equals to UPDATED_TIMESTAMP
        defaultAnomalyShouldBeFound("timestamp.notEquals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    public void getAllAnomaliesByTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where timestamp in DEFAULT_TIMESTAMP or UPDATED_TIMESTAMP
        defaultAnomalyShouldBeFound("timestamp.in=" + DEFAULT_TIMESTAMP + "," + UPDATED_TIMESTAMP);

        // Get all the anomalyList where timestamp equals to UPDATED_TIMESTAMP
        defaultAnomalyShouldNotBeFound("timestamp.in=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    public void getAllAnomaliesByTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where timestamp is not null
        defaultAnomalyShouldBeFound("timestamp.specified=true");

        // Get all the anomalyList where timestamp is null
        defaultAnomalyShouldNotBeFound("timestamp.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnomaliesByTimestampIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where timestamp is greater than or equal to DEFAULT_TIMESTAMP
        defaultAnomalyShouldBeFound("timestamp.greaterThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the anomalyList where timestamp is greater than or equal to UPDATED_TIMESTAMP
        defaultAnomalyShouldNotBeFound("timestamp.greaterThanOrEqual=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    public void getAllAnomaliesByTimestampIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where timestamp is less than or equal to DEFAULT_TIMESTAMP
        defaultAnomalyShouldBeFound("timestamp.lessThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the anomalyList where timestamp is less than or equal to SMALLER_TIMESTAMP
        defaultAnomalyShouldNotBeFound("timestamp.lessThanOrEqual=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    public void getAllAnomaliesByTimestampIsLessThanSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where timestamp is less than DEFAULT_TIMESTAMP
        defaultAnomalyShouldNotBeFound("timestamp.lessThan=" + DEFAULT_TIMESTAMP);

        // Get all the anomalyList where timestamp is less than UPDATED_TIMESTAMP
        defaultAnomalyShouldBeFound("timestamp.lessThan=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    public void getAllAnomaliesByTimestampIsGreaterThanSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where timestamp is greater than DEFAULT_TIMESTAMP
        defaultAnomalyShouldNotBeFound("timestamp.greaterThan=" + DEFAULT_TIMESTAMP);

        // Get all the anomalyList where timestamp is greater than SMALLER_TIMESTAMP
        defaultAnomalyShouldBeFound("timestamp.greaterThan=" + SMALLER_TIMESTAMP);
    }


    @Test
    @Transactional
    public void getAllAnomaliesByMachineIsEqualToSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where machine equals to DEFAULT_MACHINE
        defaultAnomalyShouldBeFound("machine.equals=" + DEFAULT_MACHINE);

        // Get all the anomalyList where machine equals to UPDATED_MACHINE
        defaultAnomalyShouldNotBeFound("machine.equals=" + UPDATED_MACHINE);
    }

    @Test
    @Transactional
    public void getAllAnomaliesByMachineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where machine not equals to DEFAULT_MACHINE
        defaultAnomalyShouldNotBeFound("machine.notEquals=" + DEFAULT_MACHINE);

        // Get all the anomalyList where machine not equals to UPDATED_MACHINE
        defaultAnomalyShouldBeFound("machine.notEquals=" + UPDATED_MACHINE);
    }

    @Test
    @Transactional
    public void getAllAnomaliesByMachineIsInShouldWork() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where machine in DEFAULT_MACHINE or UPDATED_MACHINE
        defaultAnomalyShouldBeFound("machine.in=" + DEFAULT_MACHINE + "," + UPDATED_MACHINE);

        // Get all the anomalyList where machine equals to UPDATED_MACHINE
        defaultAnomalyShouldNotBeFound("machine.in=" + UPDATED_MACHINE);
    }

    @Test
    @Transactional
    public void getAllAnomaliesByMachineIsNullOrNotNull() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where machine is not null
        defaultAnomalyShouldBeFound("machine.specified=true");

        // Get all the anomalyList where machine is null
        defaultAnomalyShouldNotBeFound("machine.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnomaliesByMachineContainsSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where machine contains DEFAULT_MACHINE
        defaultAnomalyShouldBeFound("machine.contains=" + DEFAULT_MACHINE);

        // Get all the anomalyList where machine contains UPDATED_MACHINE
        defaultAnomalyShouldNotBeFound("machine.contains=" + UPDATED_MACHINE);
    }

    @Test
    @Transactional
    public void getAllAnomaliesByMachineNotContainsSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where machine does not contain DEFAULT_MACHINE
        defaultAnomalyShouldNotBeFound("machine.doesNotContain=" + DEFAULT_MACHINE);

        // Get all the anomalyList where machine does not contain UPDATED_MACHINE
        defaultAnomalyShouldBeFound("machine.doesNotContain=" + UPDATED_MACHINE);
    }


    @Test
    @Transactional
    public void getAllAnomaliesBySensorIsEqualToSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where sensor equals to DEFAULT_SENSOR
        defaultAnomalyShouldBeFound("sensor.equals=" + DEFAULT_SENSOR);

        // Get all the anomalyList where sensor equals to UPDATED_SENSOR
        defaultAnomalyShouldNotBeFound("sensor.equals=" + UPDATED_SENSOR);
    }

    @Test
    @Transactional
    public void getAllAnomaliesBySensorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where sensor not equals to DEFAULT_SENSOR
        defaultAnomalyShouldNotBeFound("sensor.notEquals=" + DEFAULT_SENSOR);

        // Get all the anomalyList where sensor not equals to UPDATED_SENSOR
        defaultAnomalyShouldBeFound("sensor.notEquals=" + UPDATED_SENSOR);
    }

    @Test
    @Transactional
    public void getAllAnomaliesBySensorIsInShouldWork() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where sensor in DEFAULT_SENSOR or UPDATED_SENSOR
        defaultAnomalyShouldBeFound("sensor.in=" + DEFAULT_SENSOR + "," + UPDATED_SENSOR);

        // Get all the anomalyList where sensor equals to UPDATED_SENSOR
        defaultAnomalyShouldNotBeFound("sensor.in=" + UPDATED_SENSOR);
    }

    @Test
    @Transactional
    public void getAllAnomaliesBySensorIsNullOrNotNull() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where sensor is not null
        defaultAnomalyShouldBeFound("sensor.specified=true");

        // Get all the anomalyList where sensor is null
        defaultAnomalyShouldNotBeFound("sensor.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnomaliesBySensorContainsSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where sensor contains DEFAULT_SENSOR
        defaultAnomalyShouldBeFound("sensor.contains=" + DEFAULT_SENSOR);

        // Get all the anomalyList where sensor contains UPDATED_SENSOR
        defaultAnomalyShouldNotBeFound("sensor.contains=" + UPDATED_SENSOR);
    }

    @Test
    @Transactional
    public void getAllAnomaliesBySensorNotContainsSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where sensor does not contain DEFAULT_SENSOR
        defaultAnomalyShouldNotBeFound("sensor.doesNotContain=" + DEFAULT_SENSOR);

        // Get all the anomalyList where sensor does not contain UPDATED_SENSOR
        defaultAnomalyShouldBeFound("sensor.doesNotContain=" + UPDATED_SENSOR);
    }


    @Test
    @Transactional
    public void getAllAnomaliesByAnomalyIsEqualToSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where anomaly equals to DEFAULT_ANOMALY
        defaultAnomalyShouldBeFound("anomaly.equals=" + DEFAULT_ANOMALY);

        // Get all the anomalyList where anomaly equals to UPDATED_ANOMALY
        defaultAnomalyShouldNotBeFound("anomaly.equals=" + UPDATED_ANOMALY);
    }

    @Test
    @Transactional
    public void getAllAnomaliesByAnomalyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where anomaly not equals to DEFAULT_ANOMALY
        defaultAnomalyShouldNotBeFound("anomaly.notEquals=" + DEFAULT_ANOMALY);

        // Get all the anomalyList where anomaly not equals to UPDATED_ANOMALY
        defaultAnomalyShouldBeFound("anomaly.notEquals=" + UPDATED_ANOMALY);
    }

    @Test
    @Transactional
    public void getAllAnomaliesByAnomalyIsInShouldWork() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where anomaly in DEFAULT_ANOMALY or UPDATED_ANOMALY
        defaultAnomalyShouldBeFound("anomaly.in=" + DEFAULT_ANOMALY + "," + UPDATED_ANOMALY);

        // Get all the anomalyList where anomaly equals to UPDATED_ANOMALY
        defaultAnomalyShouldNotBeFound("anomaly.in=" + UPDATED_ANOMALY);
    }

    @Test
    @Transactional
    public void getAllAnomaliesByAnomalyIsNullOrNotNull() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where anomaly is not null
        defaultAnomalyShouldBeFound("anomaly.specified=true");

        // Get all the anomalyList where anomaly is null
        defaultAnomalyShouldNotBeFound("anomaly.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnomaliesBySoundclipIsEqualToSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where soundclip equals to DEFAULT_SOUNDCLIP
        defaultAnomalyShouldBeFound("soundclip.equals=" + DEFAULT_SOUNDCLIP);

        // Get all the anomalyList where soundclip equals to UPDATED_SOUNDCLIP
        defaultAnomalyShouldNotBeFound("soundclip.equals=" + UPDATED_SOUNDCLIP);
    }

    @Test
    @Transactional
    public void getAllAnomaliesBySoundclipIsNotEqualToSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where soundclip not equals to DEFAULT_SOUNDCLIP
        defaultAnomalyShouldNotBeFound("soundclip.notEquals=" + DEFAULT_SOUNDCLIP);

        // Get all the anomalyList where soundclip not equals to UPDATED_SOUNDCLIP
        defaultAnomalyShouldBeFound("soundclip.notEquals=" + UPDATED_SOUNDCLIP);
    }

    @Test
    @Transactional
    public void getAllAnomaliesBySoundclipIsInShouldWork() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where soundclip in DEFAULT_SOUNDCLIP or UPDATED_SOUNDCLIP
        defaultAnomalyShouldBeFound("soundclip.in=" + DEFAULT_SOUNDCLIP + "," + UPDATED_SOUNDCLIP);

        // Get all the anomalyList where soundclip equals to UPDATED_SOUNDCLIP
        defaultAnomalyShouldNotBeFound("soundclip.in=" + UPDATED_SOUNDCLIP);
    }

    @Test
    @Transactional
    public void getAllAnomaliesBySoundclipIsNullOrNotNull() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where soundclip is not null
        defaultAnomalyShouldBeFound("soundclip.specified=true");

        // Get all the anomalyList where soundclip is null
        defaultAnomalyShouldNotBeFound("soundclip.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnomaliesBySoundclipContainsSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where soundclip contains DEFAULT_SOUNDCLIP
        defaultAnomalyShouldBeFound("soundclip.contains=" + DEFAULT_SOUNDCLIP);

        // Get all the anomalyList where soundclip contains UPDATED_SOUNDCLIP
        defaultAnomalyShouldNotBeFound("soundclip.contains=" + UPDATED_SOUNDCLIP);
    }

    @Test
    @Transactional
    public void getAllAnomaliesBySoundclipNotContainsSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where soundclip does not contain DEFAULT_SOUNDCLIP
        defaultAnomalyShouldNotBeFound("soundclip.doesNotContain=" + DEFAULT_SOUNDCLIP);

        // Get all the anomalyList where soundclip does not contain UPDATED_SOUNDCLIP
        defaultAnomalyShouldBeFound("soundclip.doesNotContain=" + UPDATED_SOUNDCLIP);
    }


    @Test
    @Transactional
    public void getAllAnomaliesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where createdAt equals to DEFAULT_CREATED_AT
        defaultAnomalyShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the anomalyList where createdAt equals to UPDATED_CREATED_AT
        defaultAnomalyShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllAnomaliesByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where createdAt not equals to DEFAULT_CREATED_AT
        defaultAnomalyShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the anomalyList where createdAt not equals to UPDATED_CREATED_AT
        defaultAnomalyShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllAnomaliesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultAnomalyShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the anomalyList where createdAt equals to UPDATED_CREATED_AT
        defaultAnomalyShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllAnomaliesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where createdAt is not null
        defaultAnomalyShouldBeFound("createdAt.specified=true");

        // Get all the anomalyList where createdAt is null
        defaultAnomalyShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnomaliesByCreatedAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where createdAt is greater than or equal to DEFAULT_CREATED_AT
        defaultAnomalyShouldBeFound("createdAt.greaterThanOrEqual=" + DEFAULT_CREATED_AT);

        // Get all the anomalyList where createdAt is greater than or equal to UPDATED_CREATED_AT
        defaultAnomalyShouldNotBeFound("createdAt.greaterThanOrEqual=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllAnomaliesByCreatedAtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where createdAt is less than or equal to DEFAULT_CREATED_AT
        defaultAnomalyShouldBeFound("createdAt.lessThanOrEqual=" + DEFAULT_CREATED_AT);

        // Get all the anomalyList where createdAt is less than or equal to SMALLER_CREATED_AT
        defaultAnomalyShouldNotBeFound("createdAt.lessThanOrEqual=" + SMALLER_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllAnomaliesByCreatedAtIsLessThanSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where createdAt is less than DEFAULT_CREATED_AT
        defaultAnomalyShouldNotBeFound("createdAt.lessThan=" + DEFAULT_CREATED_AT);

        // Get all the anomalyList where createdAt is less than UPDATED_CREATED_AT
        defaultAnomalyShouldBeFound("createdAt.lessThan=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllAnomaliesByCreatedAtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where createdAt is greater than DEFAULT_CREATED_AT
        defaultAnomalyShouldNotBeFound("createdAt.greaterThan=" + DEFAULT_CREATED_AT);

        // Get all the anomalyList where createdAt is greater than SMALLER_CREATED_AT
        defaultAnomalyShouldBeFound("createdAt.greaterThan=" + SMALLER_CREATED_AT);
    }


    @Test
    @Transactional
    public void getAllAnomaliesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultAnomalyShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the anomalyList where updatedAt equals to UPDATED_UPDATED_AT
        defaultAnomalyShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllAnomaliesByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultAnomalyShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the anomalyList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultAnomalyShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllAnomaliesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultAnomalyShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the anomalyList where updatedAt equals to UPDATED_UPDATED_AT
        defaultAnomalyShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllAnomaliesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where updatedAt is not null
        defaultAnomalyShouldBeFound("updatedAt.specified=true");

        // Get all the anomalyList where updatedAt is null
        defaultAnomalyShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnomaliesByUpdatedAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where updatedAt is greater than or equal to DEFAULT_UPDATED_AT
        defaultAnomalyShouldBeFound("updatedAt.greaterThanOrEqual=" + DEFAULT_UPDATED_AT);

        // Get all the anomalyList where updatedAt is greater than or equal to UPDATED_UPDATED_AT
        defaultAnomalyShouldNotBeFound("updatedAt.greaterThanOrEqual=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllAnomaliesByUpdatedAtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where updatedAt is less than or equal to DEFAULT_UPDATED_AT
        defaultAnomalyShouldBeFound("updatedAt.lessThanOrEqual=" + DEFAULT_UPDATED_AT);

        // Get all the anomalyList where updatedAt is less than or equal to SMALLER_UPDATED_AT
        defaultAnomalyShouldNotBeFound("updatedAt.lessThanOrEqual=" + SMALLER_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllAnomaliesByUpdatedAtIsLessThanSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where updatedAt is less than DEFAULT_UPDATED_AT
        defaultAnomalyShouldNotBeFound("updatedAt.lessThan=" + DEFAULT_UPDATED_AT);

        // Get all the anomalyList where updatedAt is less than UPDATED_UPDATED_AT
        defaultAnomalyShouldBeFound("updatedAt.lessThan=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllAnomaliesByUpdatedAtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        // Get all the anomalyList where updatedAt is greater than DEFAULT_UPDATED_AT
        defaultAnomalyShouldNotBeFound("updatedAt.greaterThan=" + DEFAULT_UPDATED_AT);

        // Get all the anomalyList where updatedAt is greater than SMALLER_UPDATED_AT
        defaultAnomalyShouldBeFound("updatedAt.greaterThan=" + SMALLER_UPDATED_AT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAnomalyShouldBeFound(String filter) throws Exception {
        restAnomalyMockMvc.perform(get("/api/anomalies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anomaly.getId().intValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))))
            .andExpect(jsonPath("$.[*].machine").value(hasItem(DEFAULT_MACHINE)))
            .andExpect(jsonPath("$.[*].sensor").value(hasItem(DEFAULT_SENSOR)))
            .andExpect(jsonPath("$.[*].anomaly").value(hasItem(DEFAULT_ANOMALY.toString())))
            .andExpect(jsonPath("$.[*].soundclip").value(hasItem(DEFAULT_SOUNDCLIP)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))));

        // Check, that the count call also returns 1
        restAnomalyMockMvc.perform(get("/api/anomalies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAnomalyShouldNotBeFound(String filter) throws Exception {
        restAnomalyMockMvc.perform(get("/api/anomalies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAnomalyMockMvc.perform(get("/api/anomalies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAnomaly() throws Exception {
        // Get the anomaly
        restAnomalyMockMvc.perform(get("/api/anomalies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnomaly() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        int databaseSizeBeforeUpdate = anomalyRepository.findAll().size();

        // Update the anomaly
        Anomaly updatedAnomaly = anomalyRepository.findById(anomaly.getId()).get();
        // Disconnect from session so that the updates on updatedAnomaly are not directly saved in db
        em.detach(updatedAnomaly);
        updatedAnomaly
            .timestamp(UPDATED_TIMESTAMP)
            .machine(UPDATED_MACHINE)
            .sensor(UPDATED_SENSOR)
            .anomaly(UPDATED_ANOMALY)
            .soundclip(UPDATED_SOUNDCLIP)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        AnomalyDTO anomalyDTO = anomalyMapper.toDto(updatedAnomaly);

        restAnomalyMockMvc.perform(put("/api/anomalies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anomalyDTO)))
            .andExpect(status().isOk());

        // Validate the Anomaly in the database
        List<Anomaly> anomalyList = anomalyRepository.findAll();
        assertThat(anomalyList).hasSize(databaseSizeBeforeUpdate);
        Anomaly testAnomaly = anomalyList.get(anomalyList.size() - 1);
        assertThat(testAnomaly.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testAnomaly.getMachine()).isEqualTo(UPDATED_MACHINE);
        assertThat(testAnomaly.getSensor()).isEqualTo(UPDATED_SENSOR);
        assertThat(testAnomaly.getAnomaly()).isEqualTo(UPDATED_ANOMALY);
        assertThat(testAnomaly.getSoundclip()).isEqualTo(UPDATED_SOUNDCLIP);
        assertThat(testAnomaly.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAnomaly.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingAnomaly() throws Exception {
        int databaseSizeBeforeUpdate = anomalyRepository.findAll().size();

        // Create the Anomaly
        AnomalyDTO anomalyDTO = anomalyMapper.toDto(anomaly);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnomalyMockMvc.perform(put("/api/anomalies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anomalyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Anomaly in the database
        List<Anomaly> anomalyList = anomalyRepository.findAll();
        assertThat(anomalyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnomaly() throws Exception {
        // Initialize the database
        anomalyRepository.saveAndFlush(anomaly);

        int databaseSizeBeforeDelete = anomalyRepository.findAll().size();

        // Delete the anomaly
        restAnomalyMockMvc.perform(delete("/api/anomalies/{id}", anomaly.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Anomaly> anomalyList = anomalyRepository.findAll();
        assertThat(anomalyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
