package ResolutionEnhancer;

import java.util.Random;

public class Neuron {
    public double[] weights;
    public double[] bestWeights;
    public double bias;
    public double bestBias;
    private static final double MUTATION_RATE = 0.2;
    private static final double MUTATION_RANGE = 0.01;
    private final Random random = new Random();

    public Neuron(int inputSize) {
        weights = new double[inputSize];
        bestWeights = new double[inputSize];

        for (int i = 0; i < inputSize; i++) {
            weights[i] = (random.nextDouble() * 2 - 1) * 0.1;
            bestWeights[i] = weights[i];
        }

        bias = (random.nextDouble() * 2 - 1) * 0.1;
        bestBias = bias;
    }

    public double compute(double[] inputs) {
        if (inputs.length != weights.length) {
            throw new IllegalArgumentException("Input size doesn't match weights size");
        }

        double sum = bias;
        for (int i = 0; i < inputs.length; i++) {
            sum += inputs[i] * weights[i];
        }
        return Utility.leakyRelu(sum);
    }

    public void mutate() {
        for (int i = 0; i < weights.length; i++) {
            if (random.nextDouble() < MUTATION_RATE) {
                weights[i] += (random.nextDouble() * 2 - 1) * MUTATION_RANGE;
            }
        }

        if (random.nextDouble() < MUTATION_RATE) {
            bias += (random.nextDouble() * 2 - 1) * MUTATION_RANGE;
        }
    }

    public void remember() {
        System.arraycopy(weights, 0, bestWeights, 0, weights.length);
        bestBias = bias;
    }

    public void forget() {
        System.arraycopy(bestWeights, 0, weights, 0, weights.length);
        bias = bestBias;
    }
}