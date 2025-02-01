import java.util.Scanner;
import java.util.ArrayList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDate;

public class TaskManager {
    public static void saveTasks(ArrayList<Task> tasks, String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath, false);
            for (Task task : tasks) {
                writer.write(task.toFileFormat() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public static void loadTasks(ArrayList<Task> tasks, String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                return;
            }
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String[] parts = fileScanner.nextLine().split("\\s*\\|\\s*");
                Task task;
                switch (parts[0]) {
                    case "T":
                        if (parts.length < 3) {
                            continue;
                        }
                        task = new Todo(parts[2]);
                        break;
                    case "D":
                        if (parts.length < 4) {
                            continue;
                        }
                        task = new Deadline(parts[2], LocalDate.parse(parts[3]));
                        break;
                    case "E":
                        if (parts.length < 4) {
                            continue;
                        }
                        String[] eventTimes = parts[3].split("-");
                        if (eventTimes.length < 2) {
                            continue;
                        }
                        task = new Event(parts[2], eventTimes[0].trim(), eventTimes[1].trim());
                        break;
                    default:
                        continue;
                }
                if (parts[1].equals("1")) {
                    task.markAsDone();
                }
                tasks.add(task);
            }
            fileScanner.close();
        } catch(IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }
}
