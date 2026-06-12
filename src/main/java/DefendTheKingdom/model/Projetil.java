package DefendTheKingdom.model;

import java.awt.*;

public class Projetil {
    private double x, y;
    private double tx, ty;
    private double speed = 8.0;
    private boolean ativo;
    private Color cor;

    public Projetil(double sx, double sy, double tx, double ty, Color cor) {
        this.x = sx; this.y = sy;
        this.tx = tx; this.ty = ty;
        this.cor = cor;
        this.ativo = true;
    }

    public void update() {
        double dx = tx - x, dy = ty - y;
        double dist = Math.sqrt(dx * dx + dy * dy);
        if (dist <= speed) { ativo = false; return; }
        x += (dx / dist) * speed;
        y += (dy / dist) * speed;
    }

    public boolean isAtivo() { return ativo; }
    public double getX() { return x; }
    public double getY() { return y; }
    public Color getCor() { return cor; }
}
