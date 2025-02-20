package lucy;

import java.util.ArrayList;
import java.util.Stack;

/**
 * List of tasks managed by the user.
 */
public class TaskList {
    private ArrayList<Task> tasks;
    private Stack<ArrayList<Task>> history;

    /**
     * Constructs a TaskList with an existing list of tasks.
     * @param tasks the list of tasks to initialize with.
     */
    public TaskList(ArrayList<Task> tasks) {
        assert tasks != null : "Task list should not be null";
        this.tasks = tasks;
        this.history = new Stack<>();
    }

    /**
     * Saves the current state of tasks before modification.
     */
    private void saveState() {
        ArrayList<Task> taskCopy = new ArrayList<>();
        for (Task task : tasks) {
            taskCopy.add(task.clone());
        }
        history.push(taskCopy);
    }

    /**
     * Adds a task to the task list and saves it to storage.
     * @param task The task to add.
     * @param storage The storage instance to save tasks.
     */
    public void addTask(Task task, Storage storage) {
        saveState();
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

        saveState();
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

        saveState();
        if (done) {
            tasks.get(index).markAsDone();
        } else {
            tasks.get(index).markAsNotDone();
        }
        storage.saveTasks(tasks);
    }

    /**
     * Undoes the last action performed on the task list.
     * Restores the previous state from history and updates storage.
     *
     * @param storage The storage instance used to persist the undone state.
     * @return A message indicating the result of the undo operation.
     */
    public String undo(Storage storage) {
        if (history.isEmpty()) {
            return "Oops! No actions left to undo. Maybe we can just ketchup next time?";
        }

        ArrayList<Task> previousState = history.pop();
        tasks.clear();
        tasks.addAll(previousState);

        storage.saveTasks(tasks);
        return "Phew! I rolled things back like a good spud!";
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

    /**
     * Returns a formatted string listing all tasks in the task list.
     *
     * @return A string representation of all tasks or a message if the list is empty.
     */
    public String listTasksString() {
        if (tasks.isEmpty()) {
            return "Oh no, Tomo! Your task list is emptier than a potato sack! Let's add something!";
        }
        StringBuilder sb = new StringBuilder("Here's your task list, Tomo! " +
                "Let's get these tasks done before we sprout more problems! :\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Searches for tasks that contain a given keyword in their description.
     *
     * @param keyword The keyword to search for.
     * @return A formatted string listing the matching tasks or a message if no matches are found.
     */
    public String findTasksString(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.description.contains(keyword)) {
                matchingTasks.add(task);
            }
        }
        if (matchingTasks.isEmpty()) {
            return "Oopsie! No matching tasks found, Tomo. Maybe I need to put on my potato glasses?";
        }
        StringBuilder sb = new StringBuilder("Here are the matching tasks, Tomo! :\n");
        for (int i = 0; i < matchingTasks.size(); i++) {
            sb.append(" ").append(i + 1).append(". ").append(matchingTasks.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns the number of tasks currently in the task list.
     *
     * @return The total number of tasks.
     */
    public int getSize() {
        return tasks.size();
    }

}
