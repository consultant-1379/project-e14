package search;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MicroserviceRepository extends MongoRepository<Microservice, String> {

    List<Microservice> findDistinctByCategoryOrName(String category, String serviceName);

    List<Microservice> findDistinctByName(String serviceName);

    List<Microservice> findDistinctByCategory(String category);

    List<Microservice> findDistinctByNameAndCategory(String name, String category);

    List<Microservice> findDistinctByNameAndVersion(String name, Integer version);
}