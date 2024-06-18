import java.util.List;

public class Executor {
    private List<Task> taskList;

    public void executeTask() {
        for (Task task : taskList) {
            System.out.println(task.getTaskContent() + " was execute");
            if (task.getSubtaskContent() != null) {
                System.out.println(task.getSubtaskContent().getTaskContent() + " from " + task.getTaskContent() + " was also execute");
            }
            System.out.println();
        }
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
}
