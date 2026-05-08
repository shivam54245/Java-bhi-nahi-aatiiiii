import java.util.Scanner;

public class ConsoleIO_sample {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static String read(String prompt) {
        System.out.print(prompt + " ");
        return SCANNER.nextLine().trim();
    }

    public static void println(String message) {
        System.out.println(message);
    }
}
