package greaterThan13;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NeuralNetwork {
    private final List<Neuron> neurons;
    private double bestFitness = Double.MIN_VALUE;
    private final List<TrainingData> trainingData;

    public NeuralNetwork() {
        neurons = new ArrayList<>(List.of(
                new Neuron(),                 // Input layer
                new Neuron(), new Neuron(),  // Hidden layer
                new Neuron()                 // Output neuron
        ));
        trainingData = generateTrainingData();
    }

    private List<TrainingData> generateTrainingData() {
        List<TrainingData> data = new ArrayList<>();


        for (int i = 0; i <= 13; i++) {
            data.add(new TrainingData(i, 0.0));
        }


        for (int i = 14; i <= 30; i++) {
            data.add(new TrainingData(i, 1.0));
        }


        data.add(new TrainingData(13.1, 1.0));
        data.add(new TrainingData(13.5, 1.0));
        data.add(new TrainingData(12.9, 0.0));
        data.add(new TrainingData(12.5, 0.0));

        return data;
    }

    public Double predict(Double input) {

        Double normalizedInput = input / 30.0;  //Range 0-30


        Double output1 = neurons.get(0).compute(normalizedInput, normalizedInput);
        Double output2 = neurons.get(1).compute(output1, output1);
        Double output3 = neurons.get(2).compute(output1, output2);

        return neurons.get(3).compute(output2, output3);
    }

    public void train(int generations) {
        for (int gen = 0; gen < generations; gen++) {

            for (Neuron neuron : neurons) {
                neuron.mutate();
            }


            double fitness = calculateFitness();
            //todo potem wywali
            //System.out.println("generation " + gen + ": " + fitness);


            if (fitness > bestFitness) {
                bestFitness = fitness;
                for (Neuron neuron : neurons) {
                    neuron.remember();
                }
            } else {

                for (Neuron neuron : neurons) {
                    neuron.forget();
                }
            }
        }
    }

    private double calculateFitness() {
        double totalError = 0;


        for (TrainingData data : trainingData) {
            double prediction = predict(data.input);
            double error = Math.abs(data.expectedOutput - prediction);
            totalError += error;
        }


        return trainingData.size() - totalError;
    }

    public static void testNetwork(NeuralNetwork network) {
        double[] testValues = {5.0, 10.0, 12.9, 13.0, 13.1, 15.0, 20.0};
        for (double value : testValues) {
            double prediction = network.predict(value);
            System.out.printf("Input: %.1f -> Prediction: %.3f (Expected: %d)%n",
                    value, prediction, value > 13 ? 1 : 0);
        }
    }
}