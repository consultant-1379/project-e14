package search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchMicroserviceService {
    @Autowired
    private MicroserviceRepository microserviceRepository;

    public List getMicroservices() {

        return microserviceRepository.findAll();
    }

    public List getMicroservicesByNameOrCategory(String nameOrCategory) {
        //May look strange, but this seems to be the cleanest way to return a distinct list that searches for both names and categories
        return microserviceRepository.findDistinctByCategoryOrName(nameOrCategory, nameOrCategory);
    }
    public List getMicroservicesByName(String name) {
        return microserviceRepository.findDistinctByName(name);
    }
    public List getMicroservicesByCategory(String category) {
        return microserviceRepository.findDistinctByCategory(category);
    }
    public List getMicroservicesWithoutDependencies() {
        return microserviceRepository.findAll().stream().filter(microservice -> microservice.getDependencies().isEmpty()).collect(Collectors.toList());
    }

    public List<List> getMicroservicePage(String serviceName, Integer version) {
        List<Microservice> matchingServices;
        List<Microservice> allServices = microserviceRepository.findDistinctByName(serviceName);
        if (version==null){
            matchingServices = microserviceRepository.findDistinctByName(serviceName);
        }else {
            matchingServices = microserviceRepository.findDistinctByNameAndVersion(serviceName,version);
        }
        List<Integer> allVersions = new ArrayList<>();
        for (Microservice service : allServices) {
            Integer tempVersion = service.getVersion();
            allVersions.add(tempVersion);
        }

        List<Microservice> dependencies = grabDependencies(matchingServices.get(0));

        List<List> results = new ArrayList<>();
        results.add(matchingServices);
        results.add(dependencies);
        results.add(allVersions);
        return results;
    }

    private List<Microservice> grabDependencies(Microservice resultMicroservice) {
        List<String> dependencies = resultMicroservice.getDependencies();
        List<Microservice> dependencyInfoList = new ArrayList<>();

        for (String d : dependencies) {
            Optional<Microservice> latestVersionDependency = microserviceRepository.findDistinctByName(d).stream().max(Comparator.comparing(Microservice::getVersion));

            if (latestVersionDependency.isPresent()) {
                Microservice latestVersionMicroservice = latestVersionDependency.get();
                dependencyInfoList.add(latestVersionMicroservice);
            }
        }
        //adding all dependencies to the list.
        return dependencyInfoList;
    }

    public List<Microservice> getMicroservicesByNameAndCategory(String name, String category) {
        return microserviceRepository.findDistinctByNameAndCategory(name,category);
    }

    public String getSpecification(String name) {
        return microserviceRepository.findDistinctByName(name).get(0).getSpecification();
    }
    public String getSpecification(String name,Integer version) {
        return microserviceRepository.findDistinctByNameAndVersion(name,version).get(0).getSpecification();
    }
}
