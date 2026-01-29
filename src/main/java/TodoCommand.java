import java.io.IOException;

public class TodoCommand extends Command {
    private String text;
    public TodoCommand(String text){
        this.text=text;
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage)  throws Exception {
        ToDo todo = ToDo.taskToToDo(text);
        tasks.add(todo);
        storage.save(tasks);
        ui.todo(tasks,todo);
    }
}
