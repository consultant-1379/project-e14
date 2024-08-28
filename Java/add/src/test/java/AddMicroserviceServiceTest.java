import add.AddMicroserviceService;
import add.Microservice;
import add.MicroserviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class AddMicroserviceServiceTest {

    @InjectMocks
    private AddMicroserviceService addMicroserviceService;

    @Mock
    private MicroserviceRepository microserviceRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveMicroservice_NewMicroservice() throws Exception {
        String name = "new-microservice";
        int version = 1;
        ArrayList<String> dependencies = new ArrayList<>();
        dependencies.add("Dependency-1");
        dependencies.add("Dependency-2");

        Mockito.when(microserviceRepository.findByNameAndVersion(name, version)).thenReturn(null);
        Microservice savedMicroservice = new Microservice();
        savedMicroservice.setName(name);
        savedMicroservice.setVersion(version);
        Mockito.when(microserviceRepository.save(any(Microservice.class))).thenReturn(savedMicroservice);

        Microservice result = addMicroserviceService.saveMicroservice(name, "Category", "EngineerName", "EngineerEmail", "Description", version, dependencies, "Specification");

        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(version, result.getVersion());
    }

    @Test
    public void testSaveMicroservice_ExistingMicroservice() {
        String name = "existing-microservice";
        int version = 1;
        Microservice existingMicroservice = new Microservice();
        ArrayList<String> dependencies = new ArrayList<>();
        dependencies.add("Dependency-1");
        dependencies.add("Dependency-2");

        Mockito.when(microserviceRepository.findByNameAndVersion(name, version)).thenReturn(existingMicroservice);
        assertThrows(Exception.class, () -> addMicroserviceService.saveMicroservice(name, "Category", "EngineerName", "EngineerEmail", "Description", version, dependencies, "Specification"));
    }
}
