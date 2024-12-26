package DigitRecognizer;

import java.util.ArrayList;
import java.util.List;

public  class Layer {
    List<Neuron> neurons;

    public Layer(int inputSize, int neuronCount) {
        neurons = new ArrayList<>();
        for (int i = 0; i < neuronCount; i++) {
            neurons.add(new Neuron(inputSize));
        }
    }

    public double[] forward(double[] inputs) {
        double[] outputs = new double[neurons.size()];
        for (int i = 0; i < neurons.size(); i++) {
            outputs[i] = neurons.get(i).compute(inputs);
        }
        return outputs;
    }
}
