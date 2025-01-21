import java.util.ArrayList;
import java.util.Scanner;
public class ZBOT {
    private static final ArrayList<Task> tasks = new ArrayList<>();
    private void generateResponse(String input) {
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
        }
    }

    private void addContent(String type, String description) {
        switch(type) {
            case "todo":
                Task newToDoTask = new ToDoTask(description);
                tasks.add(newToDoTask);
                System.out.println("---------------------------------------------------");
                System.out.println("Got it. I've added this task:");
                System.out.println(newToDoTask.toString());
                System.out.printf("Now you have %d tasks in the list.%n", tasks.size());
                System.out.println("---------------------------------------------------");
                break;
            case "event":
                String[] eventParts = description.split(" /");
                Task newEventTask = new EventTask(eventParts[0], eventParts[1].substring(5), eventParts[2].substring(3));
                tasks.add(newEventTask);
                System.out.println("---------------------------------------------------");
                System.out.println("Got it. I've added this task:");
                System.out.println(newEventTask.toString());
                System.out.printf("Now you have %d tasks in the list.%n", tasks.size());
                System.out.println("---------------------------------------------------");
                break;
            case "deadline":
                String[] parts = description.split(" /by ");
                Task newDeadlineTask = new DeadlineTask(parts[0], parts[1]);
                tasks.add(newDeadlineTask);
                System.out.println("---------------------------------------------------");
                System.out.println("Got it. I've added this task:");
                System.out.println(newDeadlineTask.toString());
                System.out.printf("Now you have %d tasks in the list.%n", tasks.size());
                System.out.println("---------------------------------------------------");
                break;
        }
    }

    private void showContents() {
        int size = tasks.size();
        System.out.println("---------------------------------------------------");
        System.out.println("Here are the tasks in your list:");
        for (int i = 1; i < size + 1; i++) {
            System.out.println(i + ". " + tasks.get(i - 1).toString());
        }
        System.out.println("---------------------------------------------------");
    }

    private void markTask(int index) {
        tasks.get(index).markDone();
        System.out.println("---------------------------------------------------");
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(tasks.get(index).toString());
        System.out.println("---------------------------------------------------");
    }

    private void unmarkTask(int index) {
        tasks.get(index).markUndone();
        System.out.println("---------------------------------------------------");
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(tasks.get(index).toString());
        System.out.println("---------------------------------------------------");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ZBOT myBot = new ZBOT();
        String input;
        myBot.generateResponse("start");;

        do {
            input = scanner.nextLine();
            String[] parts = input.split(" ", 2);
            if (input.equals("list")) {
                myBot.showContents();
            } else if (parts[0].equals("mark")){
                myBot.markTask(Integer.parseInt(parts[1]) - 1);
            } else if (parts[0].equals("unmark")) {
                myBot.unmarkTask(Integer.parseInt(parts[1]) - 1);
            } else if (parts[0].equals("todo")) {
                myBot.addContent(parts[0], parts[1]);
            } else if (parts[0].equals("deadline")) {
                myBot.addContent(parts[0], parts[1]);
            } else if (parts[0].equals("event")) {
                myBot.addContent(parts[0], parts[1]);
            }
        } while (!input.equals("bye"));

        myBot.generateResponse("end");
    }
}

