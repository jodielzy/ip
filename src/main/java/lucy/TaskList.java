package lucy;

import java.util.ArrayList;

/**
 * List of tasks managed by the user.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs a TaskList with an existing list of tasks.
     * @param tasks the list of tasks to initialize with.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the task list and saves it to storage.
     * @param task The task to add.
     * @param storage The storage instance to save tasks.
     */
    public void addTask(Task task, Storage storage) {
        tasks.add(task);
        storage.saveTasks(tasks);
    }

    /**
     * Deletes a task from the task list and updates storage.
     * @param index The index of the task to be deleted.
     * @param storage The storage instance to update tasks.
     * @throws LucyException If the index is out of range.
     */
    public void deleteTask(int index, Storage storage) throws LucyException {
        if (index < 0 || index >= tasks.size()) {
            throw new LucyException("lucy.Task index out of range.");
        }
        tasks.remove(index);
        storage.saveTasks(tasks);
    }

    /**
     * Marks a task as done or not done and updates storage.
     * @param index The index of the task.
     * @param done The status to set (true for done, false for not done).
     * @param storage The storage instance to save tasks.
     * @throws LucyException If the index is out of range.
     */
    public void markTask(int index, boolean done, Storage storage) throws LucyException {
        if (index < 0 || index >= tasks.size()) {
            throw new LucyException("lucy.Task index out of range.");
        }
        if (done) {
            tasks.get(index).markAsDone();
        } else {
            tasks.get(index).markAsNotDone();
        }
        storage.saveTasks(tasks);
    }

    /**
     * Lists all tasks in the task list.
     */
    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    /**
     * Retrives a task from the list based on its index.
     * @param index The index of the task.
     * @return The requested Task.
     * @throws LucyException If the index is out of range
     */
    public Task getTask(int index) throws LucyException {
        if (index < 0 || index >= tasks.size()) {
            throw new LucyException("Task index out of range.");
        }
        return tasks.get(index);
    }
    public void findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (task.description.contains(keyword)) {
                matchingTasks.add(task);
            }
        }
        if (matchingTasks.isEmpty()) {
            System.out.println(" No matching tasks found.");
        } else {
            System.out.println(" Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + matchingTasks.get(i));
            }
        }
    }
}
