package search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class SearchMicroserviceController {

    @Autowired
    private SearchMicroserviceService searchMicroserviceService;

    public SearchMicroserviceController() {
    }

    @GetMapping("/api/search/")
    public List searchNewMicroservice(@RequestParam(required = false, defaultValue = "") String name, @RequestParam(required = false, defaultValue = "") String category, @RequestParam(required = false, defaultValue = "true") Boolean hasDependencies, @RequestParam(required = false, defaultValue = "true") Boolean allVersions) {
        List<Microservice> microservices;
        if (hasDependencies) {
            microservices = searchMicroserviceService.getMicroservices();
        } else {
            microservices = searchMicroserviceService.getMicroservicesWithoutDependencies();
        }

        if (allVersions) {
            microservices = microservices.stream()
                    .filter(microservice -> name.isEmpty() || microservice.getName().contains(name))
                    .filter(microservice -> category.isEmpty() || microservice.getCategory().contains(category))
                    .collect(Collectors.toList());
            return microservices;
        } else {
            List<Microservice> filteredMicroservices = microservices.stream()
                    .filter(microservice -> name.isEmpty() || microservice.getName().contains(name))
                    .filter(microservice -> category.isEmpty() || microservice.getCategory().contains(category))
                    .collect(Collectors.toList());

            Map<String, Microservice> microserviceMap = new HashMap<>();
            for (Microservice microservice : filteredMicroservices) {
                String microserviceName = microservice.getName();
                if (!microserviceMap.containsKey(microserviceName) || microservice.getVersion() > microserviceMap.get(microserviceName).getVersion()) {
                    microserviceMap.put(microserviceName, microservice);
                }
            }
            List<Microservice> result = new ArrayList<>(microserviceMap.values());
        return result;
        }
    }

    @GetMapping("/api/{service_name}/v{version}/")
    public List<List> getMicroservicePage(@PathVariable String service_name, @RequestParam(required = false) Integer version) {
        return searchMicroserviceService.getMicroservicePage(service_name, version);
    }

    @GetMapping("/api/{serviceName}/v{version}/openapi.yaml")
    public ResponseEntity<String> getYaml(@PathVariable("serviceName") String serviceName, @PathVariable("version") Integer version) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Yaml yaml = new Yaml();
        Object obj = yaml.load(searchMicroserviceService.getSpecification(serviceName, version));

        String yamlString = new String(mapper.writeValueAsBytes(obj));
        HttpHeaders headers = new HttpHeaders();
        //uncomment this line if you want the yaml to automatically download instead of display it in browswer.
        //headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=myfile.yaml");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/plain"))
                .body(yamlString);
    }


}
