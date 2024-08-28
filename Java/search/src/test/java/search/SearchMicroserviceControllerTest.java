package search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SearchMicroserviceControllerTest {
    @Mock
    private SearchMicroserviceService searchMicroserviceService;

    @InjectMocks
    private SearchMicroserviceController microserviceController;

    @BeforeEach
    public void setUP() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMicroServicePageWithVersion() {
        String serviceName = "sample-service";

        Microservice Test1 = new Microservice();
        Microservice Test2 = new Microservice();
        Test1.setName(serviceName);
        Test1.setVersion(1);
        Test2.setName(serviceName);
        Test2.setVersion(2);

        List<Microservice> matchingServices = new ArrayList<>();
        List<Microservice> testDeps = new ArrayList<>();
        List<Integer> testInts = new ArrayList<>();
        testDeps.add(Test2);
        testInts.add(1);
        matchingServices.add(Test1);
        matchingServices.add(Test2);

        int latestVersionNumber = 2;

        when(microserviceController.getMicroservicePage(serviceName, null)).thenReturn(Collections.singletonList(matchingServices));

        List<List> result= new ArrayList<>();
        result.add(microserviceController.getMicroservicePage(serviceName, null));
        result.add(testDeps);
        result.add(testInts);


        List<Integer> actualVersions = new ArrayList<>();

        List<Integer> testIntsResults = result.get(2);
        for (int num : testIntsResults) {
            System.out.println(num);
            actualVersions.add(num);
        }

        assertFalse(result.isEmpty());
    }

    @Test
    public void testSearchNewMicroserviceGetMicroservices() {
        // Given
        List<Microservice> microservices = new ArrayList<>();
        Microservice microservice1 = new Microservice();
        Microservice microservice2 = new Microservice();
        microservice1.setName("Microservice");
        microservice1.setCategory("category1");
        microservice2.setName("Microservice");
        microservice2.setCategory("category2");
        microservices.add(microservice1);
        microservices.add(microservice2);
        //when(microserviceController.searchNewMicroservice("Microservice", "category1", true)).thenReturn(microservices);
        when(searchMicroserviceService.getMicroservices()).thenReturn(microservices);

        // When
        List result = microserviceController.searchNewMicroservice("Microservice", "category1", true,true);

        // Then
        assertEquals(1, result.size());


    }

    @Test
    public void testSearchNewMicroserviceGetMicroservicesWithoutDependencies() {
        // Given
        List<Microservice> microservices = new ArrayList<>();
        Microservice microservice1 = new Microservice();
        Microservice microservice2 = new Microservice();
        microservice1.setName("Microservice");
        microservice1.setCategory("category1");
        microservice2.setName("Microservice");
        microservice2.setCategory("category2");
        microservices.add(microservice1);
        microservices.add(microservice2);


        when(searchMicroserviceService.getMicroservicesWithoutDependencies()).thenReturn(microservices);

        List result = microserviceController.searchNewMicroservice("Microservice", "", false,true);

        assertEquals(2, result.size());

    }

    @Test
    public void getYamlTest() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Yaml yaml = new Yaml();
        String serviceName="sample-service";
        int version = 1;
        String filename = "myfile";

        when(searchMicroserviceService.getSpecification(anyString(),anyInt())).thenReturn(
                "---\n" +
                        " doe: \"a deer, a female deer\"\n" +
                        " ray: \"a drop of golden sun\"\n" +
                        " pi: 3.14159\n" +
                        " xmas: true\n" +
                        " french-hens: 3\n" +
                        " calling-birds:\n" +
                        "   - huey\n" +
                        "   - dewey\n" +
                        "   - louie\n" +
                        "   - fred\n" +
                        " xmas-fifth-day:\n" +
                        "   calling-birds: four\n" +
                        "   french-hens: 3\n" +
                        "   golden-rings: 5\n" +
                        "   partridges:\n" +
                        "     count: 1\n" +
                        "     location: \"a pear tree\"\n" +
                        "   turtle-doves: two\n"
        );

        ResponseEntity<String> responseEntityResult=microserviceController.getYaml(serviceName,version);

        assertEquals(HttpStatus.OK, responseEntityResult.getStatusCode());
        String bodyOfResponseEntity= responseEntityResult.getBody();
        assertNotNull(bodyOfResponseEntity);
    }
}