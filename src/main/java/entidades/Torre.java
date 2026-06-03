package entidades;

import utils.Posicao;

public abstract class Torre {
    protected int dano;
    protected double alcance;
    protected double cadencia;
    protected Posicao posicao;

    public Torre(int dano, double alcance, double cadencia, Posicao posicao) {
        this.dano = dano;
        this.alcance = alcance;
        this.cadencia = cadencia;
        this.posicao = posicao;
    }

    public boolean podeAtacar(Inimigo inimigo) {
        return posicao.distancia(inimigo.getPosicao()) <= alcance;
    }

    public abstract void atacar(Inimigo inimigo);



}
