package ResolutionEnhancer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvSplitter {


    public static List<MatrixData> splitData(String filePath) throws IOException {
        List<MatrixData> dataset = new ArrayList<>();
        double[] input = new double[12];
        double[] output = new double[27];

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                for(int i = 0;i<12;i++){
                    input[i] = Double.parseDouble(values[i]);
                }
                for(int j = 0;j<27;j++){
                    output[j] = Double.parseDouble(values[j+12]);
                }



                dataset.add(new MatrixData(input,output));
            }
        }

        return dataset;
    }
}