public class Task {
    private String taskContent;
    private Task subtaskContent;

    public void setSubtaskContent(Task subtaskContent) {
        this.subtaskContent = subtaskContent;
    }

    public Task getSubtaskContent() {
        return subtaskContent;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }
}
