package lucy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;

import java.time.LocalDate;

/**
 * Handles storage of tasks into a file and loading them.
 */
public class Storage {
    private String filePath;

    /**
     * Constructs a Storage instance with a file path.
     * @param filePath The path of the file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves tasks to the file.
     * @param tasks The list of tasks to save.
     */
    public void saveTasks(ArrayList<Task> tasks) {
        assert tasks != null : "Tasks list should not be null";

        try {
            FileWriter writer = new FileWriter(filePath, false);
            for (Task task : tasks) {
                assert task != null : "Task should not be null";
                writer.write(task.toFileFormat() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the file.
     * @return The list of tasks loaded from the file.
     */
    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                return tasks;
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
        return tasks;
    }
}
