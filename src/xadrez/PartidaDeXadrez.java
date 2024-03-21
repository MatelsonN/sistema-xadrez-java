package xadrez;

import jogodetabuleiro.Tabuleiro;

public class PartidaDeXadrez {

    private Tabuleiro tabuleiro;

    public PartidaDeXadrez() {
        tabuleiro = new Tabuleiro(8, 8);
    }

    public PecaDeXadrez[][] pegarPecas() {
        PecaDeXadrez[][] tabuPeca = new PecaDeXadrez[tabuleiro.getLinha()][tabuleiro.getColuna()];
        for (int i = 0; i < tabuleiro.getLinha(); i++) {
            for (int j = 0; j < tabuleiro.getColuna(); j++) {
                tabuPeca[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j);
            }
        }
        return tabuPeca;
    }
}
