package utils;

public class Posicao {
    private double x;
    private double y;

    public Posicao(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distancia(Posicao outra) {
        double dx = this.x - outra.x;
        double dy = this.y - outra.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
