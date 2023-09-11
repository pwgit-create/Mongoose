package pn.mongoose;

import pn.mongoose.applications.*;
import pn.mongoose.constant.ConstantStrings;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class Menu {


    public Menu() {
        scanner = new Scanner(System.in);
        nestedScanner = new Scanner(System.in);
    }

    private ThreadPoolExecutor executor =
            (ThreadPoolExecutor) Executors.newCachedThreadPool();
    private final Scanner scanner;
    private final Scanner nestedScanner;

    public void printMenu() {

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
                case "3":
                    executeApplication(3);
                    break;
                case "4":
                    executeApplication(4);
                    break;
                case "5":
                    executeApplication(5);
                default:
                    invalidOption();
            }


        }

    }


    private static void invalidOption() {

        System.out.println("Mongoose could not parse your request");

    }

    private void executeApplication(int inputNumber) {


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


            nestedScanner.nextLine();
            executor.shutdownNow();
            while (!executor.isShutdown()) {
                System.out.println();
            }
            executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();


            System.out.println("Fuzzing ended :)");


        } else if (inputNumber == 3) {

            System.out.println("""
                    Instructions of fire walking
                    ----------------------------
                    1. Do a traceroute at your intended target and see where you get burnt
                    2. Add the burnt target (often a firewall) and run the firewalk port scan again
                    3. Repeat until you reach your target :)""");

            String[] argList = new String[3];
            System.out.println("Enter the host IP");
            argList[0] = scanner.nextLine();
            System.out.println("Enter the start port");
            argList[1] = scanner.nextLine();
            System.out.println("Enter the end port");
            argList[2] = scanner.nextLine();


            System.out.printf("Starting firewalking  at: %s ...", argList[0]);


            final Firewalk fireWalker = new Firewalk(argList);
            executor.execute(fireWalker);


            nestedScanner.nextLine();
            executor.shutdownNow();
            while (!executor.isShutdown()) {
                System.out.println();
            }
            executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();


            System.out.println("Firewalking ended :)");


        } else if (inputNumber == 4) {

            boolean areAllChildPathsSet = false;
            String addMoreChildPathInputFromUser;

            // The arg list is left un initialized until all the child paths are set
            String[] argList;
            // The initial list is set with a placeholder length of 1000
            String[] initialList = new String[1000];
            System.out.println("Enter the URL of the target");
            initialList[0] = scanner.nextLine();
            // Default child paths
            initialList[1] = "/index.html";
            initialList[2] = "/index.php";


            int counter = 3;
            while (!areAllChildPathsSet) {

                System.out.println("Do you wish to add more child paths? (y/n)");
                addMoreChildPathInputFromUser = scanner.nextLine();

                if (addMoreChildPathInputFromUser.equalsIgnoreCase("y")) {
                    System.out.println("OK , add a new child path");

                    initialList[counter] = scanner.nextLine();
                    counter++;
                } else {
                    System.out.println("All child paths are set");
                    areAllChildPathsSet = true;
                }
            }

            List<String> list = new ArrayList<>(Arrays.asList(initialList));
            list.removeIf(Objects::isNull);
            argList = list.toArray(new String[list.size()]);

            System.out.println("Click on any keystroke to end the credential harvesting");
            System.out.printf("Starting credential harvesting against target at: %s ...", argList[0]);
            final CredentialHarvester credentialHarvester = new CredentialHarvester(argList);
            executor.execute(credentialHarvester);

            nestedScanner.nextLine();
            executor.shutdownNow();
            while (!executor.isShutdown()) {
                System.out.println();

            }
            executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        } else if (inputNumber == 5) {

            String[] argList = new String[2];
            System.out.println("Enter the remote url path for an image");
            argList[0] = scanner.nextLine();
            System.out.println("Enter the absolute path of a local image");
            argList[1] = scanner.nextLine();
            System.out.println();


            System.out.printf("Starting experimental picture changer (under construction) for remote url: %s\n" +
                    "and local file path of: %s", argList[1], argList[1]);


            final ChangePicture changePicture = new ChangePicture(argList);
            executor.execute(changePicture);


            nestedScanner.nextLine();
            executor.shutdownNow();
            while (!executor.isShutdown()) {
                System.out.println();

            }
            executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();


            System.out.println("Experimental picture changer ended!");

        }
    }
}
