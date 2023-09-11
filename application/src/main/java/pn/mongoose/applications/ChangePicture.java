package pn.mongoose.applications;

import pn.mongoose.applications.base.ApplicationBase;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLConnection;

public class ChangePicture extends ApplicationBase {
    public ChangePicture(String[] args) {
        super(args);
    }

    @Override
    protected void RunApplication(String... args) {

        System.out.println("""
                You need to find a remote picture url with minimal http headers for this to work :P
                This option is under construction.""");


        //URL of the remote picture
        String remoteUrl = args[0];

        //Location of the local image
        String localFilePath = args[1];

        try {
            // Get a connection to the remote picture
            URLConnection conn = new URL(remoteUrl).openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //Copy the contents of the input stream to the local file

            File file = new File(localFilePath);
            //Replacing remote picture with local picture
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[1024];
            int i;
            while ((i = fis.read(data)) != -1) {
                conn.getOutputStream().write(data, 0, i);

            }

            System.out.println("Done. Under construction....");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        RunApplication(args);
    }
}
