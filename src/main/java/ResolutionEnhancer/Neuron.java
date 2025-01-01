package ResolutionEnhancer;

import java.io.Serializable;
import java.util.Random;

public  class Neuron implements Serializable{
    private static final long serialVersionUID = 1L;
    public double[] weights;
    public double[] bestWeights;
    public double bias;
    public double bestBias;
    private double mutationRate = 0.2;
    private static final double INITIAL_WEIGHT_RANGE = 0.1;
    private final Random random = new Random();

    public Neuron(int inputSize) {
        weights = new double[inputSize];
        bestWeights = new double[inputSize];

        double scale = Math.sqrt(2.0 / (inputSize + 1));

        for (int i = 0; i < inputSize; i++) {
            weights[i] = random.nextGaussian() * scale;
            bestWeights[i] = weights[i];
        }

        bias = random.nextGaussian() * scale;
        bestBias = bias;
    }

    public void mutate() {
        for (int i = 0; i < weights.length; i++) {
            if (random.nextDouble() < mutationRate) {
                weights[i] += random.nextGaussian() * INITIAL_WEIGHT_RANGE;
            }
        }

        if (random.nextDouble() < mutationRate) {
            bias += random.nextGaussian() * INITIAL_WEIGHT_RANGE;
        }
    }

    public void adjustMutationRate(double factor) {
        mutationRate = Math.min(0.5, Math.max(0.01, mutationRate * factor));
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