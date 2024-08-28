package add;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "microservices")
public class Microservice {
    @Id
    private String microserviceID;
    private String name;
    private String category;
    private String leadEngineerName;
    private String leadEngineerEmail;
    private String description;
    private int version;
    private Date creationDate;
    private List<String> dependencies;
    private String specification;

    public String getMicroserviceID() {
        return microserviceID;
    }

    public void setMicroserviceID(String microserviceID) {
        this.microserviceID = microserviceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLeadEngineerName() {
        return leadEngineerName;
    }

    public void setLeadEngineerName(String leadEngineerName) {
        this.leadEngineerName = leadEngineerName;
    }

    public String getLeadEngineerEmail() {
        return leadEngineerEmail;
    }

    public void setLeadEngineerEmail(String leadEngineerEmail) {
        this.leadEngineerEmail = leadEngineerEmail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate() {
        this.creationDate = new Date();
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<String> dependencies) {
        this.dependencies = dependencies;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }
}
