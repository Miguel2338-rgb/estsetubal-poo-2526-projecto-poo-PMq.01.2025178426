package entidades;

import utils.Posicao;

public class InimigoRapido extends Inimigo {
    private double fatorVelocidade;

    public InimigoRapido(Posicao inicial) {
        super(15, 1.5, 5, inicial);
        this.fatorVelocidade = 1.3;
    }
    @Override
    public void mover(){
        posicaoAtual = new Posicao(posicaoAtual.getX(), posicaoAtual.getY());
    }
}
