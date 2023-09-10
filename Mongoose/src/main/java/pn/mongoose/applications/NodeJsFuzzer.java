package pn.mongoose.applications;

import pn.mongoose.applications.base.ApplicationBase;
import pn.mongoose.applications.model.ApplicationResponse;

import java.net.HttpURLConnection;
import java.net.URL;


public class NodeJsFuzzer extends ApplicationBase {
    public NodeJsFuzzer(String[] args) {
        super(args);
    }

    private String randomString(String capacity) {
        StringBuilder sb = new StringBuilder(capacity);
        for (int i = 0; i < 5; i++) {
            sb.append((char) (97 + (int) (Math.random() * 25)));
        }
        return sb.toString();
    }

    @Override
    protected void RunApplication(String... args) {

        try {
            String targetUrl = args[0];
            URL url = new URL(targetUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(20000);
             conn.connect();
            for (int i = 1; i <= 30; i++) {
                URL url2 = new URL(targetUrl + randomString(args[1]));
                conn = (HttpURLConnection) url2.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(20000);
                conn.connect();
                System.out.println("Response Code: " + conn.getResponseCode() + " -- Response Message: " + conn.getResponseMessage());
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        this.RunApplication(args);
    }
}