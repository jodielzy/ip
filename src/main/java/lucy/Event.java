package lucy;

/**
 * Represents an event task with a start and end time.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Constructs an Event task.
     * @param description The description of the event.
     * @param from The start time of the event.
     * @param to The end time of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Creates and returns a copy of this Event task.
     * The cloned task retains the same description, due date, and completion status.
     * @return A new Event object that is a copy of the current task.
     */
    @Override
    public Event clone() {
        Event clonedEvent = new Event(this.description, this.from, this.to);
        clonedEvent.isDone = this.isDone; 
        return clonedEvent;
    }

    /**
     * Returns the string representation of the event task.
     * @return The formatted task string.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    /**
     * Returns the task in file format.
     * @return The formatted string to be saved in a file.
     */
    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + from + "-" + to;
    }
}
