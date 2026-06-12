package entidades;

import jogo.Mapa;
import java.awt.*;
import java.util.List;

public abstract class Torre {
    protected int col, row;          // grid position
    protected double alcance;        // pixels
    protected int dano;
    protected int cadencia;          // ticks between shots
    protected int ticksCooldown;
    protected int nivel;
    protected int custoUpgrade;

    public Torre(int col, int row, double alcance, int dano, int cadencia, int custoUpgrade) {
        this.col = col;
        this.row = row;
        this.alcance = alcance;
        this.dano = dano;
        this.cadencia = cadencia;
        this.ticksCooldown = 0;
        this.nivel = 1;
        this.custoUpgrade = custoUpgrade;
    }

    public Inimigo attack(List<Inimigo> inimigos) {
        if (ticksCooldown > 0) {
            ticksCooldown--;
            return null;
        }

        double cx = col * Mapa.CELL_SIZE + Mapa.CELL_SIZE / 2.0;
        double cy = row * Mapa.CELL_SIZE + Mapa.CELL_SIZE / 2.0;

        Inimigo alvo = null;
        double melhorProgresso = -1;

        for (Inimigo e : inimigos) {
            if (e.estaMorto() || e.chegouABase()) continue;
            double dx = e.getX() - cx;
            double dy = e.getY() - cy;
            double dist = Math.sqrt(dx * dx + dy * dy);
            if (dist <= alcance && e.getProgresso() > melhorProgresso) {
                melhorProgresso = e.getProgresso();
                alvo = e;
            }
        }

        if (alvo != null) {
            alvo.receberDano(dano);
            ticksCooldown = cadencia;
            return alvo;
        }
        return null;
    }

    public boolean upgrade(int moedasDisponiveis) {
        if (nivel >= 3 || moedasDisponiveis < custoUpgrade) return false;
        nivel++;
        dano = (int) (dano * 1.5);
        alcance *= 1.15;
        cadencia = Math.max(5, (int) (cadencia * 0.75));
        custoUpgrade = (int) (custoUpgrade * 1.8);
        return true;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public double getAlcance() {
        return alcance;
    }

    public int getDano() {
        return dano;
    }

    public int getCadencia() {
        return cadencia;
    }

    public int getNivel() {
        return nivel;
    }

    public int getCustoUpgrade() {
        return custoUpgrade;
    }

    public double getCentroX() {
        return col * Mapa.CELL_SIZE + Mapa.CELL_SIZE / 2.0;
    }

    public double getCentroY() {
        return row * Mapa.CELL_SIZE + Mapa.CELL_SIZE / 2.0;
    }

    public abstract Color getCor();

    public abstract String getNome();

    public abstract int getCustoBase();

    public abstract void draw(Graphics2D g, boolean selected);
}