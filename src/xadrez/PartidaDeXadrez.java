package xadrez;

import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.peca.King;
import xadrez.peca.Rook;

public class PartidaDeXadrez {

    private Tabuleiro tabuleiro;

    public PartidaDeXadrez() {
        tabuleiro = new Tabuleiro(8, 8);
        configuracaoInicial();
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

    public void configuracaoInicial() {
        tabuleiro.lugarDaPeca(new Rook(tabuleiro, Cor.BRANCO), new Posicao(2, 1));
        tabuleiro.lugarDaPeca(new King(tabuleiro, Cor.PRETO), new Posicao(0, 4));
        tabuleiro.lugarDaPeca(new King(tabuleiro, Cor.BRANCO), new Posicao(7, 4));
    }
}
