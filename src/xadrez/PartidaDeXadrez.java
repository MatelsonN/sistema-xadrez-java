package xadrez;

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
        PecaDeXadrez[][] tabuPeca = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        for (int i = 0; i < tabuleiro.getLinhas(); i++) {
            for (int j = 0; j < tabuleiro.getColunas(); j++) {
                tabuPeca[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j);
            }
        }
        return tabuPeca;
    }

    private void colocarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
        tabuleiro.lugarDaPeca(peca, new XadrezPosicao(coluna, linha).paraPosicionar());
    }

    private void configuracaoInicial() {
        colocarNovaPeca('b', 6, new Rook(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('e', 8, new King(tabuleiro, Cor.PRETO));
        colocarNovaPeca('e', 1, new King(tabuleiro, Cor.BRANCO));
    }
}
