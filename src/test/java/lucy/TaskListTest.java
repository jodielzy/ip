package lucy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class TaskListTest {
    private TaskList taskList;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage("data/test.txt");
        ArrayList<Task> tasks = new ArrayList<>();
        taskList = new TaskList(tasks);
    }
    @Test
    public void testMarkTask_validIndex() throws LucyException {
        Task todo = new Todo("Test task");
        taskList.addTask(todo, storage);
        taskList.markTask(0, true, storage);
        assertTrue(todo.isDone);
    }
    @Test
    public void testMarkTask_invalidIndex() {
        assertThrows(LucyException.class, () -> taskList.markTask(999, true, storage));
    }
    @Test
    public void testDeleteTask_validIndex() throws LucyException {
        Task todo = new Todo("Test task");
        taskList.addTask(todo, storage);
        taskList.deleteTask(0, storage);
        assertThrows(LucyException.class, () -> taskList.getTask(0));
    }
    @Test
    public void testDeleteTask_invalidIndex() {
        assertThrows(LucyException.class, () -> taskList.deleteTask(999, storage));
    }
}
