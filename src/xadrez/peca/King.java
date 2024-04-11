package xadrez.peca;

import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;

public class King extends PecaDeXadrez {

    private PartidaDeXadrez partidaDeXadrez;

    public King(Tabuleiro tabuleiro, Cor cor, PartidaDeXadrez partidaDeXadrez) {
        super(tabuleiro, cor);
        this.partidaDeXadrez = partidaDeXadrez;
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean podeMover(Posicao posicao) {
        PecaDeXadrez pec = (PecaDeXadrez)getTabuleiro().peca(posicao);
        return pec == null || pec.getCor() != getCor();
    }

    private boolean testandoMovimentoRook(Posicao posicao) {
        PecaDeXadrez pec = (PecaDeXadrez)getTabuleiro().peca(posicao);
        return pec != null && pec instanceof Rook && pec.getCor() == getCor() && pec.getContarMovimento() == 0;
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

        // movimento especial da torre e do rei
        if (getContarMovimento() == 0 && !partidaDeXadrez.getCheck()) {
            // movimento especial do lado direito
            Posicao posicaoTorre1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
            if (testandoMovimentoRook(posicaoTorre1)) {
                Posicao posicao1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                Posicao posicao2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
                if (getTabuleiro().peca(posicao1) == null && getTabuleiro().peca(posicao2) == null) {
                    matriz[posicao.getLinha()][posicao.getColuna() + 2] = true;
                }
            }

            // movimento especial do lado esquerdo
            Posicao posicaoTorre2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
            if (testandoMovimentoRook(posicaoTorre2)) {
                Posicao posicao1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                Posicao posicao2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
                Posicao posicao3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
                if (getTabuleiro().peca(posicao1) == null &&
                        getTabuleiro().peca(posicao2) == null && getTabuleiro().peca(posicao3) == null) {
                    matriz[posicao.getLinha()][posicao.getColuna() - 2] = true;
                }
            }
        }

        return matriz;
    }
}
