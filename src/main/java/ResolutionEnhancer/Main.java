package ResolutionEnhancer;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    private static final String RESOURCES_PATH = "src/main/java/ResolutionEnhancer";
    private static final String trainData = RESOURCES_PATH + "/data/train.csv";
    private static final String testData = RESOURCES_PATH + "/data/test.csv";
    private static final String MODEL_PATH = "src/main/java/trainedModel/trainedModel.csv";

    public static void main(String[] args) throws IOException {

        String projectDir = System.getProperty("user.dir");


        String trainDataPath = projectDir + File.separator + trainData.replace("/", File.separator);
        String modelPath = projectDir + File.separator + MODEL_PATH.replace("/", File.separator);

        //todo żeby wygenerować dane do nauczania należy najpierw odkomentować poniższy kod

//                try {
//            String imagePath = projectDir + File.separator + RESOURCES_PATH +  File.separator + "images" + File.separator + "image1.jpg";
//            BufferedImage image = ImageIO.read(new File(imagePath));
//            DataExtractor.extractData(image, trainDataPath);
//            imagePath = projectDir + File.separator + RESOURCES_PATH +  File.separator + "images" + File.separator + "image1.jpg";
//            image = ImageIO.read(new File(imagePath));
//            DataExtractor.extractData(image, trainDataPath);
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//       }

        List<MatrixData> data = CsvSplitter.splitData(trainDataPath);
        NeuralNetwork neuralNetwork = new NeuralNetwork();
        neuralNetwork.train(data, 10000);
        neuralNetwork.saveToFile(modelPath);





    }
}
