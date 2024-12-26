package DigitRecognizer;

import java.util.Random;

public class Neuron {
    private double[] weights;
    private double[] bestWeights;
    private double learningRate = 0.1;
    private final Random random = new Random();

    public Neuron(int inputSize) {
        weights = new double[inputSize];
        bestWeights = new double[inputSize];

        for (int i = 0; i < inputSize; i++) {
            weights[i] = random.nextDouble() * 2 - 1;
        }
    }

    public double compute(double[] inputs) {
        if (inputs.length != weights.length) {
            throw new IllegalArgumentException("Input size doesn't match weights size");
        }

        double sum = 0;
        for (int i = 0; i < inputs.length; i++) {
            sum += inputs[i] * weights[i];
        }


        return Utility.relu(sum);
    }

    public void mutate() {
        for (int i = 0; i < weights.length; i++) {
            //todo change mutation rate
            if (random.nextDouble() < 0.4) {
                weights[i] += random.nextGaussian() * learningRate;
            }
        }
    }

    public void remember() {
        System.arraycopy(weights, 0, bestWeights, 0, weights.length);
    }

    public void forget() {
        System.arraycopy(bestWeights, 0, weights, 0, weights.length);
    }
}