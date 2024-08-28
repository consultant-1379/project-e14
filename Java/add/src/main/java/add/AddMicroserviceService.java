package add;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AddMicroserviceService {
    @Autowired
    private MicroserviceRepository microserviceRepository;

    public Microservice saveMicroservice(String name,
                                         String category,
                                         String leadEngineerName,
                                         String leadEngineerEmail,
                                         String description,
                                         int version,
                                         ArrayList<String> dependencies,
                                         String specification) throws Exception {

        Microservice existingMicroservice = microserviceRepository.findByNameAndVersion(name, version);

        if (existingMicroservice != null) {
            throw new Exception("This Microservice Already Exists.");
        }

        Microservice microservice = new Microservice();

        microservice.setName(name);
        microservice.setCategory(category);
        microservice.setLeadEngineerName(leadEngineerName);
        microservice.setLeadEngineerEmail(leadEngineerEmail);
        microservice.setDescription(description);
        microservice.setVersion(version);
        microservice.setCreationDate();
        microservice.setDependencies(dependencies);
        microservice.setSpecification(specification);

        return microserviceRepository.save(microservice);
    }

}
