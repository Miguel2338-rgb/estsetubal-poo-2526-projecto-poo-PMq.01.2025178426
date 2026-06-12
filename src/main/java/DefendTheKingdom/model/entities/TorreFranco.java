package DefendTheKingdom.model.entities;

import DefendTheKingdom.model.Mapa;
import java.awt.*;

public class TorreFranco extends Torre {
    private static final int CUSTO = 850;

    public TorreFranco(int col, int row) {
        super(col, row, 200, 70, 60, 160);
    }

    @Override public Color getCor() { return new Color(60, 200, 120); }
    @Override public String getNome() { return "Franco-Atirador"; }
    @Override public int getCustoBase() { return CUSTO; }

    @Override
    public Inimigo attack(java.util.List<Inimigo> inimigos) {
        if (ticksCooldown > 0) { ticksCooldown--; return null; }

        double cx = col * Mapa.CELL_SIZE + Mapa.CELL_SIZE / 2.0;
        double cy = row * Mapa.CELL_SIZE + Mapa.CELL_SIZE / 2.0;

        // Target enemy with highest HP
        Inimigo alvo = null;
        int maxVida = 0;
        for (Inimigo e : inimigos) {
            if (e.estaMorto() || e.chegouABase()) continue;
            double dx = e.getX() - cx;
            double dy = e.getY() - cy;
            double dist = Math.sqrt(dx * dx + dy * dy);
            if (dist <= alcance && e.getVidaAtual() > maxVida) {
                maxVida = e.getVidaAtual();
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

    @Override
    public void draw(Graphics2D g, boolean selected) {
        int px = col * Mapa.CELL_SIZE;
        int py = row * Mapa.CELL_SIZE;
        int cs = Mapa.CELL_SIZE;

        if (selected) {
            g.setColor(new Color(255, 255, 0, 60));
            g.fillOval((int)(getCentroX() - alcance), (int)(getCentroY() - alcance),
                    (int)(alcance * 2), (int)(alcance * 2));
            g.setColor(new Color(255, 255, 0, 130));
            g.setStroke(new BasicStroke(1.5f));
            g.drawOval((int)(getCentroX() - alcance), (int)(getCentroY() - alcance),
                    (int)(alcance * 2), (int)(alcance * 2));
            g.setStroke(new BasicStroke(1f));
        }

        g.setColor(new Color(20, 100, 50));
        g.fillRoundRect(px + 5, py + 5, cs - 10, cs - 10, 6, 6);

        g.setColor(getCor());
        g.fillRoundRect(px + 10, py + 10, cs - 20, cs - 20, 5, 5);

        // Long barrel
        g.setColor(new Color(15, 80, 40));
        g.fillRect(px + cs / 2, py + cs / 2 - 2, cs / 2 - 2, 5);

        for (int i = 0; i < nivel; i++) {
            g.setColor(new Color(0, 255, 150));
            g.fillOval(px + 8 + i * 9, py + cs - 14, 6, 6);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 9));
        g.drawString("F", px + cs / 2 - 4, py + cs / 2 + 4);
    }
}
