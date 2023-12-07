import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part2 {

    public static Pattern pattern = Pattern.compile("(\\d+) (\\w+)");

    public static void main(String[] args) throws Exception {
        /*String input = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green \n" +
                "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue\n" +
                "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red\n" +
                "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red\n" +
                "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green\n";*/

        String filePath = "day2InputPart2.txt";
        String input = readFileToString(filePath);

        int sum = Arrays.stream(input.split("\n"))
                .map(line -> line.split(":"))
                .mapToInt(arr -> {
                    int gameNum = Integer.parseInt(arr[0].replace("Game", "").trim());
                    int[] counts = Arrays.stream(arr[1].split(";"))
                            .flatMap(rounds -> Arrays.stream(rounds.split(",")))
                            .map(pattern::matcher)
                            .filter(Matcher::find)
                            .collect(() -> new int[3], (acc, m) -> {
                                int quantity = Integer.parseInt(m.group(1));
                                String color = m.group(2);
                                switch (color) {
                                    case "blue":
                                        acc[0] = Math.max(acc[0], quantity);
                                        break;
                                    case "red":
                                        acc[1] = Math.max(acc[1], quantity);
                                        break;
                                    case "green":
                                        acc[2] = Math.max(acc[2], quantity);
                                        break;
                                }
                            }, (left, right) -> {
                                left[0] = Math.max(left[0], right[0]);
                                left[1] = Math.max(left[1], right[1]);
                                left[2] = Math.max(left[2], right[2]);
                            });

                    return counts[0] * counts[1] * counts[2];
                })
                .sum();

        System.out.println(sum);
    }

    private static String readFileToString(String filePath) throws Exception {
        Path path = Paths.get(filePath);
        byte[] fileBytes = Files.readAllBytes(path);
        return new String(fileBytes);
    }
}