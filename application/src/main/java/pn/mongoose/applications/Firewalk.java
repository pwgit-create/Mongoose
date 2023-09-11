package pn.mongoose.applications;

import pn.mongoose.applications.base.ApplicationBase;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class Firewalk extends ApplicationBase {


    public Firewalk(String[] args) {
        super(args);
    }

    @Override
    protected void RunApplication(String... args) {

        String ipAddress = args[0];
        String startPort = args[1];
        String endPort = args[2];


        // Convert the port interval into a list of integers
        List<Integer> portInterval = convertPortIntervalFromArgsToList(startPort, endPort);

        // Could not parse port interval
        if (portInterval.isEmpty())
            return;
        // Defines a list that will hold the elements who´s ports are open
        List<Integer> openPortsResult = new LinkedList<>();

        InputStream is = null;
        OutputStream os = null;

        // Declare and instantiate a socket object connected to given address and port
        Socket s = null;

        for (Integer port : portInterval) {

            //Skipping to the next port if an exception happens within the for loop

            boolean skipToNextPortInLoop = false;
            System.out.println();
            System.out.printf("Scanning port: %d", port);
            try {
                s = new Socket(InetAddress.getByName(ipAddress), port);
            } catch (IOException e) {
                System.out.println(" Could not connect to host");
                skipToNextPortInLoop = true;
            }

            if (!skipToNextPortInLoop) {

                // Get the input/output byte streams to send/receive the data in bytes
                try {
                    is = s.getInputStream();
                } catch (IOException e) {
                    System.out.println("Error: Can´t get the input stream");
                    skipToNextPortInLoop = true;
                }

                try {
                    os = s.getOutputStream();
                } catch (IOException e) {
                    System.out.println("Error: Can´t get the output stream");
                    skipToNextPortInLoop = true;
                }
            }
            if (!skipToNextPortInLoop) {
                // Create a byte array to depicts the traffic sent/received to/from the host
                byte[] received = new byte[1024];

                // Reads the traffic from the host specified with the IP-address and port
                try {
                    is.read(received);
                    System.out.println(" ## Port is open!");
                    openPortsResult.add(port);
                } catch (IOException e) {
                    System.out.println("Can´t read the traffic from the host specified");
                }

                // Prints out the traffic received from the host
                String strReceived = new String(received, StandardCharsets.UTF_8);
                System.out.println(strReceived);

            }
        }

        // Print final result
        System.out.println();
        System.out.printf("Open ports on host %s:", ipAddress);
        System.out.println();
        openPortsResult.forEach(System.out::println);

        // Release resources
        try {
            assert is != null;
            is.close();
            assert os != null;
            os.close();
            s.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void run() {

        RunApplication(args);
    }

    private List<Integer> convertPortIntervalFromArgsToList(String startPort, String endPort) {

        int startPortInterval;
        int endPortInterval;

        List<Integer> portInterval = new LinkedList<>();

        try {
            startPortInterval = Integer.parseInt(startPort);
            endPortInterval = Integer.parseInt(endPort);

            for (int i = startPortInterval; i < endPortInterval + 1; i++) {

                portInterval.add(i);

            }
        } catch (Exception e) {

            System.out.println("Parsing error: port interval");
            return new LinkedList<>();
        }

        return portInterval;
    }
}