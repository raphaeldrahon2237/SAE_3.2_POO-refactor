package towerdef.model;

import java.io.*;

public class ScoreManager {

    private static final String FILE_NAME = "score.txt";

    public static void saveScore(String playerName, int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(playerName + ";" + score);
        } catch (IOException e) {
            System.out.println("Erreur sauvegarde score : " + e.getMessage());
        }
    }

    public static int loadScore(String playerName) {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return 0;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line = reader.readLine();

            if (line != null) {
                String[] parts = line.split(";");

                if (parts.length == 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);

                    if (name.equals(playerName)) {
                        return score;
                    }
                }
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("Erreur lecture score : " + e.getMessage());
        }

        return 0;
    }
}