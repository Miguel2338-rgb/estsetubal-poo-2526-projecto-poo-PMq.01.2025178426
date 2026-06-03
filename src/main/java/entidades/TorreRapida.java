package entidades;

import utils.Posicao;

public class TorreRapida extends Torre {
    private double bonusVelocidade;

    public TorreRapida(Posicao posicao) {
        super(3, 2.5, 0.8, posicao);
        this.bonusVelocidade = 1.2;
    }

    @Override
    public void atacar(Inimigo inimigo) {
        inimigo.receberDano((int)(dano * bonusVelocidade));
    }
}
