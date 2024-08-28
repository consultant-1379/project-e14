import add.Microservice;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class MicroserviceTest {

    @Test
    public void testGetterAndSetterMethods() {
        Microservice microservice = new Microservice();

        microservice.setMicroserviceID("123");
        microservice.setName("TestMicroservice");
        microservice.setCategory("TestCategory");
        microservice.setLeadEngineerName("Test Engineer");
        microservice.setLeadEngineerEmail("test@example.com");
        microservice.setDescription("Description");
        microservice.setVersion(1);
        microservice.setCreationDate();
        List<String> dependencies = new ArrayList<>();
        dependencies.add("Dependency1");
        dependencies.add("Dependency2");
        microservice.setDependencies(dependencies);
        microservice.setSpecification("YAML Specification");

        assertEquals("123", microservice.getMicroserviceID());
        assertEquals("TestMicroservice", microservice.getName());
        assertEquals("TestCategory", microservice.getCategory());
        assertEquals("Test Engineer", microservice.getLeadEngineerName());
        assertEquals("test@example.com", microservice.getLeadEngineerEmail());
        assertEquals("Description", microservice.getDescription());
        assertEquals(1, microservice.getVersion());
        assertNotNull(microservice.getCreationDate());
        assertEquals(dependencies, microservice.getDependencies());
        assertEquals("YAML Specification", microservice.getSpecification());
    }

    @Test
    public void testSetCreationDate() {
        Microservice microservice = mock(Microservice.class);
        microservice.setCreationDate();

        verify(microservice, times(1)).setCreationDate();
    }
}
