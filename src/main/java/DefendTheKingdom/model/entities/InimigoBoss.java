package DefendTheKingdom.model.entities;

import java.awt.*;
import java.util.List;

public class InimigoBoss extends Inimigo {
    public InimigoBoss(List<Point> caminho) {
        super(caminho, 700, 0.9, 90, 5);
    }

    @Override public Color getCor() { return new Color(120, 0, 180); }
    @Override public String getNome() { return "Boss"; }
    @Override public int getRaio() { return 20; }
}