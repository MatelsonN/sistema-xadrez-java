package xadrez.peca;

import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class King extends PecaDeXadrez {

    public King(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean podeMover(Posicao posicao) {
        PecaDeXadrez pec = (PecaDeXadrez)getTabuleiro().peca(posicao);
        return pec == null || pec.getCor() != getCor();
    }
    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao pos = new Posicao(0, 0);

        // movimento para cima
        pos.definirValores(posicao.getLinha() -1 , posicao.getColuna());
        if (getTabuleiro().posicaoExiste(pos) && podeMover(pos)) {
            matriz[pos.getLinha()][pos.getColuna()] = true;
        }

        // movimento para baixo
        pos.definirValores(posicao.getLinha() + 1, posicao.getColuna());
        if (getTabuleiro().posicaoExiste(pos) && podeMover(pos)) {
            matriz[pos.getLinha()][pos.getColuna()] = true;
        }

        // movimento para esquerda
        pos.definirValores(posicao.getLinha(), posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExiste(pos) && podeMover(pos)) {
            matriz[pos.getLinha()][pos.getColuna()] = true;
        }

        // movimento para direita
        pos.definirValores(posicao.getLinha(), posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExiste(pos) && podeMover(pos)) {
            matriz[pos.getLinha()][pos.getColuna()] = true;
        }

        // movimento para noroeste
        pos.definirValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExiste(pos) && podeMover(pos)) {
            matriz[pos.getLinha()][pos.getColuna()] = true;
        }

        // movimento para nordeste
        pos.definirValores(posicao.getLinha() - 1, posicao.getColuna() + 1 );
        if (getTabuleiro().posicaoExiste(pos) && podeMover(pos)) {
            matriz[pos.getLinha()][pos.getColuna()] = true;
        }

        // movimento para o sudoeste
        pos.definirValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExiste(pos) && podeMover(pos)) {
            matriz[pos.getLinha()][pos.getColuna()] = true;
        }

        // movimento para o sudeste
        pos.definirValores(posicao.getLinha() + 1, posicao.getColuna() + 1 );
        if (getTabuleiro().posicaoExiste(pos) && podeMover(pos)) {
            matriz[pos.getLinha()][pos.getColuna()] = true;
        }

        return matriz;
    }
}
