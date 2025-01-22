import java.util.Scanner;

public class Lucy {
    public static void main(String[] args) {
        String line = "_________________________________";

        //greet user
        System.out.println(line);
        System.out.println("Hello! I'm Lucy");
        System.out.println("What can I do for you?");
        System.out.println(line);

        Scanner scanner =  new Scanner(System.in);
        String input;

        while(true) {
            input = scanner.nextLine();

            if (input.equals("bye")) {
                System.out.println(line);
                System.out.println("Bye. Hope to see you soon!");
                System.out.println(line);
                break;
            }
            System.out.println(line);
            System.out.println(input);
            System.out.println(line);
        }
        scanner.close();
    }
}
