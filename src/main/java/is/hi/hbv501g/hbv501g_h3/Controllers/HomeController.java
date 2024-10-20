package is.hi.hbv501g.hbv501g_h3.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@RestController
public class HomeController {

    // Define the API descriptions for "patterns" endpoints
    private List<EndpointInfo> getPatternEndpoints() {
        List<EndpointInfo> patternEndpoints = new ArrayList<>();
        patternEndpoints.add(new EndpointInfo("/patterns", "GET", "Get a list of patterns or search/filter by public status, title, or username."));
        patternEndpoints.add(new EndpointInfo("/patterns/{id}", "GET", "Get a specific pattern by ID."));
        patternEndpoints.add(new EndpointInfo("/patterns", "POST", "Create a new pattern."));
        patternEndpoints.add(new EndpointInfo("/patterns/{id}", "PATCH", "Update a pattern by ID (patching specific fields)."));
        patternEndpoints.add(new EndpointInfo("/patterns/{id}", "DELETE", "Delete a pattern by ID."));
        return patternEndpoints;
    }

    // Define the API descriptions for "users" endpoints
    private List<EndpointInfo> getUserEndpoints() {
        List<EndpointInfo> userEndpoints = new ArrayList<>();
        userEndpoints.add(new EndpointInfo("/users", "GET", "Get a list of users or search by username."));
        userEndpoints.add(new EndpointInfo("/users/{id}", "GET", "Get a specific user by ID."));
        userEndpoints.add(new EndpointInfo("/users", "POST", "Create a new user."));
        userEndpoints.add(new EndpointInfo("/users/{id}", "PATCH", "Update a user by ID (patching specific fields)."));
        userEndpoints.add(new EndpointInfo("/users/{id}", "DELETE", "Delete a user by ID."));
        return userEndpoints;
    }

    // The API description with grouped endpoints
    @GetMapping("/")
    public Map<String, List<EndpointInfo>> apiDescription() {
        return Map.of(
                "patterns", getPatternEndpoints(),
                "users", getUserEndpoints()
        );
    }

    // A simple helper class to describe each endpoint
    public static class EndpointInfo {
        private String endpoint;
        private String method;
        private String description;

        public EndpointInfo(String endpoint, String method, String description) {
            this.endpoint = endpoint;
            this.method = method;
            this.description = description;
        }

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
