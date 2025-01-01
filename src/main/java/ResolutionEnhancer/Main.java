package ResolutionEnhancer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    static String traindata = "/home/marcin/IdeaProjects/AI from scratch/src/main/java/ResolutionEnhancer/data/train.csv";
    static String testData = "/home/marcin/IdeaProjects/AI from scratch/src/main/java/ResolutionEnhancer/data/train.csv";
    public static void main(String[] args) throws IOException {
        List<MatrixData> data = CsvSplitter.splitData(traindata);
        NeuralNetwork neuralNetwork = new NeuralNetwork();
        neuralNetwork.train(data,1000);
        neuralNetwork.saveToFile("/home/marcin/IdeaProjects/AI from scratch/src/main/java/trainedModel/trainedModel.csv");



//        try {
//            BufferedImage image = ImageIO.read(new File("/home/marcin/IdeaProjects/AI from scratch/src/main/java/ResolutionEnhancer/images/imagetest.jpg"));
//            DataExtractor.extractData(image,"/home/marcin/IdeaProjects/AI from scratch/src/main/java/ResolutionEnhancer/data/test.csv");
//
//        }catch (IOException e){
//            System.out.println(e.getMessage());
//        }
    }
}
