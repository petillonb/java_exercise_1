import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;

interface Command {
    public String name();

    public boolean run(Scanner var1);
}

class Fibo
        implements Command {
    Fibo() {
    }

    @Override
    public String name() {
        return "fibo";
    }

    @Override
    public boolean run(Scanner scanner) {
        try {
            System.out.println("Entrez nombre");
            int n = scanner.nextInt();
            int n2 = 0;
            int n3 = 1;
            int n4 = 0;
            for (int i = 1; i < n; ++i) {
                n4 = n3 + n2;
                n2 = n3;
                n3 = n4;
            }
            System.out.println("fibo(" + n + ") = " + n4);
            return false;
        } catch (Exception exception) {
            System.err.println("Error: " + exception.toString());
            return true;
        }
    }
}

class Freq
        implements Command {
    Freq() {
    }

    @Override
    public String name() {
        return "freq";
    }

    @Override
    public boolean run(Scanner scanner) {
        try {
            System.out.println("Entrez un chemin de fichier...");
            String filepath = scanner.nextLine();
            try {
                Scanner scanner2 = new Scanner(new File(filepath));
                HashMap<String, Integer> dictionary = new HashMap<String, Integer>();
                String firstword = null;

                while (scanner2.hasNextLine()) {
                    String line = scanner2.nextLine();
                    String[] words = line.replaceAll("\n", "").replaceAll("[.,?!;&:]", "").split(" ");

                    for (String word : words) {
                        if (firstword == null) {
                            firstword = word;
                        }
                        if (dictionary.containsKey(word) == true) {
                            int val = dictionary.get(word);
                            dictionary.replace(word, val + 1);
                        } else {
                            dictionary.put(word, 1);
                        }
                    }
                }

                for (int i = 0; i < 3; i++) {
                    String max = firstword;
                    for (String key : dictionary.keySet()) {
                        if (dictionary.get(max) < dictionary.get(key)) {
                            max = key;
                        }
                    }
                    int count = i + 1;
                    System.out.println(
                            "Top " + count + " word is " + max + " with " + dictionary.get(max) + " occurrences !");
                    dictionary.remove(max);
                }

                scanner.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return false;

        } catch (Exception exception) {
            System.err.println("Error: " + exception.toString());
            return true;
        }
    }
}

class Quit
        implements Command {
    Quit() {
    }

    @Override
    public String name() {
        return "quit";
    }

    @Override
    public boolean run(Scanner scanner) {
        return true;
    }
}

class launcher {
    launcher() {
    }

    public static void main(String[] arrstring) {
        Command[] arrcommand = new Command[] { new Fibo(), new Freq(), new Quit() };
        List<Command> list = Arrays.asList(arrcommand);
        Scanner scanner = new Scanner(System.in);
        String string = "";
        boolean bl = true;
        while (bl) {
            System.out.println("Entrez commande");
            string = scanner.nextLine();
            boolean bl2 = true;
            for (Command command : list) {
                if (!string.equalsIgnoreCase(command.name()))
                    continue;
                bl = !command.run(scanner);
                bl2 = false;
                break;
            }
            if (!bl2)
                continue;
            System.out.println("Unknown command");
        }
        scanner.close();
    }
}