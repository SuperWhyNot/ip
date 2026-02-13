package jack.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import jack.Excep;
import jack.task.Deadline;
import jack.task.Event;
import jack.task.Task;
import jack.task.TaskList;
import jack.task.ToDo;

/**
 * Handles the storage of tasks in the Jack application.
 * Saves tasks to a file on the hard disk and loads them when the application starts.
 */
public class Storage {

    private static final String SEPARATOR = " | ";
    private final File storageFile;
    /**
     * Constructs a new Storage instance with the specified file path.
     * Creates the file and parent directories if they do not exist.
     * @param filePath The file path where tasks will be stored.
     * @throws Excep If the file cannot be created or if the path is not a file.
     * @throws IOException If an I/O error occurs when creating directories.
     */
    public Storage(String filePath) throws Excep, IOException {
        if (filePath == null || filePath.isEmpty()) {
            throw new Excep("File path cannot be empty");
        }

        storageFile = new File(filePath);

        if (!storageFile.exists()) {
            createStorageFile();
        } else if (!storageFile.isFile()) {
            throw new Excep("Path is not a file");
        }
    }

    /**
     * Creates the storage file and parent directories if they do not exist.
     * @throws IOException If an I/O error occurs when creating directories or file.
     * @throws Excep If the file cannot be created.
     */
    private void createStorageFile() throws IOException, Excep {
        File parentDir = storageFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new IOException("Failed to create parent directories");
            }
        }

        boolean isCreated = storageFile.createNewFile();
        if (!isCreated) {
            throw new Excep("Create File Fail");
        }
    }

    /**
     * Saves the specified task list to the storage file.
     * @param taskList The task list to save.
     * @throws IOException If an I/O error occurs when writing to the file.
     */
    public void save(TaskList taskList) throws IOException {
        if (taskList == null) {
            throw new IllegalArgumentException("Task list cannot be null");
        }

        try (FileOutputStream os = new FileOutputStream(storageFile.getPath(), false);
             OutputStreamWriter ow = new OutputStreamWriter(os, StandardCharsets.UTF_8);
             BufferedWriter bw = new BufferedWriter(ow)) {

            for (Task task : taskList) {
                if (task != null) {
                    bw.write(serialize(task));
                    bw.newLine();
                }
            }

            bw.flush();
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Reads tasks from the storage file and returns them as a TaskList.
     * @return A TaskList containing all tasks read from the file.
     * @throws FileNotFoundException If the storage file is not found.
     * @throws IOException If an I/O error occurs when reading from the file.
     */
    public TaskList read() throws FileNotFoundException, IOException {
        TaskList taskList = new TaskList();

        try (FileReader fileReader = new FileReader(storageFile.getPath());
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                try {
                    Task task = deserialize(currentLine);
                    if (task != null) {
                        taskList.add(task);
                    }
                } catch (Exception e) {
                    System.err.println("Error reading task: " + e.getMessage());
                }
            }
        }

        System.out.println("Now you have " + taskList.size() + " tasks in the list.");
        return taskList;
    }

    /**
     * Serializes a Task object to a string format for storage.
     * @param task The task to serialize.
     * @return A string representation of the task in storage format.
     */
    public static String serialize(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        return String.join(SEPARATOR, task.taskName(), task.isDone() ? "1" : "0", task.toTask());
    }

    /**
     * Deserializes a string from the storage file to a Task object.
     * @param line The string to deserialize.
     * @return A Task object created from the string.
     * @throws Exception If the string format is invalid or the task type is unknown.
     */
    public static Task deserialize(String line) throws Exception {
        if (line == null || line.isEmpty()) {
            throw new IllegalArgumentException("Line cannot be empty");
        }

        String[] parts = line.split(" \\|" );
        if (parts.length < 3) {
            throw new Exception("Invalid task format: missing required parts");
        }

        String taskType = parts[0];
        boolean isDone = "1".equals(parts[1]);
        String taskDetails = parts[2];

        Task task;
        switch (taskType) {
        case "E":
            task = Event.taskToEvent(taskDetails);
            break;
        case "D":
            task = Deadline.taskToDeadline(taskDetails);
            break;
        case "T":
            task = ToDo.taskToToDo(taskDetails);
            break;
        default:
            throw new Exception("Unknown task type: " + taskType);
        }

        if (isDone) {
            task.mark();
        }

        return task;
    }
}
