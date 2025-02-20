package lucy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents the main application logic for Lucy.
 */
public class Lucy {
    private Storage storage;
    private TaskList tasks;

    /**
     * Constructs a Lucy instance with a file path for task storage.
     *
     * @param filePath The path to the storage file.
     */
    public Lucy(String filePath) {
        storage = new Storage(filePath);
        tasks = new TaskList(storage.loadTasks());
    }

    /**
     * Processes user commands and returns responses.
     *
     * @param input The user input string.
     * @return Lucy's response.
     */
    public String getResponse(String input) {
        assert input != null : "Input should never be null";

        String[] parsedCommand = Parser.parseCommand(input);
        assert parsedCommand.length > 0 : "Parsed command should not be empty";

        try {
            switch (parsedCommand[0]) {
            case "bye":
                return handleBye();
            case "list":
                return handleList();
            case "mark":
                return handleMark(parsedCommand);
            case "unmark":
                return handleUnmark(parsedCommand);
            case "todo":
                return handleTodo(parsedCommand);
            case "deadline":
                return handleDeadline(parsedCommand);
            case "event":
                return handleEvent(parsedCommand);
            case "delete":
                return handleDelete(parsedCommand);
            case "find":
                return handleFind(parsedCommand);
            case "undo":
                return handleUndo();
            default:
                return "Uhh... my little potato brain doesn't understand this, Tomo!";
            }
        } catch (LucyException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Handles the "bye" command, returning a farewell message.
     *
     * @return A goodbye message from Lucy.
     */
    private String handleBye() {
        return "Added to your list, Tomo! Just don’t let it grow roots!";
    }

    /**
     * Handles the "list" command, returning a list of all tasks.
     *
     * @return A formatted list of all tasks.
     */
    private String handleList() {
        return tasks.listTasksString();
    }

    /**
     * Handles the "mark" command, marking a specified task as done.
     *
     * @param parsedCommand The parsed command containing the task index.
     * @return A confirmation message indicating the task has been marked as done.
     * @throws LucyException If the index is invalid.
     */
    private String handleMark(String[] parsedCommand) throws LucyException {
        int markIndex = Integer.parseInt(parsedCommand[1]) - 1;
        assert markIndex >= 0 && markIndex < tasks.getSize() : "Invalid mark index";
        tasks.markTask(markIndex, true, storage);
        return "Ooo! You finished a task! That's *spud-tacular*!";
    }

    /**
     * Handles the "unmark" command, marking a specified task as not done.
     *
     * @param parsedCommand The parsed command containing the task index.
     * @return A confirmation message indicating the task has been marked as not done.
     * @throws LucyException If the index is invalid.
     */
    private String handleUnmark(String[] parsedCommand) throws LucyException {
        int unmarkIndex = Integer.parseInt(parsedCommand[1]) - 1;
        assert unmarkIndex >= 0 && unmarkIndex < tasks.getSize() : "Invalid unmark index";
        tasks.markTask(unmarkIndex, false, storage);
        return "Oopsie... We unmarked that. It's okay! Potatoes sometimes forget things too!";
    }

    /**
     * Handles the "todo" command, adding a new todo task.
     *
     * @param parsedCommand The parsed command containing the task description.
     * @return A confirmation message indicating the task has been added.
     */
    private String handleTodo(String[] parsedCommand) {
        if (parsedCommand.length < 2 || parsedCommand[1].trim().isEmpty()) {
            return "Error: The description of a todo cannot be empty. Use: todo <task>";
        }

        Todo newTask = new Todo(parsedCommand[1]);
        tasks.addTask(newTask, storage);
        return "Added to your list, Tomo! Just don’t let it grow roots!\n" + newTask.toString() + "\n"
                + "You have " + tasks.getSize() + " tasks on your list!";
    }

    /**
     * Handles the "deadline" command, adding a new deadline task.
     *
     * @param parsedCommand The parsed command containing the task description and due date.
     * @return A confirmation message indicating the task has been added.
     */
    private String handleDeadline(String[] parsedCommand) {
        if (parsedCommand.length < 2 || !parsedCommand[1].contains(" /by ")) {
            return "Error: The description of a deadline cannot be empty. Use: deadline <task> /by yyyy-MM-dd";
        }
        String[] deadlineParts = parsedCommand[1].split(" /by ");
        if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty() || deadlineParts[1].trim().isEmpty()) {
            return "Error: Invalid deadline format. Use: deadline <task> /by yyyy-MM-dd";
        }
        try {
            LocalDate date = LocalDate.parse(deadlineParts[1].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Deadline newTask = new Deadline(deadlineParts[0].trim(), date);
            tasks.addTask(newTask, storage);
            return "Added to your list, Tomo! Just don’t let it grow roots!\n" + newTask.toString() + "\n"
                    + "You have " + tasks.getSize() + " tasks on your list!";
        } catch (DateTimeParseException e) {
            return "Error: Invalid date format! Please use yyyy-MM-dd.";
        }
    }

    /**
     * Handles the "event" command, adding a new event task.
     *
     * @param parsedCommand The parsed command containing the event details.
     * @return A confirmation message indicating the task has been added.
     */
    private String handleEvent(String[] parsedCommand) {
        if (parsedCommand.length < 2 || !parsedCommand[1].contains(" /from ") || !parsedCommand[1].contains(" /to ")) {
            return "Error: The description of an event cannot be empty. Use: event <task> /from <start> /to <end>";
        }
        String[] eventParts = parsedCommand[1].split(" /from | /to ");
        if (eventParts.length < 3 || eventParts[0].trim().isEmpty() || eventParts[1].trim().isEmpty() ||
                eventParts[2].trim().isEmpty()) {
            return "Error: Invalid event format. Use: event <task> /from <start> /to <end>";
        }
        Event newTask = new Event(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim());
        tasks.addTask(newTask, storage);
        return "Added to your list, Tomo! Just don’t let it grow roots!\n" + newTask.toString() + "\n"
                + "You have " + tasks.getSize() + " tasks on your list!";
    }

    /**
     * Handles the "delete" command, removing a task.
     *
     * @param parsedCommand The parsed command containing the task index.
     * @return A confirmation message indicating the task has been removed.
     * @throws LucyException If the index is invalid.
     */
    private String handleDelete(String[] parsedCommand) throws LucyException {
        int deleteIndex = Integer.parseInt(parsedCommand[1]) - 1;
        tasks.deleteTask(deleteIndex, storage);
        return "Chopping this task away... Hope it wasn’t too rooted in your plans!";
    }

    /**
     * Handles the "find" command, searching for tasks containing a keyword.
     *
     * @param parsedCommand The parsed command containing the search keyword.
     * @return A list of matching tasks.
     */
    private String handleFind(String[] parsedCommand) {
        if (parsedCommand.length < 2 || parsedCommand[1].trim().isEmpty()) {
            return "Please provide a keyword to search for.";
        }
        return tasks.findTasksString(parsedCommand[1].trim());
    }

    /**
     * Handles the "undo" command, undoing the last action performed.
     *
     * @return A message indicating the result of the undo operation.
     */
    private String handleUndo() {
        return tasks.undo(storage);
    }

    /**
     * The main entry point of the application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new Lucy("data/lucy.txt");
    }
}
