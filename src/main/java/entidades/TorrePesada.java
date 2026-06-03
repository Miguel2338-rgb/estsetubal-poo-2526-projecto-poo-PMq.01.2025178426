package entidades;

import utils.Posicao;

public class TorrePesada extends Torre {
    private double bonusDano;

    public TorrePesada(Posicao posicao) {
        super(8, 3.0, 1.5, posicao);
        this.bonusDano = 1.5;
    }

    @Override
    public void atacar(Inimigo inimigo) {
        inimigo.receberDano((int)(dano * bonusDano));
    }
}
