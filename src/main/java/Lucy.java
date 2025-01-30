import java.util.ArrayList;
import java.util.Scanner;

public class Lucy {
    private static final String FILE_PATH = "data/lucy.txt";
    private static ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        TaskManager.loadTasks(tasks, FILE_PATH);
        Scanner scanner = new Scanner(System.in);
        String line = "_________________________________";

        //greet user
        System.out.println(line);
        System.out.println("Hello! I'm Lucy");
        System.out.println("What can I do for you?");
        System.out.println(line);

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
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }
                    System.out.println(line);
                } else if (input.startsWith("mark ")) {
                    try {
                        int index = Integer.parseInt(input.split(" ")[1]) - 1;
                        if (index >= 0 && index < tasks.size()) {
                            tasks.get(index).markAsDone();
                            TaskManager.saveTasks(tasks, FILE_PATH);
                            System.out.println(line);
                            System.out.println("Nice! I've marked this task as done:");
                            System.out.println(" " + tasks.get(index));
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
                        if (index >= 0 && index < tasks.size()) {
                            tasks.get(index).markAsNotDone();
                            TaskManager.saveTasks(tasks, FILE_PATH);
                            System.out.println(line);
                            System.out.println("I've marked this task as undone:");
                            System.out.println(" " + tasks.get(index));
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
                    tasks.add(new Todo(description));
                    TaskManager.saveTasks(tasks, FILE_PATH);
                    System.out.println(line);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(" " + tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println(line);
                } else if (input.startsWith("deadline ")) {
                    if (!input.contains(" /by ")) {
                        throw new LucyException("The description of a deadline must include 'by <time>'.");
                    }
                    String[] parts = input.substring(9).split(" /by ");
                    if (parts.length < 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
                        throw new LucyException("Invalid format for deadline. Ensure you specify a description and a deadline time.");
                    }
                    tasks.add(new Deadline(parts[0], parts[1]));
                    TaskManager.saveTasks(tasks, FILE_PATH);
                    System.out.println(line);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(" " + tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println(line);
                } else if (input.startsWith("event ")) {
                    if (!input.contains(" /from ") || !input.contains(" /to ")) {
                        throw new LucyException("Invalid format for event. Ensure you specify a description, start time and end time.");
                    }
                    String[] parts = input.substring(6).split(" /from | /to ");
                    tasks.add(new Event(parts[0], parts[1], parts[2]));
                    TaskManager.saveTasks(tasks, FILE_PATH);
                    System.out.println(line);
                    System.out.println("Got it. I've added this task:");
                    System.out.println(" " + tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println(line);
                } else if (input.startsWith("delete ")) {
                    try {
                        int index = Integer.parseInt(input.split(" ")[1]) - 1;
                        if (index >= 0 && index < tasks.size()) {
                            Task removedTask = tasks.remove(index);
                            TaskManager.saveTasks(tasks, FILE_PATH);
                            System.out.println(line);
                            System.out.println("Noted. I've removed this task:");
                            System.out.println(" " + removedTask);
                            System.out.println("Now you have " + tasks.size() + " tasks in the list");
                            System.out.println(line);
                        } else {
                            throw new LucyException("Task index out of range. Please provide a valid task number.");
                        }
                    } catch (NumberFormatException e) {
                        throw new LucyException("Invalid format. Please provide task number to delete.");
                    }
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