package jack.command;

import jack.storage.Storage;
import jack.task.TaskList;
import jack.ui.Ui;

public class FindCommand extends Command {
    private String keyword;
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        return ui.find(tasks, keyword);
    }
}
