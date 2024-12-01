package it.capozxvii.clankchampionship.abstracts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.capozxvii.clankchampionship.service.IChampionshipService;
import it.capozxvii.clankchampionship.service.IGameService;
import it.capozxvii.clankchampionship.service.IPlayerService;
import it.capozxvii.clankchampionship.service.IPointsService;
import it.capozxvii.clankchampionship.service.IPrevisionService;
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
    protected IChampionshipService championshipService;

    @MockBean
    protected IGameService gameService;

    @MockBean
    protected IPlayerService playerService;

    @MockBean
    protected IPointsService pointsService;

    @MockBean
    protected IPrevisionService previsionService;

    @BeforeAll
    public static void setup() throws Exception {
        MAPPER.registerModule(new JavaTimeModule());
    }
}
