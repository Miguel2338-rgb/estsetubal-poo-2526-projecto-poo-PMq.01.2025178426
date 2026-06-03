package jogo;

public class Jogador {
    private int moedas;
    private int vida;

    public Jogador(int moedas, int vida) {
        this.moedas = moedas;
        this.vida = vida;
    }

    public boolean gastarMoedas(int qtd) {
        if (moedas >= qtd) {
            moedas -= qtd;
            return true;
        }
        return false;
    }

    public void ganharMoedas(int qtd) {
        moedas += qtd;
    }

    public void perderVida(int qtd) {
        vida -= qtd;
    }

    public int getMoedas() { return moedas; }
    public int getVida() { return vida; }
}

