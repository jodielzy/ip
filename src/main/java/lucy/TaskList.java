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
        assert tasks != null : "Task list should not be null";
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
        assert storage != null : "Storage instance should not be null";
        assert index >= 0 && index < tasks.size() : "Task index out of range";

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

    public String listTasksString() {
        if (tasks.isEmpty()) {
            return "No tasks found.";
        }
        StringBuilder sb = new StringBuilder("Here are your tasks:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }

    public String findTasksString(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.description.contains(keyword)) {
                matchingTasks.add(task);
            }
        }
        if (matchingTasks.isEmpty()) {
            return "No matching tasks found.";
        }
        StringBuilder sb = new StringBuilder("Here are the matching tasks:\n");
        for (int i = 0; i < matchingTasks.size(); i++) {
            sb.append(" ").append(i + 1).append(". ").append(matchingTasks.get(i)).append("\n");
        }
        return sb.toString();
    }

    public int getSize() {
        return tasks.size();
    }

}
