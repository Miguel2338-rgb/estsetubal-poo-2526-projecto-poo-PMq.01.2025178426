package jogo;

public class Jogador {
    private int moedas;
    private int vida;
    private static final int VIDA_INICIAL = 20;
    private static final int MOEDAS_INICIAIS = 150;

    public Jogador() {
        this.moedas = MOEDAS_INICIAIS;
        this.vida = VIDA_INICIAL;
    }

    public int getMoedas() { return moedas; }
    public int getVida() { return vida; }

    public boolean gastarMoedas(int custo) {
        if (moedas >= custo) {
            moedas -= custo;
            return true;
        }
        return false;
    }

    public void adicionarMoedas(int valor) { moedas += valor; }

    public void perderVida(int dano) {
        vida = Math.max(0, vida - dano);
    }

    public boolean estaVivo() { return vida > 0; }

    public void reset() {
        moedas = MOEDAS_INICIAIS;
        vida = VIDA_INICIAL;
    }
}