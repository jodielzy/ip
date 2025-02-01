package lucy;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task, Storage storage) {
        tasks.add(task);
        storage.saveTasks(tasks);
    }

    public void deleteTask(int index, Storage storage) throws LucyException {
        if (index < 0 || index >= tasks.size()) {
            throw new LucyException("lucy.Task index out of range.");
        }
        tasks.remove(index);
        storage.saveTasks(tasks);
    }

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

    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }
}
