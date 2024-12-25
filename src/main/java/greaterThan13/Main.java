package greaterThan13;

public class Main {
    public static void main(String[] args) {
        //AI for predicting if number is greater than 13
        NeuralNetwork network = new NeuralNetwork();
        NeuralNetwork.testNetwork(network);
        network.train(1000000);
        network.testNetwork(network);

    }
}
