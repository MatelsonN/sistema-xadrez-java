package xadrez;

import jogodetabuleiro.Peca;
import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;

public abstract class PecaDeXadrez extends Peca {

    private Cor cor;

    public PecaDeXadrez(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro);
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    }

    public XadrezPosicao getXadrezPosicao() {
        return XadrezPosicao.daPosicao(posicao);
    }

    protected boolean existePecaDoOponente(Posicao posicao) {
        PecaDeXadrez pec = (PecaDeXadrez)getTabuleiro().peca(posicao);
        return pec != null && pec.getCor() != cor;
    }

}
