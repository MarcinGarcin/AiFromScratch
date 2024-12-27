package ResolutionEnhancer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public  class DataExtractor {

    public static void extractData(BufferedImage image, String outputCsvPath) {
        int[] pixels = new int[13];
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputCsvPath, true))) {

            for (int x = 0; x < image.getWidth() - 2; x += 3) {
                for (int y = 0; y < image.getHeight() - 2; y += 3) {
                    pixels[0] = image.getRGB(x, y);
                    pixels[1] = image.getRGB(x + 2, y);
                    pixels[2] = image.getRGB(x, y + 2);
                    pixels[3] = image.getRGB(x + 2, y + 2);
                    pixels[4] = pixels[0];
                    pixels[5] = image.getRGB(x + 1, y);
                    pixels[6] = pixels[1];
                    pixels[7] = image.getRGB(x, y + 1);
                    pixels[8] = image.getRGB(x + 1, y + 1);
                    pixels[9] = image.getRGB(x + 2, y + 1);
                    pixels[10] = pixels[2];
                    pixels[11] = image.getRGB(x + 1, y + 2);
                    pixels[12] = pixels[3];

                    StringBuilder line = new StringBuilder();
                    for (int i = 0; i < pixels.length; i++) {
                        Color color = new Color(pixels[i]);
                        line.append(color.getRed()).append(",")
                                .append(color.getGreen()).append(",")
                                .append(color.getBlue());
                        if (i < pixels.length - 1) {
                            line.append(",");
                        }
                    }

                    writer.write(line.toString());
                    writer.newLine();
                }
            }
            System.out.println("Data successfully saved to " + outputCsvPath);
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }
}
