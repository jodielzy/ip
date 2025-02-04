package lucy;

/**
 * Represents a to-do task.
 */
public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the string representation of the to-do task.
     * @return The formatted task string.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns the task in file format.
     * @return The formatted string to be saved in a file.
     */
    @Override
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}
