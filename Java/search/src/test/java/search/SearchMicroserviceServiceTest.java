package search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchMicroserviceServiceTest {

    @Mock
    private MicroserviceRepository microserviceRepository;
    @InjectMocks
    private SearchMicroserviceService searchMicroserviceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetMicroservicesByCategory_Success() {
        // Arrange
        String category = "test-category";
        List<Microservice> microservices = new ArrayList<>();
        Microservice ms1 = new Microservice();
        Microservice ms2 = new Microservice();

        ms1.setName("ms1");
        ms1.setDescription("description1");
        ms1.setCategory("test-category");

        ms2.setName("ms2");
        ms2.setDescription("description2");
        ms2.setCategory("test-category");

        microservices.add(ms1);
        microservices.add(ms2);

        when(microserviceRepository.findDistinctByCategory(category)).thenReturn(microservices);

        // Act
        List<Microservice> result = searchMicroserviceService.getMicroservicesByCategory(category);

        // Assert
        assertEquals(2, result.size());
        assertEquals("ms1", result.get(0).getName());
        assertEquals("ms2", result.get(1).getName());
    }

    @Test
    void testGetMicroservicesByCategory_NoResults() {
        // Arrange
        String category = "test-category";

        when(microserviceRepository.findDistinctByCategory(category)).thenReturn(new ArrayList<>());

        // Act
        List<Microservice> result = searchMicroserviceService.getMicroservicesByCategory(category);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetMicroservices() {
        // Arrange
        List<Microservice> microservices = new ArrayList<>();
        Microservice ms1 = new Microservice();
        Microservice ms2 = new Microservice();

        ms1.setName("ms1");
        ms1.setDescription("description1");
        ms1.setCategory("test-category1");

        ms2.setName("ms2");
        ms2.setDescription("description2");
        ms2.setCategory("test-category2");

        microservices.add(ms1);
        microservices.add(ms2);

        when(microserviceRepository.findAll()).thenReturn(microservices);

        // Act
        List<Microservice> result = searchMicroserviceService.getMicroservices();

        // Assert
        assertEquals(2, result.size());
        assertEquals("ms1", result.get(0).getName());
        assertEquals("ms2", result.get(1).getName());
    }

    @Test
    void testGetMicroservicesByNameOrCategory() {
        // Arrange
        String nameOrCategory = "test-category";
        List<Microservice> microservices = new ArrayList<>();
        Microservice ms1 = new Microservice();
        Microservice ms2 = new Microservice();

        ms1.setName("ms1");
        ms1.setDescription("description1");
        ms1.setCategory("test-category1");

        ms2.setName("ms2");
        ms2.setDescription("description2");
        ms2.setCategory("test-category");

        microservices.add(ms1);
        microservices.add(ms2);

        when(microserviceRepository.findDistinctByCategoryOrName(nameOrCategory, nameOrCategory)).thenReturn(microservices);

        // Act
        List<Microservice> result = searchMicroserviceService.getMicroservicesByNameOrCategory(nameOrCategory);

        // Assert
        assertEquals(2, result.size());
        assertEquals("ms1", result.get(0).getName());
        assertEquals("ms2", result.get(1).getName());
    }

    @Test
    void testGetMicroservicesByName() {
        // Arrange
        String name = "test-name";
        List<Microservice> microservices = new ArrayList<>();
        Microservice ms1 = new Microservice();
        Microservice ms2 = new Microservice();

        ms1.setName("test-name1");
        ms1.setDescription("description1");
        ms1.setCategory("test-category1");

        ms2.setName("test-name");
        ms2.setDescription("description2");
        ms2.setCategory("test-category2");

        microservices.add(ms1);
        microservices.add(ms2);

        when(microserviceRepository.findDistinctByName(name)).thenReturn(microservices);

        // Act
        List<Microservice> result = searchMicroserviceService.getMicroservicesByName(name);

        // Assert
        assertEquals(2, result.size());
        assertEquals("test-name1", result.get(0).getName());
        assertEquals("test-name", result.get(1).getName());
    }
    @Test
    void testGetMicroservicesByCategory() {
        // Arrange
        String category = "test-category";
        List<Microservice> microservices = new ArrayList<>();
        Microservice ms1 = new Microservice();
        Microservice ms2 = new Microservice();

        ms1.setName("ms1");
        ms1.setDescription("description1");
        ms1.setCategory("test-category1");

        ms2.setName("ms2");
        ms2.setDescription("description2");
        ms2.setCategory("test-category");

        microservices.add(ms1);
        microservices.add(ms2);

        when(microserviceRepository.findDistinctByCategory(category)).thenReturn(microservices);

        // Act
        List<Microservice> result = searchMicroserviceService.getMicroservicesByCategory(category);

        // Assert
        assertEquals(2, result.size());
        assertEquals("ms1", result.get(0).getName());
        assertEquals("ms2", result.get(1).getName());
    }

    /*@Test
    void testGetMicroservicesWithoutDependencies() {
        // Arrange
        List<Microservice> microservices = new ArrayList<>();
        Microservice ms1 = new Microservice();
        Microservice ms2 = new Microservice();
        Microservice ms3 = new Microservice();

        ms1.setName("ms1");
        ms1.setDescription("description1");
        ms1.setCategory("test-category1");
        ms1.setDependencies(Arrays.asList("ms2", "ms3"));

        ms2.setName("ms2");
        ms2.setDescription("description2");
        ms2.setCategory("test-category2");

        ms3.setName("ms3");
        ms3.setDescription("description3");
        ms3.setCategory("test-category3");

        microservices.add(ms1);
        microservices.add(ms2);
        microservices.add(ms3);

        when(microserviceRepository.findAll()).thenReturn(microservices);

        // Act
        List<Microservice> result = searchMicroserviceService.getMicroservicesWithoutDependencies();

        // Assert
        assertEquals(1, result.size());
        assertEquals("ms2", result.get(0).getName());
    }*/
   /* @Test
    public void testGetMicroservicePage() {
        // create test data
        Microservice ms1 = new Microservice();
        ms1.setName("test-service");
        ms1.setVersion(1);

        Microservice ms2 = new Microservice();
        ms2.setName("test-service");
        ms2.setVersion(2);

        List<Microservice> matchingServices = new ArrayList<>();
        matchingServices.add(ms1);
        matchingServices.add(ms2);

        // mock repository method
        when(microserviceRepository.findDistinctByNameAndVersion(eq("test-service"), eq(1)))
                .thenReturn(matchingServices);

        // call method to test
        var result = searchMicroserviceService.getMicroservicePage("test-service", 1);

        // assert results
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey(ms1));

        HashMap<List, List> dependencies = result.get(ms1);
        assertNotNull(dependencies);
        assertEquals(1, dependencies.size());

        List<Microservice> expectedDeps = new ArrayList<>();
        expectedDeps.add(new Microservice()); // create sample dep
        List<Integer> expectedDepVersions = new ArrayList<>();
        expectedDepVersions.add(1);
        expectedDepVersions.add(2);

        assertEquals(Collections.singletonList(expectedDeps), new ArrayList<>(dependencies.keySet()));
        assertEquals(Collections.singletonList(expectedDepVersions), new ArrayList<>(dependencies.values()));
    }*/
    @Test
    void testGetMicroservicesByNameAndCategory() {
        // Arrange
        String name = "test-name";
        String category = "test-category";
        List<Microservice> microservices = new ArrayList<>();
        Microservice ms1 = new Microservice();
        Microservice ms2 = new Microservice();

        ms1.setName("test-name1");
        ms1.setDescription("description1");
        ms1.setCategory("test-category1");

        ms2.setName("test-name");
        ms2.setDescription("description2");
        ms2.setCategory("test-category");

        microservices.add(ms1);
        microservices.add(ms2);

        when(microserviceRepository.findDistinctByNameAndCategory(name, category)).thenReturn(microservices);

        // Act
        List<Microservice> result = searchMicroserviceService.getMicroservicesByNameAndCategory(name, category);

        // Assert
        assertEquals(2, result.size());
        assertEquals("test-name1", result.get(0).getName());
        assertEquals("test-name", result.get(1).getName());
    }
    @Test
    void testGetSpecification_WithNameOnly() {
        // Arrange
        String name = "test-name";
        Microservice ms1 = new Microservice();
        ms1.setName("test-name");
        ms1.setDescription("test-description");
        ms1.setCategory("test-category");
        ms1.setVersion(1);
        ms1.setSpecification("test-specification");

        when(microserviceRepository.findDistinctByName(name)).thenReturn(Arrays.asList(ms1));

        // Act
        String result = searchMicroserviceService.getSpecification(name);

        // Assert
        assertEquals(ms1.getSpecification(), result);
    }

    @Test
    void testGetSpecification_WithNameAndVersion() {
        // Arrange
        String name = "test-name";
        Integer version = 1;
        Microservice ms1 = new Microservice();
        ms1.setName("test-name");
        ms1.setDescription("test-description");
        ms1.setCategory("test-category");
        ms1.setVersion(1);
        ms1.setSpecification("test-specification");

        when(microserviceRepository.findDistinctByNameAndVersion(name, version)).thenReturn(Arrays.asList(ms1));

        // Act
        String result = searchMicroserviceService.getSpecification(name, version);

        // Assert
        assertEquals(ms1.getSpecification(), result);
    }

}