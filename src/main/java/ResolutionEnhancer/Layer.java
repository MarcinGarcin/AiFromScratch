package ResolutionEnhancer;

import java.util.ArrayList;
import java.util.List;

public  class Layer {
    public List<Neuron> neurons;
    private double[] activations;

    public Layer(int inputSize, int outputSize) {
        neurons = new ArrayList<>();
        for (int i = 0; i < outputSize; i++) {
            neurons.add(new Neuron(inputSize));
        }
    }

    public double[] forward(double[] input) {
        activations = new double[neurons.size()];
        for (int i = 0; i < neurons.size(); i++) {
            double sum = neurons.get(i).bias;
            for (int j = 0; j < input.length; j++) {
                sum += input[j] * neurons.get(i).weights[j];
            }
            activations[i] = Utility.elu(sum);
        }
        return activations;
    }


}