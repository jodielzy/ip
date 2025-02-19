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
     * Creates and returns a copy of this Todo task.
     * The cloned task retains the same description, due date, and completion status.
     * @return A new Todo object that is a copy of the current task.
     */
    @Override
    public Todo clone() {
        Todo clonedTodo = new Todo(this.description);
        clonedTodo.isDone = this.isDone;
        return clonedTodo;
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
