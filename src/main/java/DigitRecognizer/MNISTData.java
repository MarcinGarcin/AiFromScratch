package DigitRecognizer;

public class MNISTData {
    public double[] pixels;
    public int label;

    public MNISTData(double[] pixels, int label) {
        this.pixels = pixels;
        this.label = label;
    }
}
