package lucy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    private LocalDate dueDate;

    /**
     * Constructs a Deadline task.
     * @param description The description of the task.
     * @param by The due date of the task.
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.dueDate = by;
    }

    @Override
    public Deadline clone() {
        Deadline clonedDeadline = new Deadline(this.description, this.dueDate);
        clonedDeadline.isDone = this.isDone;
        return clonedDeadline;
    }


    /**
     * Returns the string representation of the deadline task.
     * @return The formatted task string.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + dueDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }

    /**
     * Returns the task in file format.
     * @return The formatted string to be saved in a file.
     */
    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + dueDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
