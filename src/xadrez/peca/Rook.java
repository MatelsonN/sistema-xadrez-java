package xadrez.peca;

import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Rook extends PecaDeXadrez {

    public Rook(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString() {
        return "R";
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao pos = new Posicao(0, 0);

        // movimento para cima
        pos.definirValores(posicao.getLinha() - 1, posicao.getColuna());
        while (getTabuleiro().posicaoExiste(pos) && !getTabuleiro().temUmaPeca(pos)) {
            matriz[pos.getLinha()][pos.getColuna()] = true;
            pos.setLinha(pos.getLinha() - 1);
        }
        if (getTabuleiro().posicaoExiste(pos) && existePecaDoOponente(pos)) {
            matriz[pos.getLinha()][pos.getColuna()] = true;
        }

        // movimento para baixo
        pos.definirValores(posicao.getLinha() + 1, posicao.getColuna());
        while (getTabuleiro().posicaoExiste(pos) && !getTabuleiro().temUmaPeca(pos)) {
            matriz[pos.getLinha()][pos.getColuna()] = true;
            pos.setLinha(pos.getLinha() + 1);
        }
        if (getTabuleiro().posicaoExiste(pos) && getTabuleiro().temUmaPeca(pos)) {
            matriz[pos.getLinha()][pos.getColuna()] = true;
        }

        // movimento para esquerda
        pos.definirValores(posicao.getLinha(), posicao.getColuna() - 1);
        while (getTabuleiro().posicaoExiste(pos) && !getTabuleiro().temUmaPeca(pos)) {
            matriz[pos.getLinha()][pos.getColuna()] = true;
            pos.setColuna(pos.getColuna() - 1);
        }
        if (getTabuleiro().posicaoExiste(pos) && existePecaDoOponente(pos)) {
            matriz[pos.getLinha()][pos.getColuna()] = true;
        }

        // movimento para direita
        pos.definirValores(posicao.getLinha(), posicao.getColuna() + 1);
        while (getTabuleiro().posicaoExiste(pos) && !getTabuleiro().temUmaPeca(pos)) {
            matriz[pos.getLinha()][pos.getColuna()] = true;
            pos.setColuna(pos.getColuna() + 1);
        }
        if (getTabuleiro().posicaoExiste(pos) && getTabuleiro().temUmaPeca(pos)) {
            matriz[pos.getLinha()][pos.getColuna()] = true;
        }

        return matriz;
    }
}
