package ResolutionEnhancer;

public class Utility {
    public static double sigmoid(double in) {
        return 1 / (1 + Math.exp(-in));
    }

    public static double relu(double input) {
        return Math.max(0, input);
    }

    public static double softmax(double input1, double input2) {

        double exp1 = Math.exp(input1);
        double exp2 = Math.exp(input2);
        double sumExp = exp1 + exp2;
        return exp1 / sumExp;
    }
    public static double leakyRelu(double x) {
        return x > 0 ? x : 0.01 * x;
    }
}
