package add;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MicroserviceRepository extends MongoRepository<Microservice, String> {
    Microservice findByNameAndVersion(String name, int version);
}
