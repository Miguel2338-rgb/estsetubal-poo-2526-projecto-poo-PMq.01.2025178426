package entidades;

import jogo.Mapa;
import java.awt.*;
import java.util.List;

public abstract class Inimigo {
    protected double x, y;          // pixel position (center)
    protected int vidaMax;
    protected int vidaAtual;
    protected double velocidade;    // pixels per tick
    protected int recompensa;
    protected int danoBase;         // damage dealt to player when reaching base

    private List<Point> caminho;
    private int pathIndex;
    private boolean chegouABase;
    private boolean morto;

    public Inimigo(List<Point> caminho, int vidaMax, double velocidade, int recompensa, int danoBase) {
        this.caminho = caminho;
        this.vidaMax = vidaMax;
        this.vidaAtual = vidaMax;
        this.velocidade = velocidade;
        this.recompensa = recompensa;
        this.danoBase = danoBase;
        this.pathIndex = 0;
        this.chegouABase = false;
        this.morto = false;

        // Start at first path cell
        if (!caminho.isEmpty()) {
            Point start = caminho.get(0);
            x = start.x * Mapa.CELL_SIZE + Mapa.CELL_SIZE / 2.0;
            y = start.y * Mapa.CELL_SIZE + Mapa.CELL_SIZE / 2.0;
        }
    }

    public void update() {
        if (morto || chegouABase) return;
        if (pathIndex >= caminho.size() - 1) {
            chegouABase = true;
            return;
        }

        Point target = caminho.get(pathIndex + 1);
        double tx = target.x * Mapa.CELL_SIZE + Mapa.CELL_SIZE / 2.0;
        double ty = target.y * Mapa.CELL_SIZE + Mapa.CELL_SIZE / 2.0;

        double dx = tx - x;
        double dy = ty - y;
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist <= velocidade) {
            x = tx;
            y = ty;
            pathIndex++;
        } else {
            x += (dx / dist) * velocidade;
            y += (dy / dist) * velocidade;
        }
    }

    public void receberDano(int dano) {
        vidaAtual -= dano;
        if (vidaAtual <= 0) {
            vidaAtual = 0;
            morto = true;
        }
    }

    public boolean estaMorto() {
        return morto;
    }

    public boolean chegouABase() {
        return chegouABase;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getVidaAtual() {
        return vidaAtual;
    }

    public int getVidaMax() {
        return vidaMax;
    }

    public int getRecompensa() {
        return recompensa;
    }

    public int getDanoBase() {
        return danoBase;
    }

    // Progress 0.0 to 1.0
    public double getProgresso() {
        return (double) pathIndex / Math.max(1, caminho.size() - 1);
    }

    public abstract Color getCor();

    public abstract String getNome();

    public abstract int getRaio();
}
