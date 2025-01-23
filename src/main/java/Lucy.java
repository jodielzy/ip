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
        String[] tasks = new String[100];
        int taskCount = 0;

        while(true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                System.out.println(line);
                System.out.println("Bye. Hope to see you soon!");
                System.out.println(line);
                break;
            } else if (input.equals("list")) {
                System.out.println(line);
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                System.out.println(line);
            } else {
                tasks[taskCount] = input;
                taskCount++;
                System.out.println(line);
                System.out.println("added: " + input);
                System.out.println(line);
            }
        }
        scanner.close();
    }
}
