public class ByeCommand extends Command{
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage)  throws Exception{
        ui.bye();
        this.isExit=true;
    }
}
