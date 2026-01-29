public class ListCommand extends Command{
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage)  throws Exception{
        ui.list(tasks);
    }
}
