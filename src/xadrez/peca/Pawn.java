package xadrez.peca;

import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;

public class Pawn extends PecaDeXadrez {

    private PartidaDeXadrez partidaDeXadrez;

    public Pawn(Tabuleiro tabuleiro, Cor cor, PartidaDeXadrez partidaDeXadrez) {
        super(tabuleiro, cor);
        this.partidaDeXadrez = partidaDeXadrez;
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
            Posicao pos2 = new Posicao(posicao.getLinha() -1, posicao.getColuna());
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

            // movimento especial en passant branca
            if (posicao.getLinha() == 3) {
                Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if (getTabuleiro().posicaoExiste(esquerda) && existePecaDoOponente(esquerda) &&
                        getTabuleiro().peca(esquerda) == partidaDeXadrez.getVulnerabilidadeEnPassant()) {
                    matriz[esquerda.getLinha() -1][esquerda.getColuna()] = true;
                }
                Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                if (getTabuleiro().posicaoExiste(direita) && existePecaDoOponente(direita) &&
                        getTabuleiro().peca(direita) == partidaDeXadrez.getVulnerabilidadeEnPassant()) {
                    matriz[direita.getLinha() - 1][direita.getColuna()] = true;
                }
            }
        }
        else {
            pos.definirValores(posicao.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().posicaoExiste(pos) && !getTabuleiro().temUmaPeca(pos)) {
                matriz[pos.getLinha()][pos.getColuna()] = true;
            }
            pos.definirValores(posicao.getLinha() + 2, posicao.getColuna());
            Posicao pos2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
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

            // movimento especial en passant preta
            if (posicao.getLinha() == 4) {
                Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if (getTabuleiro().posicaoExiste(esquerda) && existePecaDoOponente(esquerda) &&
                        getTabuleiro().peca(esquerda) == partidaDeXadrez.getVulnerabilidadeEnPassant()) {
                    matriz[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
                }
                Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                if (getTabuleiro().posicaoExiste(direita) && existePecaDoOponente(direita) &&
                        getTabuleiro().peca(direita) == partidaDeXadrez.getVulnerabilidadeEnPassant()) {
                    matriz[direita.getLinha() + 1][direita.getColuna()] = true;
                }
            }
        }
        return matriz;
    }

    @Override
    public String toString() {
        return "P";
    }
}
