package jack.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jack.DateTimeTool;
import jack.Excep;

/**
 * Represents a note in the Jack application.
 * Notes are simple text entries with timestamps.
 * Inherits from Task to be stored in TaskList.
 */
public class Note extends Task {
    private LocalDateTime timestamp;

    /**
     * Constructs a new Note with the specified content and current timestamp.
     * @param content The content of the note.
     */
    public Note(String content) {
        super(content);
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Constructs a new Note with the specified content and timestamp.
     * Used for deserialization.
     * @param content The content of the note.
     * @param timestamp The timestamp of the note.
     */
    public Note(String content, LocalDateTime timestamp) {
        super(content);
        this.timestamp = timestamp;
    }

    /**
     * Returns the timestamp of the note.
     * @return The timestamp of the note.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the task type name.
     * @return "N" for Note.
     */
    @Override
    public String taskName() {
        return "N";
    }

    /**
     * Returns a string representation of the note.
     * @return A string containing the note content and timestamp.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "[N] " + description + " (created: " + timestamp.format(formatter) + ")";
    }

    /**
     * Marks the note as done.
     * Notes cannot be marked as done, so this method does nothing.
     */
    @Override
    public void mark() {
    }

    /**
     * Marks the note as not done.
     * Notes cannot be marked as not done, so this method does nothing.
     */
    @Override
    public void unmark() {
    }

    /**
     * Returns whether the note is done.
     * Notes are always considered not done.
     * @return false.
     */
    @Override
    public boolean isDone() {
        return false;
    }

    /**
     * Creates a new Note task from the given task string.
     * @param task The task string containing the description.
     * @return A new Note task with the specified description.
     */
    public static Note taskToNote(String task) throws Excep {
        if (task.isEmpty()) {
            throw new Excep("note description cannot be empty");
        }
        if (!task.contains("/by")) {
            return new Note(task.trim());
        }
        String[] temp = task.split("/by", 2);
        String text = temp[0].trim();
        if (text.isEmpty()) {
            throw new Excep("wrong note format");
        }
        String datetime = temp[1].trim();
        if (datetime.isEmpty() || datetime.contains("/by")) {
            throw new Excep("wrong note format");
        }
        return new Note(text, DateTimeTool.parseDateTime(datetime));
    }

    /**
     * Returns the note information as a string for storage.
     * @return The description and timestamp of the note.
     */
    @Override
    public String toTask() {
        return description + " /by " + DateTimeTool.formatDateTime(timestamp);
    }
}
