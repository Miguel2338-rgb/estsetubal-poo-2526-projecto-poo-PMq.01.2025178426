package entidades;

import utils.Posicao;

public abstract class Inimigo {
    protected int vida;
    protected double velocidade;
    protected int recompensa;
    protected Posicao posicaoAtual;

    public Inimigo(int vida, double velocidade, int recompensa, Posicao posicaoInicial) {
        this.vida = vida;
        this.velocidade = velocidade;
        this.recompensa = recompensa;
        this.posicaoAtual = posicaoInicial;
    }

    public abstract void mover();

    public void receberDano(int dano) {
        vida -= dano;
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public Posicao getPosicao() {
        return posicaoAtual;
    }

    public int getRecompensa() {
        return recompensa;
    }
}

