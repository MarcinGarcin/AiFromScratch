package DigitRecognizer;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {
    private static final int INPUT_SIZE = 784;
    private static final int HIDDEN_SIZE = 128;
    private static final int OUTPUT_SIZE = 10;

    private final List<Layer> layers;
    private double bestFitness = Double.MIN_VALUE;


    public NeuralNetwork() {
        layers = new ArrayList<>();
        layers.add(new Layer(INPUT_SIZE, HIDDEN_SIZE));
        layers.add(new Layer(HIDDEN_SIZE, OUTPUT_SIZE));
    }

    public int predict(double[] input) {

        double[] normalized = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            normalized[i] = input[i] / 255.0;
        }


        double[] current = normalized;
        for (Layer layer : layers) {
            current = layer.forward(current);
        }


        int maxIndex = 0;
        for (int i = 1; i < current.length; i++) {
            if (current[i] > current[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public void train(List<MNISTData> trainingData, int generations) {
        for (int gen = 0; gen < generations; gen++) {
            for (Layer layer : layers) {
                for (Neuron neuron : layer.neurons) {
                    neuron.mutate();
                }
            }

            double fitness = calculateFitness(trainingData);
            System.out.println("generation " + gen + ": " + fitness);

            if (fitness > bestFitness) {
                bestFitness = fitness;
                for (Layer layer : layers) {
                    for (Neuron neuron : layer.neurons) {
                        neuron.remember();
                    }
                }
            } else {
                for (Layer layer : layers) {
                    for (Neuron neuron : layer.neurons) {
                        neuron.forget();
                    }
                }
            }
        }
    }

    public void test(List<MNISTData> testData) {
        double fitness = calculateFitness(testData);

        System.out.println("Testing results:");
        System.out.println("Accuracy: " + (fitness * 100) + "%");

        int correct = 0;
        for (MNISTData sample : testData) {
            int prediction = predict(sample.pixels);
            if (prediction == sample.label) {
                correct++;
            } else {
                System.out.println("Incorrect prediction: Expected " + sample.label + ", Got " + prediction);
            }
        }

        System.out.println("Total correct predictions: " + correct + " out of " + testData.size());
        System.out.println(correct/testData.size());
    }


    private double calculateFitness(List<MNISTData> data) {
        int correct = 0;
        for (MNISTData sample : data) {
            int prediction = predict(sample.pixels);
            if (prediction == sample.label) {
                correct++;
            }
        }
        return (double) correct / data.size();
    }


}
