package entidades;

import java.awt.*;
import java.util.List;

public class InimigoRapido extends Inimigo {
    public InimigoRapido(List<Point> caminho) {
        super(caminho, 70, 2.5, 12, 1);
    }

    @Override
    public Color getCor() {
        return new Color(80, 180, 80);
    }

    @Override
    public String getNome() {
        return "Goblin";
    }

    @Override
    public int getRaio() {
        return 10;
    }
}