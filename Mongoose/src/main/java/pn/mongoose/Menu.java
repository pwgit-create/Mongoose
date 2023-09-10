package pn.mongoose;

import pn.mongoose.applications.NodeJsFuzzer;
import pn.mongoose.applications.SipNullByte;
import pn.mongoose.constant.ConstantStrings;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class Menu {


    public Menu() {
    }

    private ThreadPoolExecutor executor =
            (ThreadPoolExecutor) Executors.newCachedThreadPool();

    private Scanner scanner = new Scanner(System.in);

    public void printMenu() throws InterruptedException {

        String input = "";
        while (!input.equals("exit")) {
            System.out.println(ConstantStrings.APP_START_TEXT);
            System.out.println(ConstantStrings.MENU_OPTIONS);
            input = scanner.nextLine();

            switch (input) {
                case "1":
                    executeApplication(1);
                    break;
                case "2":
                    executeApplication(2);
                    break;
                default:
                    invalidOption();
            }


        }

    }


    private static void invalidOption() {

        System.out.println("Mongoose could not parse your request");

    }

    private void executeApplication(int inputNumber) throws InterruptedException {


        if (inputNumber == 1) {

            String[] argList = new String[2];
            System.out.println("Enter the URL of the target");
            argList[0] = scanner.nextLine();
            System.out.println("Use default SIP port? (Y/N");
            String sipPortOption = scanner.nextLine();

            if (sipPortOption.equalsIgnoreCase("Y")) {

                argList[1] = "DEFAULT";

            } else {
                argList[1] = sipPortOption;
            }

            final SipNullByte nullByteSip = new SipNullByte(argList);

            executor.execute(nullByteSip);


        } else if (inputNumber == 2) {

            String[] argList = new String[2];
            System.out.println("Enter the URL of the target");
            argList[0] = scanner.nextLine();
            System.out.println("Enter the capacity of fuzzing request");
            argList[1] = scanner.nextLine();
            System.out.println();
            System.out.println("Click on any keystroke to end the fuzzing");

            System.out.printf("Staring fuzzing against target at: %s ...", argList[0]);


            final NodeJsFuzzer fuzzer = new NodeJsFuzzer(argList);
            executor.execute(fuzzer);

            Scanner scanner2 = new Scanner(System.in);
            scanner2.nextLine();
            executor.shutdownNow();
            while (!executor.isShutdown()) {
            }
            executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();


            System.out.println("Fuzzing ended :)");


        }
    }
}
