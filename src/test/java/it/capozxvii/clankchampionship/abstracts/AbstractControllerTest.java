package it.capozxvii.clankchampionship.abstracts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.capozxvii.clankchampionship.service.impl.ChampionshipService;
import it.capozxvii.clankchampionship.service.impl.PlayerService;
import it.capozxvii.clankchampionship.service.impl.PointsService;
import it.capozxvii.clankchampionship.service.impl.PrevisionService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class AbstractControllerTest extends AbstractTest {

    protected static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    protected MockMvc mvc;

    @MockBean
    protected ChampionshipService championshipService;

    @MockBean
    protected PlayerService playerService;

    @MockBean
    protected PointsService pointsService;

    @MockBean
    protected PrevisionService previsionService;

    @BeforeAll
    public static void setup() throws Exception {
        MAPPER.registerModule(new JavaTimeModule());
    }
}
