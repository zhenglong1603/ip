import java.util.ArrayList;
import java.util.Scanner;
public class ZBOT {
    private static final ArrayList<String> tasks = new ArrayList<>();

    public static void intro() {
        System.out.println("---------------------------------------------------");
        System.out.println("Hello! I'm ZBOT!!");
        System.out.println("What can I do for you?");
        System.out.println("---------------------------------------------------");
    }
    public static void end() {
        System.out.println("---------------------------------------------------");
        System.out.println("Bye. Hope to see you again soon :)");
        System.out.println("---------------------------------------------------");
    }

    public static void showContents() {
        int size = tasks.size();
        System.out.println("---------------------------------------------------");
        System.out.println("<List>");
        for (int i = 1; i < size + 1; i ++) {
            System.out.println(i + ". " + tasks.get(i - 1));
        }
        System.out.println("---------------------------------------------------");

    }

    public static void addContent(String input) {
        tasks.add(input);
        System.out.println("---------------------------------------------------");
        System.out.println(" Successfully Added: " + input);
        System.out.println("---------------------------------------------------");

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        intro();

        do {
            input = scanner.nextLine();
            if (input.equals("list")) {
                showContents();
            } else if (!input.equals("bye")){
                addContent(input);
            }
        } while (!input.equals("bye"));

        end();
    }
}

