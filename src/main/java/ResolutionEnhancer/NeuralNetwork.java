package ResolutionEnhancer;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {
    private static final int INPUT_SIZE = 12;
    private static final int SECOND_SIZE = 17;
    private static final int THIRD_SIZE = 22;
    private static final int OUTPUT_SIZE = 27;

    private final List<Layer> layers;
    private double bestFitness = Double.MIN_VALUE;

    public NeuralNetwork() {
        layers = new ArrayList<>();
        layers.add(new Layer(INPUT_SIZE, SECOND_SIZE));
        layers.add(new Layer(SECOND_SIZE, THIRD_SIZE));
        layers.add(new Layer(THIRD_SIZE, OUTPUT_SIZE));
    }

    public double[] predict(double[] input) {
        if (input.length != INPUT_SIZE) {
            throw new IllegalArgumentException("Input size must be " + INPUT_SIZE);
        }

        double[] normalized = normalizeInput(input);
        double[] current = normalized;

        for (Layer layer : layers) {
            current = layer.forward(current);
        }

        return denormalizeOutput(current);
    }

    public void train(List<MatrixData> trainingData, int generations) {
        for (int gen = 0; gen < generations; gen++) {
            mutateLayers();

            double fitness = calculateFitness(trainingData);
            System.out.println("Generation " + gen + ": Fitness = " + String.format("%.20f", fitness));

            if (fitness > bestFitness) {
                bestFitness = fitness;
                rememberLayers();
            } else {
                forgetLayers();
            }
        }
    }

    public void test(List<MatrixData> testData) {
        double fitness = calculateFitness(testData);

        System.out.println("\nTesting Results:");
        System.out.println("Overall Fitness: " + String.format("%.4f", fitness));
        System.out.println("Best Fitness Achieved: " + String.format("%.4f", bestFitness));

        int correct = 0;
        for (MatrixData sample : testData) {
            double[] prediction = predict(sample.input);
            double mse = meanSquaredError(prediction, sample.output);

            if (mse < 0.01) {
                correct++;
            } else {
                System.out.println("High MSE Sample: " + String.format("%.4f", mse));
            }
        }

        double accuracy = (double) correct / testData.size();
        System.out.println("Correct Predictions: " + correct + "/" + testData.size());
        System.out.println("Accuracy: " + String.format("%.2f%%", accuracy * 100));
    }

    private double calculateFitness(List<MatrixData> data) {
        int correct = 0;
        int total = data.size();
        double errorThreshold = 0.4;

        for (MatrixData sample : data) {
            double[] prediction = predict(sample.input);
            double[] difference = calculateDifference(prediction, sample.output);

            if (isWithinError(difference, errorThreshold)) {
                correct++;
            }
        }
        return (double) correct / total;
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

    private double[] calculateDifference(double[] arr1, double[] arr2) {
        double[] difference = new double[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            difference[i] = Math.abs(arr1[i] - arr2[i]);
        }
        return difference;
    }

    private boolean isWithinError(double[] arr, double threshold) {
        for (double val : arr) {
            if (val > threshold) {
                return false;
            }
        }
        return true;
    }

    private double meanSquaredError(double[] arr1, double[] arr2) {
        double sum = 0;
        for (int i = 0; i < arr1.length; i++) {
            sum += Math.pow(arr1[i] - arr2[i], 2);
        }
        return sum / arr1.length;
    }
}