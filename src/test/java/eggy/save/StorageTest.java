package eggy.save;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import eggy.TaskList;
import eggy.task.ToDo;
import eggy.task.DeadlineTask;
import eggy.task.Event;
import eggy.task.Task;
import java.nio.file.*;
import java.io.IOException;
import java.util.ArrayList;

public class StorageTest {
    private Storage storage;
    private final Path dataFile = Paths.get(".", "data", "eggy.txt");

    @BeforeEach
    public void setUp() throws IOException {
        storage = new Storage();
        if (Files.exists(dataFile)) {
            Files.delete(dataFile);
        }
    }

    @Test
    public void saveAndLoadTasks_roundtrip() throws IOException {
        TaskList taskList = new TaskList(new ArrayList<>());

        taskList.add(new ToDo("todo read book"));
        taskList.add(new DeadlineTask("deadline submit report /by 2025-08-30T18:00"));
        taskList.add(new Event("event team meeting /from 2025-08-31T15:00 /to 2025-08-31T16:00"));

        storage.saveTasksToFile(taskList);
        assertTrue(Files.exists(dataFile), "Data file must exist after saving");

        TaskList loadedList = storage.loadTasksFromFile();
        assertEquals(taskList.size(), loadedList.size());

        Task origTask = taskList.get(1);
        Task loadedTask = loadedList.get(1);
        assertEquals(origTask.description, loadedTask.description);
        assertEquals(origTask.isDone, loadedTask.isDone);
    }

    @Test
    public void loadTasksFromFile_emptyFile_returnsEmptyList() throws IOException {
        Files.createDirectories(dataFile.getParent());
        Files.write(dataFile, new ArrayList<>());

        TaskList loadedList = storage.loadTasksFromFile();
        assertEquals(0, loadedList.size());
    }
}
