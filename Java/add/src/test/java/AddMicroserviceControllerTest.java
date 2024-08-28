import add.AddMicroserviceController;
import add.AddMicroserviceService;
import add.Microservice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class AddMicroserviceControllerTest {

    @InjectMocks
    private AddMicroserviceController addMicroserviceController;

    @Mock
    private AddMicroserviceService addMicroserviceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddNewMicroservice_Success() throws Exception {
        // mock a successful service call
        when(addMicroserviceService.saveMicroservice(anyString(), anyString(), anyString(), anyString(), anyString(), anyInt(), any(ArrayList.class), anyString()))
                .thenReturn(new Microservice());

        // mock a valid YAML file
        MockMultipartFile file = new MockMultipartFile("file", "test.yaml", "text/plain", "YAML content".getBytes());

        ResponseEntity<String> response = addMicroserviceController.addNewMicroservice(
                "TestMicroservice",
                "TestCategory",
                "Test Engineer",
                "test@example.com",
                "Description",
                "1",
                "Dependency-1, Dependency-2",
                file
        );

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Microservice Created.", response.getBody());
    }

    @Test
    public void testAddNewMicroservice_BadRequest() throws Exception {
        // mock a case where the request is invalid (for example, missing required parameters)

        ResponseEntity<String> response = addMicroserviceController.addNewMicroservice(
                "",  // missing 'name'
                "TestCategory",
                "Engineer",
                "test@example.com",
                "Description",
                "1",
                "-",
                null
        );

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("All Fields Are Required.", response.getBody());
    }

    @Test
    public void testAddNewMicroservice_InvalidEmail() throws Exception {
        // mock a case where the email address is invalid

        ResponseEntity<String> response = addMicroserviceController.addNewMicroservice(
                "TestMicroservice",
                "TestCategory",
                "Test Engineer",
                "invalid-email",  // invalid email
                "Description",
                "1",
                "-",
                null
        );

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid Email Address.", response.getBody());
    }

    @Test
    public void testAddNewMicroservice_InvalidVersion() throws Exception {
        // mock a case where the version is not a valid integer

        ResponseEntity<String> response = addMicroserviceController.addNewMicroservice(
                "TestMicroservice",
                "TestCategory",
                "Test Engineer",
                "test@example.com",
                "Description",
                "invalid-version",  // invalid version
                "-",
                null
        );

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Version Number Must Be an Integer.", response.getBody());
    }

    @Test
    public void testAddNewMicroservice_InvalidSpecificationFile() throws Exception {
        // mock a case where the specification file is not YAML
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Text content".getBytes());

        ResponseEntity<String> response = addMicroserviceController.addNewMicroservice(
                "TestMicroservice",
                "TestCategory",
                "Test Engineer",
                "test@example.com",
                "Description",
                "1",
                "-",
                file
        );

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Specification Must Be a YAML File.", response.getBody());
    }
}
