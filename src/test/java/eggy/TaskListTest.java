// TaskListTest.java
package eggy;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import eggy.task.Task;
import eggy.task.ToDo;
import eggy.task.Event;

public class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList(new java.util.ArrayList<>());
    }

    @Test
    void appendTodoAddsTask() throws Exception {
        Task task = taskList.append("todo Read book", "todo");
        assertTrue(task instanceof ToDo);
        assertEquals("Read book", task.description);
        assertEquals(1, taskList.size());
    }

    @Test
    void appendDeadlineThrowsExceptionWithoutBy() {
        Exception e = assertThrows(Exception.class, () -> {
            taskList.append("deadline submit report", "deadline");
        });
        assertTrue(e.getMessage().contains("please provide a deadline"));
    }

    @Test
    void appendEventAddsTask() throws Exception {
        Task task = taskList.append("event team meeting /from Mon 2pm /to Mon 4pm", "event");
        assertTrue(task instanceof Event);
        assertEquals("team meeting", task.description);
        assertEquals(1, taskList.size());
    }

    @Test
    void handleMarkUnmarkChangesStatus() throws Exception {
        taskList.append("todo clean room", "todo");
        Task task = taskList.get(0);
        assertFalse(task.isDone);
        taskList.handleMarkUnmark("mark 1");
        assertTrue(task.isDone);
        taskList.handleMarkUnmark("unmark 1");
        assertFalse(task.isDone);
    }
}
