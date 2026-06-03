package entidades;

import utils.Posicao;

public class InimigoLento extends Inimigo{
    private double fatorResistencia;

    public InimigoLento(Posicao inicial) {
        super(30, 0.5, 10, inicial);
        this.fatorResistencia = 0.8;
    }
    @Override
    public void mover() {
        posicaoAtual = new Posicao(posicaoAtual.getX(), posicaoAtual.getY());
    }
}
