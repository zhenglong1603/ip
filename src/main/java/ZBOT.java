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

    private void showContents() {
        int size = tasks.size();
        System.out.println("---------------------------------------------------");
        System.out.println("Here are the tasks in your list:");
        for (int i = 1; i < size + 1; i ++) {
            System.out.println(i + ". " + tasks.get(i - 1).toString());
        }
        System.out.println("---------------------------------------------------");
    }

    private void addContent(String input) {
        tasks.add(new Task(input));
        System.out.println("---------------------------------------------------");
        System.out.println(" Successfully Added: " + input);
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
            String[] parts = input.split(" ");
            if (input.equals("list")) {
                myBot.showContents();
            } else if (parts[0].equals("mark")){
                myBot.markTask(Integer.parseInt(parts[1]) - 1);
            } else if (parts[0].equals("unmark")) {
                myBot.unmarkTask(Integer.parseInt(parts[1]) - 1);
            } else if (!input.equals("bye")){
                myBot.addContent(input);
            }
        } while (!input.equals("bye"));

        myBot.generateResponse("end");
    }
}

