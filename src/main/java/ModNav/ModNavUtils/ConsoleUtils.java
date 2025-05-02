package ModNav.ModNavUtils;

public class ConsoleUtils {
    public static void clearScreen() {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            ProcessBuilder processBuilder;
            if (os.contains("windows")) {
                processBuilder = new ProcessBuilder("cmd", "/c", "cls");
            }
            else {
                processBuilder = new ProcessBuilder("clear");
            }

            Process process = processBuilder.inheritIO().start();
            process.waitFor();
        }
        catch (Exception e) {
            System.out.println("Error while clearing screen: " + e.getMessage());
        }
    }
}

