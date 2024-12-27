package ResolutionEnhancer;

import java.util.ArrayList;
import java.util.List;

class Layer {
    public List<Neuron> neurons;
    private double[] activations;
    private double[] z;

    public Layer(int inputSize, int outputSize) {
        neurons = new ArrayList<>();
        for (int i = 0; i < outputSize; i++) {
            neurons.add(new Neuron(inputSize));
        }
    }

    public double[] forward(double[] input) {
        activations = new double[neurons.size()];
        z = new double[neurons.size()];
        for (int i = 0; i < neurons.size(); i++) {
            z[i] = 0;
            for (int j = 0; j < input.length; j++) {
                z[i] += input[j] * neurons.get(i).weights[j];
            }
            z[i] += neurons.get(i).bias;
            activations[i] = Utility.leakyRelu(z[i]);
        }
        return activations;
    }
    public double[] getActivations(){
        return activations;
    }
    public double[] getZ(){
        return z;
    }
}
