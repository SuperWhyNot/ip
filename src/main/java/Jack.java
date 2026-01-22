import java.util.Scanner;

public class Jack {
    private static final String line = "____________________________________________________________";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String[] inputList = new String[100];
        int count = 0;
        System.out.println(line);
        welcome();
        while (true) {
            String command = input.nextLine();
            if (command.equals("bye")) {
                bye();
                break;
            } else if (command.equals("list")) {
                list(inputList, count);
            }
            else {
                inputList = add(inputList, count, command);
                count = count + 1;
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

    private static String[] add(String[] list, int count, String c) {
        System.out.println(line);
        System.out.println("added: " + c);
        System.out.println(line);
        list[count] = c;
        return list;
    }
    private static void list(String[] list, int count) {
        System.out.println(line);
        for (int j = 1; j <= count; j++) {
            System.out.println(j + ". " + list[j - 1]);
        }
        System.out.println(line);
    }
}