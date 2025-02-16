package lucy;

/**
 * Represents a generic task.
 */
abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task with a description.
     * @param description The task description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task.
     * @return "X" if done, otherwise " ".
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns the task in file format.
     * @return THe formatted string to be saved in a file.
     */
    public abstract String toFileFormat();

    @Override
    public Task clone() {
        try {
            Task clonedTask = (Task) super.clone();
            clonedTask.isDone = this.isDone;
            return clonedTask;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning not supported for Task");
        }
    }


    /**
     * Returns the string representation of the task.
     * @return The formatted task string.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
