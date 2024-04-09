package xadrez.peca;

import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Knight extends PecaDeXadrez {

    public Knight(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString() {
        return "N";
    }

    private boolean podeMover(Posicao posicao) {
        PecaDeXadrez pec = (PecaDeXadrez)getTabuleiro().peca(posicao);
        return pec == null || pec.getCor() != getCor();
    }
    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao pos = new Posicao(0, 0);


        pos.definirValores(posicao.getLinha() - 1 , posicao.getColuna() - 2);
        if (getTabuleiro().posicaoExiste(pos) && podeMover(pos)) {
            matriz[pos.getLinha()][pos.getColuna()] = true;
        }


        pos.definirValores(posicao.getLinha() - 2, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExiste(pos) && podeMover(pos)) {
            matriz[pos.getLinha()][pos.getColuna()] = true;
        }


        pos.definirValores(posicao.getLinha() - 2, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExiste(pos) && podeMover(pos)) {
            matriz[pos.getLinha()][pos.getColuna()] = true;
        }


        pos.definirValores(posicao.getLinha() - 1, posicao.getColuna() + 2);
        if (getTabuleiro().posicaoExiste(pos) && podeMover(pos)) {
            matriz[pos.getLinha()][pos.getColuna()] = true;
        }


        pos.definirValores(posicao.getLinha() + 1, posicao.getColuna() + 2);
        if (getTabuleiro().posicaoExiste(pos) && podeMover(pos)) {
            matriz[pos.getLinha()][pos.getColuna()] = true;
        }


        pos.definirValores(posicao.getLinha() + 2, posicao.getColuna() + 1 );
        if (getTabuleiro().posicaoExiste(pos) && podeMover(pos)) {
            matriz[pos.getLinha()][pos.getColuna()] = true;
        }


        pos.definirValores(posicao.getLinha() + 2, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExiste(pos) && podeMover(pos)) {
            matriz[pos.getLinha()][pos.getColuna()] = true;
        }


        pos.definirValores(posicao.getLinha() + 1, posicao.getColuna() - 2 );
        if (getTabuleiro().posicaoExiste(pos) && podeMover(pos)) {
            matriz[pos.getLinha()][pos.getColuna()] = true;
        }

        return matriz;
    }
}