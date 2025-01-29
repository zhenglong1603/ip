public class Ui {
    public void generateResponse(String input) {
        switch(input) {
            case "start":
                System.out.println("---------------------------------------------------");
                System.out.println("Hello! I'm ZBOT!!");
                System.out.println("What can I do for you?");
                System.out.println("---------------------------------------------------");
                break;
            case "end":
                System.out.println("---------------------------------------------------");
                System.out.println("Bye. Hope to see you again soon :)");
                System.out.println("---------------------------------------------------");
                break;
            case "loadingError":
                System.out.println("---------------------------------------------------");
                System.out.println("There was an issue loading the list");
                System.out.println("---------------------------------------------------");
                break;
        }
    }

    public void showContents(TaskList taskList) {
        System.out.println("---------------------------------------------------");
        System.out.println("Here are the tasks in your list:");
        int index = 1;
        for (Task task : taskList.getTaskList()) {
            System.out.println(index + ". " + task.toString());
            index++;
        }
        System.out.println("---------------------------------------------------");
    }

}
