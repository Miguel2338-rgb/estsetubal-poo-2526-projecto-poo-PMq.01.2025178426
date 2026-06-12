package DefendTheKingdom.model.entities;

import java.awt.*;
import java.util.List;

public class InimigoLento extends Inimigo {
    public InimigoLento(List<Point> caminho) {
        super(caminho, 180, 1.2, 26, 2);
    }

    @Override public Color getCor() { return new Color(180, 60, 60); }
    @Override public String getNome() { return "Golem"; }
    @Override public int getRaio() { return 14; }
}
