import java.io.IOException;

abstract public class Command {
    protected boolean isExit = false;

    abstract public void execute(TaskList tasks,Ui ui,Storage storage) throws Exception;

    public boolean isExit(){return  this.isExit;}

}
