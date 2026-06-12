package DefendTheKingdom.model.entities;

import DefendTheKingdom.model.Mapa;
import java.awt.*;

public class TorreRapida extends Torre {
    private static final int CUSTO = 70;

    public TorreRapida(int col, int row) {
        super(col, row, 90, 12, 12, 80);
    }

    @Override public Color getCor() { return new Color(70, 160, 220); }
    @Override public String getNome() { return "Torre Rápida"; }
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

        // Base
        g.setColor(new Color(40, 100, 160));
        g.fillRoundRect(px + 6, py + 6, cs - 12, cs - 12, 8, 8);

        // Tower body
        g.setColor(getCor());
        g.fillRoundRect(px + 10, py + 10, cs - 20, cs - 20, 6, 6);

        // Barrel (pointing right)
        g.setColor(new Color(30, 80, 130));
        g.fillRect(px + cs / 2, py + cs / 2 - 3, cs / 2 - 4, 6);

        // Level dots
        drawLevelDots(g, px, py, cs);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 9));
        g.drawString("R", px + cs / 2 - 4, py + cs / 2 + 4);
    }

    private void drawLevelDots(Graphics2D g, int px, int py, int cs) {
        for (int i = 0; i < nivel; i++) {
            g.setColor(new Color(255, 220, 0));
            g.fillOval(px + 8 + i * 9, py + cs - 14, 6, 6);
        }
    }
}
