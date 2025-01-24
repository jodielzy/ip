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
        Task[] tasks = new Task[100];
        int taskCount = 0;

        while(true) {
            try {
                String input = scanner.nextLine();
                if (input.equals("bye")) {
                    System.out.println(line);
                    System.out.println("Bye. Hope to see you soon!");
                    System.out.println(line);
                    break;
                } else if (input.equals("list")) {
                    System.out.println(line);
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + "." + tasks[i]);
                    }
                    System.out.println(line);
                } else if (input.startsWith("mark ")) {
                    try {
                        int index = Integer.parseInt(input.split(" ")[1]) - 1;
                        if (index >= 0 && index < taskCount) {
                            tasks[index].markAsDone();
                            System.out.println(line);
                            System.out.println("Nice! I've marked this task as done:");
                            System.out.println(" " + tasks[index]);
                            System.out.println(line);
                        } else {
                            throw new LucyException("Task index out of range. Please provide a valid task number.");
                        }
                    } catch (NumberFormatException e) {
                        throw new LucyException("Invalid format. Please provide a task number to mark.");
                    }
                } else if (input.startsWith("unmark ")) {
                    try {
                        int index = Integer.parseInt(input.split(" ")[1]) - 1;
                        if (index >= 0 && index < taskCount) {
                            tasks[index].markAsNotDone();
                            System.out.println(line);
                            System.out.println("I've marked this task as undone:");
                            System.out.println(" " + tasks[index]);
                            System.out.println(line);
                        } else {
                            throw new LucyException("Task index out of range. Please provide a valid task number.");
                        }
                    } catch (NumberFormatException e) {
                        throw new LucyException("Invalid format. Please provide a task number to unmark.");
                    }
                } else if (input.startsWith("todo")) {
                    if (input.trim().equals("todo") || input.length() <= 5 || input.substring(5).trim().isEmpty()) {
                        throw new LucyException("The description of a todo cannot be empty.");
                    }
                    String description = input.substring(5);
                    tasks[taskCount] = new Todo(description);
                    taskCount++;
                    System.out.println(line);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(" " + tasks[taskCount - 1]);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    System.out.println(line);
                } else if (input.startsWith("deadline ")) {
                    if (!input.contains(" /by ")) {
                        throw new LucyException("The description of a deadline must include 'by <time>'.");
                    }
                    String[] parts = input.substring(9).split(" /by ");
                    if (parts.length < 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
                        throw new LucyException("Invalid format for deadline. Ensure you specify a description and a deadline time.");
                    }
                    tasks[taskCount] = new Deadline(parts[0], parts[1]);
                    taskCount++;
                    System.out.println(line);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(" " + tasks[taskCount - 1]);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    System.out.println(line);
                } else if (input.startsWith("event ")) {
                    if (!input.contains(" /from ") || !input.contains(" /to ")) {
                        throw new LucyException("Invalid format for event. Ensure you specify a description, start time and end time.");
                    }
                    String[] parts = input.substring(6).split(" /from | /to ");
                    tasks[taskCount] = new Event(parts[0], parts[1], parts[2]);
                    taskCount++;
                    System.out.println(line);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(" " + tasks[taskCount - 1]);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    System.out.println(line);
                } else {
                    throw new LucyException("I'm sorry, but I don't know what that means.");
                }
            } catch (LucyException e) {
                System.out.println(line);
                System.out.println(e.getMessage());
                System.out.println(line);
            }
        }
        scanner.close();
    }
}