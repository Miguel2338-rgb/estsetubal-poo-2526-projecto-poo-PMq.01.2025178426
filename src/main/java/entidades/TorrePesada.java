package entidades;

import jogo.Mapa;
import java.awt.*;

public class TorrePesada extends Torre {
    private static final int CUSTO = 330;

    public TorrePesada(int col, int row) {
        super(col, row, 130, 40, 35, 130);
    }

    @Override public Color getCor() { return new Color(200, 100, 50); }
    @Override public String getNome() { return "Torre Pesada"; }
    @Override public int getCustoBase() { return CUSTO; }

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

        // Heavy base
        g.setColor(new Color(100, 50, 20));
        g.fillRoundRect(px + 4, py + 4, cs - 8, cs - 8, 4, 4);

        // Tower body
        g.setColor(getCor());
        g.fillRoundRect(px + 8, py + 8, cs - 16, cs - 16, 6, 6);

        // Big barrel
        g.setColor(new Color(80, 40, 10));
        g.fillRect(px + cs / 2 - 3, py + cs / 2 - 4, cs / 2 - 2, 9);

        // Level dots
        for (int i = 0; i < nivel; i++) {
            g.setColor(new Color(255, 80, 0));
            g.fillOval(px + 8 + i * 9, py + cs - 14, 6, 6);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 9));
        g.drawString("P", px + cs / 2 - 4, py + cs / 2 + 4);
    }
}
