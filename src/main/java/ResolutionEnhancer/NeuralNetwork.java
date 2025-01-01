package ResolutionEnhancer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    private static final int INPUT_SIZE = 12;
    private static final int OUTPUT_SIZE = 27;
    private static final int HIDDEN_SIZE = 32;

    private final List<Layer> layers;
    private double bestFitness = Double.MIN_VALUE;

    public NeuralNetwork() {
        layers = new ArrayList<>();

        layers.add(new Layer(INPUT_SIZE, HIDDEN_SIZE));
        layers.add(new Layer(HIDDEN_SIZE, HIDDEN_SIZE));
        layers.add(new Layer(HIDDEN_SIZE, OUTPUT_SIZE));
    }



    public double[] predict(double[] input) {
        if (input.length != INPUT_SIZE) {
            throw new IllegalArgumentException("Input size must be " + INPUT_SIZE + " (2x2 RGB image)");
        }

        double[] normalized = normalizeInput(input);
        double[] current = normalized;

        for (Layer layer : layers) {
            current = layer.forward(current);
        }

        return denormalizeOutput(current);
    }

    public void train(List<MatrixData> trainingData, int generations) {
        double prevFitness = Double.MIN_VALUE;
        int stagnantGenerations = 0;

        for (int gen = 0; gen < generations; gen++) {
            mutateLayers();

            double fitness = calculateFitness(trainingData);
            System.out.println("Generation " + gen + ": Fitness = " + String.format("%.20f", fitness));

            if (fitness > bestFitness) {
                bestFitness = fitness;
                rememberLayers();
                stagnantGenerations = 0;
            } else {
                forgetLayers();
                stagnantGenerations++;
            }

            if (stagnantGenerations > 10) {
                adjustMutationRate(true);
            } else if (fitness > prevFitness) {
                adjustMutationRate(false);
            }

            prevFitness = fitness;
        }
    }

    private double calculateFitness(List<MatrixData> data) {
        double totalError = 0;
        for (MatrixData sample : data) {
            double[] prediction = predict(sample.input);
            totalError += calculateRGBError(prediction, sample.output);
        }
        return 1.0 / (1.0 + totalError / data.size());
    }

    private double calculateRGBError(double[] predicted, double[] actual) {
        double error = 0;
        for (int i = 0; i < predicted.length; i += 3) {
            double rError = Math.pow(predicted[i] - actual[i], 2);
            double gError = Math.pow(predicted[i + 1] - actual[i + 1], 2);
            double bError = Math.pow(predicted[i + 2] - actual[i + 2], 2);
            error += (rError + gError + bError) / 3.0;
        }
        return error / (predicted.length / 3);
    }

    private double[] normalizeInput(double[] input) {
        double[] normalized = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            normalized[i] = input[i] / 255.0;
        }
        return normalized;
    }

    private double[] denormalizeOutput(double[] output) {
        double[] denormalized = new double[output.length];
        for (int i = 0; i < output.length; i++) {
            denormalized[i] = Math.min(Math.max(output[i] * 255.0, 0), 255);
        }
        return denormalized;
    }

    private void mutateLayers() {
        for (Layer layer : layers) {
            for (Neuron neuron : layer.neurons) {
                neuron.mutate();
            }
        }
    }

    private void rememberLayers() {
        for (Layer layer : layers) {
            for (Neuron neuron : layer.neurons) {
                neuron.remember();
            }
        }
    }

    private void forgetLayers() {
        for (Layer layer : layers) {
            for (Neuron neuron : layer.neurons) {
                neuron.forget();
            }
        }
    }
    private void adjustMutationRate(boolean increase) {
        double factor = increase ? 1.5 : 0.8;
        for (Layer layer : layers) {
            for (Neuron neuron : layer.neurons) {
                neuron.adjustMutationRate(factor);
            }
        }
    }
    public void saveToFile(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(this);
            System.out.println("Neural network saved to: " + filePath);
        }
    }

    public static NeuralNetwork loadFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            NeuralNetwork network = (NeuralNetwork) ois.readObject();
            System.out.println("Neural network loaded from: " + filePath);
            return network;
        }
    }
}