package greaterThan13;

import java.util.Random;

public class Neuron {
    private final Random random = new Random();

    private Double oldBias;
    private Double bias;
    private Double oldWeight1;
    private Double weight1;
    private Double oldWeight2;
    private Double weight2;

    public Neuron() {
        initializeWeights();
    }

    private void initializeWeights() {
        bias = random.nextDouble() * 2 - 1;
        weight1 = random.nextDouble() * 2 - 1;
        weight2 = random.nextDouble() * 2 - 1;
        remember();
    }

    public Double compute(Double input1, Double input2) {
        Double sum = bias + (weight1 * input1) + (weight2 * input2);
        return Utility.relu(sum);
    }

    public void mutate() {
        int propertyToChange = random.nextInt(3);
        double changeFactor = random.nextDouble() * 2 - 1;

        switch (propertyToChange) {
            case 0 -> bias += changeFactor;
            case 1 -> weight1 += changeFactor;
            case 2 -> weight2 += changeFactor;
        }
    }

    public void forget() {
        bias = oldBias;
        weight1 = oldWeight1;
        weight2 = oldWeight2;
    }

    public void remember() {
        oldBias = bias;
        oldWeight1 = weight1;
        oldWeight2 = weight2;
    }


    public Double getBias() { return bias; }
    public Double getWeight1() { return weight1; }
    public Double getWeight2() { return weight2; }
}