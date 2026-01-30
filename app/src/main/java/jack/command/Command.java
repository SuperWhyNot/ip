package jack.command;

import jack.storage.Storage;
import jack.task.TaskList;
import jack.ui.Ui;

public abstract class Command {
    protected boolean isExit = false;

    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws Exception;

    public boolean isExit() { return this.isExit; }
}
