package pn.mongoose.applications;

import pn.mongoose.applications.base.ApplicationBase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;

public class CredentialHarvester extends ApplicationBase {

    public CredentialHarvester(String[] args) {
        super(args);
    }

    private Map<String, String> harvestCredentials(URL url, List<String> childPaths) throws MalformedURLException {
        Map<String, String> harvestedCredentials = new HashMap<>();

        // Iterate through the list of child paths
        for (String childPath : childPaths) {

            URL target = new URL(url, childPath);
            System.out.printf("Scanning : %s\n", childPath);
            try {
                // Try to connect to the URL, and if the connection is successful, fetch the credentials
                HttpURLConnection connection = (HttpURLConnection) target.openConnection();
                String inputLine;
                String[] inputArray;

                // Read the response line by line
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                        while ((inputLine = reader.readLine()) != null) {
                            // Look for lines with key-value patterns
                            inputArray = inputLine.split("=");
                            if (inputArray.length >= 2) {
                                // Store key-values in the map
                                harvestedCredentials.put(inputArray[0], inputArray[1]);
                                System.out.println();
                                if (inputArray[0].equalsIgnoreCase("password") || inputArray[0].equalsIgnoreCase("user"
                                ) || inputArray[0].equalsIgnoreCase("secret") || inputArray[0].equalsIgnoreCase("author")
                                        || inputArray[1].equalsIgnoreCase("password") || inputArray[1].equalsIgnoreCase("user")
                                        || inputArray[1].equalsIgnoreCase("secret") || inputArray[1].equalsIgnoreCase("author")) {
                                    System.out.printf("Credentials found!\n%s : %s", inputArray[0], inputArray[1]);
                                }
                                System.out.printf("Key/value pair found!\n%s : %s", inputArray[0], inputArray[1]);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Connection to " + target + " failed.");
            }
        }

        return harvestedCredentials;
    }

    @Override
    protected void RunApplication(String... args) {

        URL url;

        List<String> childPaths = new LinkedList<>();

        try {
            url = new URL(args[0]);

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        childPaths.addAll(Arrays.asList(args).subList(1, args.length));
        try {
            Map<String, String> credentialResult = harvestCredentials(url, childPaths);

            System.out.println();
            System.out.printf("Credentials run successful: %s", !credentialResult.isEmpty());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {

        RunApplication(args);
    }
}