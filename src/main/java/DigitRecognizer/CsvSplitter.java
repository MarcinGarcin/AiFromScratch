package DigitRecognizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvSplitter {
    private String filePath;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<MNISTData> splitData() throws IOException {
        List<MNISTData> dataset = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");


                int label = Integer.parseInt(values[0]);


                double[] pixels = new double[784];
                for (int i = 0; i < 784; i++) {
                    pixels[i] = Double.parseDouble(values[i + 1]) / 255.0;
                }

                dataset.add(new MNISTData(pixels, label));
            }
        }

        return dataset;
    }
}