package DigitRecognizer;

import java.io.IOException;
import java.util.List;

public class Main {
    //2000 iterations 51% accuracy


    public static void main(String[] args) throws IOException {
        CsvSplitter splitter = new CsvSplitter();
        splitter.setFilePath("/home/marcin/IdeaProjects/AI from scratch/src/main/java/DigitRecognizer/data/train.csv");
        List<MNISTData> data = splitter.splitData();

        NeuralNetwork network = new NeuralNetwork();
        network.train(data,2000);

        splitter.setFilePath("/home/marcin/IdeaProjects/AI from scratch/src/main/java/DigitRecognizer/data/test.csv");
        data = splitter.splitData();
        network.test(data);
    }
}
