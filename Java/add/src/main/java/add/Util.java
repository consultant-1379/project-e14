package add;

import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.*;

public class Util {
    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isInteger(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static ArrayList<String> parseDependencies(String input) {
        String[] dependencyArray = input.split(",");
        ArrayList<String> dependenciesList = new ArrayList<>();

        for (String dependency : dependencyArray) {
            String trimmedDependency = dependency.trim();
            if (!trimmedDependency.isEmpty()) {
                dependenciesList.add(trimmedDependency);
            }
        }

        return dependenciesList;
    }

    public static String convertYAMLToString(MultipartFile file) throws IOException {
        return new String(file.getBytes(), StandardCharsets.UTF_8);
    }
}
