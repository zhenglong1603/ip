import java.util.Scanner;
public class ZBOT {

    public static void intro() {
        System.out.println("---------------------------------------------------");
        System.out.println("Hello! I'm ZBOT!!");
        System.out.println("What can I do for you?");
        System.out.println("---------------------------------------------------");
    }
    public static void end() {
        System.out.println("---------------------------------------------------");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("---------------------------------------------------");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        intro();

        do {
            input = scanner.nextLine();
            if (!input.equals("bye")) {
                System.out.println("-----------------------");
                System.out.println(input);
                System.out.println("-----------------------");
            }
        } while (!input.equals("bye"));

        end();
    }
}

