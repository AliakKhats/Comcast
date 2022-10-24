import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ComcastExam {

    private static final String USER_PROMPT_MESSAGE = "Please enter a country name or code:";

    public static void main (String [] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(ComcastExam.USER_PROMPT_MESSAGE);
        while (scanner.hasNext()) {
            String userInput = scanner.next();
            String capital = ComcastExam.getCapitalByCountryName(userInput);
            if (capital == null) {
                capital = ComcastExam.getCapitalByCountryCode(userInput);
            }
            if (capital != null) {
                System.out.println(capital);
            } else {
                System.out.println("Country not found");
            }
            System.out.println(ComcastExam.USER_PROMPT_MESSAGE);
        }

    }

    private static String getCapitalByCountryName(String countryName) {
        try {
            URL url = new URL("https://restcountries.com/v3.1/name/" + countryName);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
                String content = reader.lines().collect(Collectors.joining());
                JSONArray resultArray = new JSONArray(content);
                JSONObject countryData = resultArray.getJSONObject(0);
                JSONArray capitalArray = countryData.getJSONArray("capital");
                return capitalArray.getString(0);
            }
        } catch (Exception e) {
            return null;
        }
    }

    private static String getCapitalByCountryCode(String countryCode) {
        try {
            URL url = new URL("https://restcountries.com/v2/alpha/" + countryCode);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
                String content = reader.lines().collect(Collectors.joining());
                JSONObject countryData = new JSONObject(content);
                JSONArray capitalArray = countryData.getJSONArray("capital");
                return capitalArray.getString(0);
            }
        } catch (Exception e) {
           return null;
        }
    }
}
