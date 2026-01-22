import java.util.Scanner;

public class Jack {
    private static final String line = "____________________________________________________________";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println(line);
        welcome();
        while (true) {
            String command = input.nextLine();
            if (command.equals("bye")) {
                bye();
                break;
            } else {
                echo(command);
            }
        }
    }

    private static void welcome() {
        System.out.println(line);
        System.out.println("Hello I'm Jack");
        System.out.println("What can I do for you?");
        System.out.println(line);
    }

    private static void bye() {
        System.out.println(line);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(line);
    }

    private static void echo(String c) {
        System.out.println(line);
        System.out.println(c);
        System.out.println(line);
    }
}