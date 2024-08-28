package add;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@RestController
public class AddMicroserviceController {
    @Autowired
    private AddMicroserviceService addMicroserviceService;

    @PostMapping(value="/api/add", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addNewMicroservice(
            @RequestPart("name") String name,
            @RequestPart("category") String category,
            @RequestPart("leadEngineerName") String leadEngineerName,
            @RequestPart("leadEngineerEmail") String leadEngineerEmail,
            @RequestPart("description") String description,
            @RequestPart("version") String version,
            @RequestPart("dependencies") String dependencies,
            @RequestPart("specification") MultipartFile file) throws Exception {

        if (name.isEmpty() || category.isEmpty() || leadEngineerName.isEmpty() || leadEngineerEmail.isEmpty() || description.isEmpty() || version.isEmpty()) {
            return ResponseEntity.badRequest().body("All Fields Are Required.");
        }

        if (!Util.isValidEmail(leadEngineerEmail)) {
            return ResponseEntity.badRequest().body("Invalid Email Address.");
        }

        if (!Util.isInteger(version)) {
            return ResponseEntity.badRequest().body("Version Number Must Be an Integer.");
        }

        if (!file.getOriginalFilename().endsWith(".yaml")) {
            return ResponseEntity.badRequest().body("Specification Must Be a YAML File.");
        }

        ArrayList<String> dependenciesList = new ArrayList<>();

        if (!(dependencies.isEmpty() || dependencies.equals("-") || dependencies.equalsIgnoreCase("N/A"))) {
            dependenciesList = Util.parseDependencies(dependencies);
        }

        String specification = Util.convertYAMLToString(file);

        try {
            addMicroserviceService.saveMicroservice(
                    name.trim(),
                    category.trim(),
                    leadEngineerName.trim(),
                    leadEngineerEmail.trim(),
                    description.trim(),
                    Integer.parseInt(version),
                    dependenciesList,
                    specification);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        return ResponseEntity.ok().body("Microservice Created.");
    }
}
