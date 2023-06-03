package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SoundWavApp;
import com.mycompany.myapp.domain.AnomalyItem;
import com.mycompany.myapp.domain.Anomaly;
import com.mycompany.myapp.repository.AnomalyItemRepository;
import com.mycompany.myapp.service.AnomalyItemService;
import com.mycompany.myapp.service.dto.AnomalyItemDTO;
import com.mycompany.myapp.service.mapper.AnomalyItemMapper;
import com.mycompany.myapp.service.dto.AnomalyItemCriteria;
import com.mycompany.myapp.service.AnomalyItemQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
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

import com.mycompany.myapp.domain.enumeration.ActionType;
/**
 * Integration tests for the {@link AnomalyItemResource} REST controller.
 */
@SpringBootTest(classes = SoundWavApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AnomalyItemResourceIT {

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_MACHINE = "AAAAAAAAAA";
    private static final String UPDATED_MACHINE = "BBBBBBBBBB";

    private static final String DEFAULT_COMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMENT = "BBBBBBBBBB";

    private static final ActionType DEFAULT_ACTION = ActionType.Immediate;
    private static final ActionType UPDATED_ACTION = ActionType.Later;

    private static final String DEFAULT_SOUNDCLIP = "AAAAAAAAAA";
    private static final String UPDATED_SOUNDCLIP = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SOUND_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SOUND_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SOUND_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SOUND_DATA_CONTENT_TYPE = "image/png";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private AnomalyItemRepository anomalyItemRepository;

    @Autowired
    private AnomalyItemMapper anomalyItemMapper;

    @Autowired
    private AnomalyItemService anomalyItemService;

    @Autowired
    private AnomalyItemQueryService anomalyItemQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnomalyItemMockMvc;

    private AnomalyItem anomalyItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnomalyItem createEntity(EntityManager em) {
        AnomalyItem anomalyItem = new AnomalyItem()
            .reason(DEFAULT_REASON)
            .machine(DEFAULT_MACHINE)
            .coment(DEFAULT_COMENT)
            .action(DEFAULT_ACTION)
            .soundclip(DEFAULT_SOUNDCLIP)
            .soundData(DEFAULT_SOUND_DATA)
            .soundDataContentType(DEFAULT_SOUND_DATA_CONTENT_TYPE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return anomalyItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnomalyItem createUpdatedEntity(EntityManager em) {
        AnomalyItem anomalyItem = new AnomalyItem()
            .reason(UPDATED_REASON)
            .machine(UPDATED_MACHINE)
            .coment(UPDATED_COMENT)
            .action(UPDATED_ACTION)
            .soundclip(UPDATED_SOUNDCLIP)
            .soundData(UPDATED_SOUND_DATA)
            .soundDataContentType(UPDATED_SOUND_DATA_CONTENT_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return anomalyItem;
    }

    @BeforeEach
    public void initTest() {
        anomalyItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnomalyItem() throws Exception {
        int databaseSizeBeforeCreate = anomalyItemRepository.findAll().size();
        // Create the AnomalyItem
        AnomalyItemDTO anomalyItemDTO = anomalyItemMapper.toDto(anomalyItem);
        restAnomalyItemMockMvc.perform(post("/api/anomaly-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anomalyItemDTO)))
            .andExpect(status().isCreated());

        // Validate the AnomalyItem in the database
        List<AnomalyItem> anomalyItemList = anomalyItemRepository.findAll();
        assertThat(anomalyItemList).hasSize(databaseSizeBeforeCreate + 1);
        AnomalyItem testAnomalyItem = anomalyItemList.get(anomalyItemList.size() - 1);
        assertThat(testAnomalyItem.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testAnomalyItem.getMachine()).isEqualTo(DEFAULT_MACHINE);
        assertThat(testAnomalyItem.getComent()).isEqualTo(DEFAULT_COMENT);
        assertThat(testAnomalyItem.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testAnomalyItem.getSoundclip()).isEqualTo(DEFAULT_SOUNDCLIP);
        assertThat(testAnomalyItem.getSoundData()).isEqualTo(DEFAULT_SOUND_DATA);
        assertThat(testAnomalyItem.getSoundDataContentType()).isEqualTo(DEFAULT_SOUND_DATA_CONTENT_TYPE);
        assertThat(testAnomalyItem.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testAnomalyItem.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createAnomalyItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = anomalyItemRepository.findAll().size();

        // Create the AnomalyItem with an existing ID
        anomalyItem.setId(1L);
        AnomalyItemDTO anomalyItemDTO = anomalyItemMapper.toDto(anomalyItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnomalyItemMockMvc.perform(post("/api/anomaly-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anomalyItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnomalyItem in the database
        List<AnomalyItem> anomalyItemList = anomalyItemRepository.findAll();
        assertThat(anomalyItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnomalyItems() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList
        restAnomalyItemMockMvc.perform(get("/api/anomaly-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anomalyItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].machine").value(hasItem(DEFAULT_MACHINE)))
            .andExpect(jsonPath("$.[*].coment").value(hasItem(DEFAULT_COMENT)))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION.toString())))
            .andExpect(jsonPath("$.[*].soundclip").value(hasItem(DEFAULT_SOUNDCLIP)))
            .andExpect(jsonPath("$.[*].soundDataContentType").value(hasItem(DEFAULT_SOUND_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].soundData").value(hasItem(Base64Utils.encodeToString(DEFAULT_SOUND_DATA))))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))));
    }
    
    @Test
    @Transactional
    public void getAnomalyItem() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get the anomalyItem
        restAnomalyItemMockMvc.perform(get("/api/anomaly-items/{id}", anomalyItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anomalyItem.getId().intValue()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON))
            .andExpect(jsonPath("$.machine").value(DEFAULT_MACHINE))
            .andExpect(jsonPath("$.coment").value(DEFAULT_COMENT))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION.toString()))
            .andExpect(jsonPath("$.soundclip").value(DEFAULT_SOUNDCLIP))
            .andExpect(jsonPath("$.soundDataContentType").value(DEFAULT_SOUND_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.soundData").value(Base64Utils.encodeToString(DEFAULT_SOUND_DATA)))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)));
    }


    @Test
    @Transactional
    public void getAnomalyItemsByIdFiltering() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        Long id = anomalyItem.getId();

        defaultAnomalyItemShouldBeFound("id.equals=" + id);
        defaultAnomalyItemShouldNotBeFound("id.notEquals=" + id);

        defaultAnomalyItemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAnomalyItemShouldNotBeFound("id.greaterThan=" + id);

        defaultAnomalyItemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAnomalyItemShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAnomalyItemsByReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where reason equals to DEFAULT_REASON
        defaultAnomalyItemShouldBeFound("reason.equals=" + DEFAULT_REASON);

        // Get all the anomalyItemList where reason equals to UPDATED_REASON
        defaultAnomalyItemShouldNotBeFound("reason.equals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where reason not equals to DEFAULT_REASON
        defaultAnomalyItemShouldNotBeFound("reason.notEquals=" + DEFAULT_REASON);

        // Get all the anomalyItemList where reason not equals to UPDATED_REASON
        defaultAnomalyItemShouldBeFound("reason.notEquals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByReasonIsInShouldWork() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where reason in DEFAULT_REASON or UPDATED_REASON
        defaultAnomalyItemShouldBeFound("reason.in=" + DEFAULT_REASON + "," + UPDATED_REASON);

        // Get all the anomalyItemList where reason equals to UPDATED_REASON
        defaultAnomalyItemShouldNotBeFound("reason.in=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where reason is not null
        defaultAnomalyItemShouldBeFound("reason.specified=true");

        // Get all the anomalyItemList where reason is null
        defaultAnomalyItemShouldNotBeFound("reason.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnomalyItemsByReasonContainsSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where reason contains DEFAULT_REASON
        defaultAnomalyItemShouldBeFound("reason.contains=" + DEFAULT_REASON);

        // Get all the anomalyItemList where reason contains UPDATED_REASON
        defaultAnomalyItemShouldNotBeFound("reason.contains=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByReasonNotContainsSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where reason does not contain DEFAULT_REASON
        defaultAnomalyItemShouldNotBeFound("reason.doesNotContain=" + DEFAULT_REASON);

        // Get all the anomalyItemList where reason does not contain UPDATED_REASON
        defaultAnomalyItemShouldBeFound("reason.doesNotContain=" + UPDATED_REASON);
    }


    @Test
    @Transactional
    public void getAllAnomalyItemsByMachineIsEqualToSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where machine equals to DEFAULT_MACHINE
        defaultAnomalyItemShouldBeFound("machine.equals=" + DEFAULT_MACHINE);

        // Get all the anomalyItemList where machine equals to UPDATED_MACHINE
        defaultAnomalyItemShouldNotBeFound("machine.equals=" + UPDATED_MACHINE);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByMachineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where machine not equals to DEFAULT_MACHINE
        defaultAnomalyItemShouldNotBeFound("machine.notEquals=" + DEFAULT_MACHINE);

        // Get all the anomalyItemList where machine not equals to UPDATED_MACHINE
        defaultAnomalyItemShouldBeFound("machine.notEquals=" + UPDATED_MACHINE);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByMachineIsInShouldWork() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where machine in DEFAULT_MACHINE or UPDATED_MACHINE
        defaultAnomalyItemShouldBeFound("machine.in=" + DEFAULT_MACHINE + "," + UPDATED_MACHINE);

        // Get all the anomalyItemList where machine equals to UPDATED_MACHINE
        defaultAnomalyItemShouldNotBeFound("machine.in=" + UPDATED_MACHINE);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByMachineIsNullOrNotNull() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where machine is not null
        defaultAnomalyItemShouldBeFound("machine.specified=true");

        // Get all the anomalyItemList where machine is null
        defaultAnomalyItemShouldNotBeFound("machine.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnomalyItemsByMachineContainsSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where machine contains DEFAULT_MACHINE
        defaultAnomalyItemShouldBeFound("machine.contains=" + DEFAULT_MACHINE);

        // Get all the anomalyItemList where machine contains UPDATED_MACHINE
        defaultAnomalyItemShouldNotBeFound("machine.contains=" + UPDATED_MACHINE);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByMachineNotContainsSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where machine does not contain DEFAULT_MACHINE
        defaultAnomalyItemShouldNotBeFound("machine.doesNotContain=" + DEFAULT_MACHINE);

        // Get all the anomalyItemList where machine does not contain UPDATED_MACHINE
        defaultAnomalyItemShouldBeFound("machine.doesNotContain=" + UPDATED_MACHINE);
    }


    @Test
    @Transactional
    public void getAllAnomalyItemsByComentIsEqualToSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where coment equals to DEFAULT_COMENT
        defaultAnomalyItemShouldBeFound("coment.equals=" + DEFAULT_COMENT);

        // Get all the anomalyItemList where coment equals to UPDATED_COMENT
        defaultAnomalyItemShouldNotBeFound("coment.equals=" + UPDATED_COMENT);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByComentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where coment not equals to DEFAULT_COMENT
        defaultAnomalyItemShouldNotBeFound("coment.notEquals=" + DEFAULT_COMENT);

        // Get all the anomalyItemList where coment not equals to UPDATED_COMENT
        defaultAnomalyItemShouldBeFound("coment.notEquals=" + UPDATED_COMENT);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByComentIsInShouldWork() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where coment in DEFAULT_COMENT or UPDATED_COMENT
        defaultAnomalyItemShouldBeFound("coment.in=" + DEFAULT_COMENT + "," + UPDATED_COMENT);

        // Get all the anomalyItemList where coment equals to UPDATED_COMENT
        defaultAnomalyItemShouldNotBeFound("coment.in=" + UPDATED_COMENT);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByComentIsNullOrNotNull() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where coment is not null
        defaultAnomalyItemShouldBeFound("coment.specified=true");

        // Get all the anomalyItemList where coment is null
        defaultAnomalyItemShouldNotBeFound("coment.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnomalyItemsByComentContainsSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where coment contains DEFAULT_COMENT
        defaultAnomalyItemShouldBeFound("coment.contains=" + DEFAULT_COMENT);

        // Get all the anomalyItemList where coment contains UPDATED_COMENT
        defaultAnomalyItemShouldNotBeFound("coment.contains=" + UPDATED_COMENT);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByComentNotContainsSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where coment does not contain DEFAULT_COMENT
        defaultAnomalyItemShouldNotBeFound("coment.doesNotContain=" + DEFAULT_COMENT);

        // Get all the anomalyItemList where coment does not contain UPDATED_COMENT
        defaultAnomalyItemShouldBeFound("coment.doesNotContain=" + UPDATED_COMENT);
    }


    @Test
    @Transactional
    public void getAllAnomalyItemsByActionIsEqualToSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where action equals to DEFAULT_ACTION
        defaultAnomalyItemShouldBeFound("action.equals=" + DEFAULT_ACTION);

        // Get all the anomalyItemList where action equals to UPDATED_ACTION
        defaultAnomalyItemShouldNotBeFound("action.equals=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where action not equals to DEFAULT_ACTION
        defaultAnomalyItemShouldNotBeFound("action.notEquals=" + DEFAULT_ACTION);

        // Get all the anomalyItemList where action not equals to UPDATED_ACTION
        defaultAnomalyItemShouldBeFound("action.notEquals=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByActionIsInShouldWork() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where action in DEFAULT_ACTION or UPDATED_ACTION
        defaultAnomalyItemShouldBeFound("action.in=" + DEFAULT_ACTION + "," + UPDATED_ACTION);

        // Get all the anomalyItemList where action equals to UPDATED_ACTION
        defaultAnomalyItemShouldNotBeFound("action.in=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where action is not null
        defaultAnomalyItemShouldBeFound("action.specified=true");

        // Get all the anomalyItemList where action is null
        defaultAnomalyItemShouldNotBeFound("action.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsBySoundclipIsEqualToSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where soundclip equals to DEFAULT_SOUNDCLIP
        defaultAnomalyItemShouldBeFound("soundclip.equals=" + DEFAULT_SOUNDCLIP);

        // Get all the anomalyItemList where soundclip equals to UPDATED_SOUNDCLIP
        defaultAnomalyItemShouldNotBeFound("soundclip.equals=" + UPDATED_SOUNDCLIP);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsBySoundclipIsNotEqualToSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where soundclip not equals to DEFAULT_SOUNDCLIP
        defaultAnomalyItemShouldNotBeFound("soundclip.notEquals=" + DEFAULT_SOUNDCLIP);

        // Get all the anomalyItemList where soundclip not equals to UPDATED_SOUNDCLIP
        defaultAnomalyItemShouldBeFound("soundclip.notEquals=" + UPDATED_SOUNDCLIP);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsBySoundclipIsInShouldWork() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where soundclip in DEFAULT_SOUNDCLIP or UPDATED_SOUNDCLIP
        defaultAnomalyItemShouldBeFound("soundclip.in=" + DEFAULT_SOUNDCLIP + "," + UPDATED_SOUNDCLIP);

        // Get all the anomalyItemList where soundclip equals to UPDATED_SOUNDCLIP
        defaultAnomalyItemShouldNotBeFound("soundclip.in=" + UPDATED_SOUNDCLIP);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsBySoundclipIsNullOrNotNull() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where soundclip is not null
        defaultAnomalyItemShouldBeFound("soundclip.specified=true");

        // Get all the anomalyItemList where soundclip is null
        defaultAnomalyItemShouldNotBeFound("soundclip.specified=false");
    }
                @Test
    @Transactional
    public void getAllAnomalyItemsBySoundclipContainsSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where soundclip contains DEFAULT_SOUNDCLIP
        defaultAnomalyItemShouldBeFound("soundclip.contains=" + DEFAULT_SOUNDCLIP);

        // Get all the anomalyItemList where soundclip contains UPDATED_SOUNDCLIP
        defaultAnomalyItemShouldNotBeFound("soundclip.contains=" + UPDATED_SOUNDCLIP);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsBySoundclipNotContainsSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where soundclip does not contain DEFAULT_SOUNDCLIP
        defaultAnomalyItemShouldNotBeFound("soundclip.doesNotContain=" + DEFAULT_SOUNDCLIP);

        // Get all the anomalyItemList where soundclip does not contain UPDATED_SOUNDCLIP
        defaultAnomalyItemShouldBeFound("soundclip.doesNotContain=" + UPDATED_SOUNDCLIP);
    }


    @Test
    @Transactional
    public void getAllAnomalyItemsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where createdAt equals to DEFAULT_CREATED_AT
        defaultAnomalyItemShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the anomalyItemList where createdAt equals to UPDATED_CREATED_AT
        defaultAnomalyItemShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where createdAt not equals to DEFAULT_CREATED_AT
        defaultAnomalyItemShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the anomalyItemList where createdAt not equals to UPDATED_CREATED_AT
        defaultAnomalyItemShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultAnomalyItemShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the anomalyItemList where createdAt equals to UPDATED_CREATED_AT
        defaultAnomalyItemShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where createdAt is not null
        defaultAnomalyItemShouldBeFound("createdAt.specified=true");

        // Get all the anomalyItemList where createdAt is null
        defaultAnomalyItemShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByCreatedAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where createdAt is greater than or equal to DEFAULT_CREATED_AT
        defaultAnomalyItemShouldBeFound("createdAt.greaterThanOrEqual=" + DEFAULT_CREATED_AT);

        // Get all the anomalyItemList where createdAt is greater than or equal to UPDATED_CREATED_AT
        defaultAnomalyItemShouldNotBeFound("createdAt.greaterThanOrEqual=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByCreatedAtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where createdAt is less than or equal to DEFAULT_CREATED_AT
        defaultAnomalyItemShouldBeFound("createdAt.lessThanOrEqual=" + DEFAULT_CREATED_AT);

        // Get all the anomalyItemList where createdAt is less than or equal to SMALLER_CREATED_AT
        defaultAnomalyItemShouldNotBeFound("createdAt.lessThanOrEqual=" + SMALLER_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByCreatedAtIsLessThanSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where createdAt is less than DEFAULT_CREATED_AT
        defaultAnomalyItemShouldNotBeFound("createdAt.lessThan=" + DEFAULT_CREATED_AT);

        // Get all the anomalyItemList where createdAt is less than UPDATED_CREATED_AT
        defaultAnomalyItemShouldBeFound("createdAt.lessThan=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByCreatedAtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where createdAt is greater than DEFAULT_CREATED_AT
        defaultAnomalyItemShouldNotBeFound("createdAt.greaterThan=" + DEFAULT_CREATED_AT);

        // Get all the anomalyItemList where createdAt is greater than SMALLER_CREATED_AT
        defaultAnomalyItemShouldBeFound("createdAt.greaterThan=" + SMALLER_CREATED_AT);
    }


    @Test
    @Transactional
    public void getAllAnomalyItemsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultAnomalyItemShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the anomalyItemList where updatedAt equals to UPDATED_UPDATED_AT
        defaultAnomalyItemShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultAnomalyItemShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the anomalyItemList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultAnomalyItemShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultAnomalyItemShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the anomalyItemList where updatedAt equals to UPDATED_UPDATED_AT
        defaultAnomalyItemShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where updatedAt is not null
        defaultAnomalyItemShouldBeFound("updatedAt.specified=true");

        // Get all the anomalyItemList where updatedAt is null
        defaultAnomalyItemShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByUpdatedAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where updatedAt is greater than or equal to DEFAULT_UPDATED_AT
        defaultAnomalyItemShouldBeFound("updatedAt.greaterThanOrEqual=" + DEFAULT_UPDATED_AT);

        // Get all the anomalyItemList where updatedAt is greater than or equal to UPDATED_UPDATED_AT
        defaultAnomalyItemShouldNotBeFound("updatedAt.greaterThanOrEqual=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByUpdatedAtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where updatedAt is less than or equal to DEFAULT_UPDATED_AT
        defaultAnomalyItemShouldBeFound("updatedAt.lessThanOrEqual=" + DEFAULT_UPDATED_AT);

        // Get all the anomalyItemList where updatedAt is less than or equal to SMALLER_UPDATED_AT
        defaultAnomalyItemShouldNotBeFound("updatedAt.lessThanOrEqual=" + SMALLER_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByUpdatedAtIsLessThanSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where updatedAt is less than DEFAULT_UPDATED_AT
        defaultAnomalyItemShouldNotBeFound("updatedAt.lessThan=" + DEFAULT_UPDATED_AT);

        // Get all the anomalyItemList where updatedAt is less than UPDATED_UPDATED_AT
        defaultAnomalyItemShouldBeFound("updatedAt.lessThan=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllAnomalyItemsByUpdatedAtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        // Get all the anomalyItemList where updatedAt is greater than DEFAULT_UPDATED_AT
        defaultAnomalyItemShouldNotBeFound("updatedAt.greaterThan=" + DEFAULT_UPDATED_AT);

        // Get all the anomalyItemList where updatedAt is greater than SMALLER_UPDATED_AT
        defaultAnomalyItemShouldBeFound("updatedAt.greaterThan=" + SMALLER_UPDATED_AT);
    }


    @Test
    @Transactional
    public void getAllAnomalyItemsByAnomalyIsEqualToSomething() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);
        Anomaly anomaly = AnomalyResourceIT.createEntity(em);
        em.persist(anomaly);
        em.flush();
        anomalyItem.setAnomaly(anomaly);
        anomalyItemRepository.saveAndFlush(anomalyItem);
        Long anomalyId = anomaly.getId();

        // Get all the anomalyItemList where anomaly equals to anomalyId
        defaultAnomalyItemShouldBeFound("anomalyId.equals=" + anomalyId);

        // Get all the anomalyItemList where anomaly equals to anomalyId + 1
        defaultAnomalyItemShouldNotBeFound("anomalyId.equals=" + (anomalyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAnomalyItemShouldBeFound(String filter) throws Exception {
        restAnomalyItemMockMvc.perform(get("/api/anomaly-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anomalyItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].machine").value(hasItem(DEFAULT_MACHINE)))
            .andExpect(jsonPath("$.[*].coment").value(hasItem(DEFAULT_COMENT)))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION.toString())))
            .andExpect(jsonPath("$.[*].soundclip").value(hasItem(DEFAULT_SOUNDCLIP)))
            .andExpect(jsonPath("$.[*].soundDataContentType").value(hasItem(DEFAULT_SOUND_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].soundData").value(hasItem(Base64Utils.encodeToString(DEFAULT_SOUND_DATA))))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))));

        // Check, that the count call also returns 1
        restAnomalyItemMockMvc.perform(get("/api/anomaly-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAnomalyItemShouldNotBeFound(String filter) throws Exception {
        restAnomalyItemMockMvc.perform(get("/api/anomaly-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAnomalyItemMockMvc.perform(get("/api/anomaly-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAnomalyItem() throws Exception {
        // Get the anomalyItem
        restAnomalyItemMockMvc.perform(get("/api/anomaly-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnomalyItem() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        int databaseSizeBeforeUpdate = anomalyItemRepository.findAll().size();

        // Update the anomalyItem
        AnomalyItem updatedAnomalyItem = anomalyItemRepository.findById(anomalyItem.getId()).get();
        // Disconnect from session so that the updates on updatedAnomalyItem are not directly saved in db
        em.detach(updatedAnomalyItem);
        updatedAnomalyItem
            .reason(UPDATED_REASON)
            .machine(UPDATED_MACHINE)
            .coment(UPDATED_COMENT)
            .action(UPDATED_ACTION)
            .soundclip(UPDATED_SOUNDCLIP)
            .soundData(UPDATED_SOUND_DATA)
            .soundDataContentType(UPDATED_SOUND_DATA_CONTENT_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        AnomalyItemDTO anomalyItemDTO = anomalyItemMapper.toDto(updatedAnomalyItem);

        restAnomalyItemMockMvc.perform(put("/api/anomaly-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anomalyItemDTO)))
            .andExpect(status().isOk());

        // Validate the AnomalyItem in the database
        List<AnomalyItem> anomalyItemList = anomalyItemRepository.findAll();
        assertThat(anomalyItemList).hasSize(databaseSizeBeforeUpdate);
        AnomalyItem testAnomalyItem = anomalyItemList.get(anomalyItemList.size() - 1);
        assertThat(testAnomalyItem.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testAnomalyItem.getMachine()).isEqualTo(UPDATED_MACHINE);
        assertThat(testAnomalyItem.getComent()).isEqualTo(UPDATED_COMENT);
        assertThat(testAnomalyItem.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testAnomalyItem.getSoundclip()).isEqualTo(UPDATED_SOUNDCLIP);
        assertThat(testAnomalyItem.getSoundData()).isEqualTo(UPDATED_SOUND_DATA);
        assertThat(testAnomalyItem.getSoundDataContentType()).isEqualTo(UPDATED_SOUND_DATA_CONTENT_TYPE);
        assertThat(testAnomalyItem.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAnomalyItem.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingAnomalyItem() throws Exception {
        int databaseSizeBeforeUpdate = anomalyItemRepository.findAll().size();

        // Create the AnomalyItem
        AnomalyItemDTO anomalyItemDTO = anomalyItemMapper.toDto(anomalyItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnomalyItemMockMvc.perform(put("/api/anomaly-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(anomalyItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnomalyItem in the database
        List<AnomalyItem> anomalyItemList = anomalyItemRepository.findAll();
        assertThat(anomalyItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnomalyItem() throws Exception {
        // Initialize the database
        anomalyItemRepository.saveAndFlush(anomalyItem);

        int databaseSizeBeforeDelete = anomalyItemRepository.findAll().size();

        // Delete the anomalyItem
        restAnomalyItemMockMvc.perform(delete("/api/anomaly-items/{id}", anomalyItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnomalyItem> anomalyItemList = anomalyItemRepository.findAll();
        assertThat(anomalyItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
