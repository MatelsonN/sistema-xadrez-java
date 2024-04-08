package xadrez.peca;

import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Pawn extends PecaDeXadrez {

    public Pawn(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao pos = new Posicao(0, 0);

        if (getCor() == Cor.BRANCO) {
            pos.definirValores(posicao.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().posicaoExiste(pos) && !getTabuleiro().temUmaPeca(pos)) {
                matriz[pos.getLinha()][pos.getColuna()] = true;
            }
            pos.definirValores(posicao.getLinha() - 2, posicao.getColuna());
            Posicao pos2 = new Posicao(pos.getLinha() -1, posicao.getColuna());
            if (getTabuleiro().posicaoExiste(pos) &&
                    !getTabuleiro().temUmaPeca(pos) &&
                    getTabuleiro().posicaoExiste(pos2) &&
                    !getTabuleiro().temUmaPeca(pos2) && getContarMovimento() == 0) {
                matriz[pos.getLinha()][pos.getColuna()] = true;
            }
            pos.definirValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
            if (getTabuleiro().posicaoExiste(pos) && existePecaDoOponente(pos)) {
                matriz[pos.getLinha()][pos.getColuna()] = true;
            }
            pos.definirValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
            if (getTabuleiro().posicaoExiste(pos) && existePecaDoOponente(pos)) {
                matriz[pos.getLinha()][pos.getColuna()] = true;
            }
        }
        else {
            pos.definirValores(posicao.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().posicaoExiste(pos) && !getTabuleiro().temUmaPeca(pos)) {
                matriz[pos.getLinha()][pos.getColuna()] = true;
            }
            pos.definirValores(posicao.getLinha() + 2, posicao.getColuna());
            Posicao pos2 = new Posicao(pos.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().posicaoExiste(pos) &&
                    !getTabuleiro().temUmaPeca(pos) &&
                    getTabuleiro().posicaoExiste(pos2) &&
                    !getTabuleiro().temUmaPeca(pos2) && getContarMovimento() == 0) {
                matriz[pos.getLinha()][pos.getColuna()] = true;
            }
            pos.definirValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
            if (getTabuleiro().posicaoExiste(pos) && existePecaDoOponente(pos)) {
                matriz[pos.getLinha()][pos.getColuna()] = true;
            }
            pos.definirValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
            if (getTabuleiro().posicaoExiste(pos) && existePecaDoOponente(pos)) {
                matriz[pos.getLinha()][pos.getColuna()] = true;
            }
        }
        return matriz;
    }

    @Override
    public String toString() {
        return "P";
    }
}
