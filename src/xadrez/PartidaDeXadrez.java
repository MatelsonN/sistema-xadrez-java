package xadrez;

import jogodetabuleiro.Peca;
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
        PecaDeXadrez[][] tabuPeca = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        for (int i = 0; i < tabuleiro.getLinhas(); i++) {
            for (int j = 0; j < tabuleiro.getColunas(); j++) {
                tabuPeca[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j);
            }
        }
        return tabuPeca;
    }

    public PecaDeXadrez movimentoDaPeca(XadrezPosicao posicaoOrigem, XadrezPosicao posicaoDestino) {
        Posicao origem = posicaoOrigem.paraPosicionar();
        Posicao destino = posicaoDestino.paraPosicionar();
        validarPosicaoOrigem(origem);
        Peca pecaCapturada = fazerMovimento(origem, destino);
        return (PecaDeXadrez)pecaCapturada;
    }

    private Peca fazerMovimento(Posicao origem, Posicao destino) {
        Peca p = tabuleiro.removerPeca(origem);
        Peca pecaCapturada = tabuleiro.removerPeca(destino);
        tabuleiro.lugarDaPeca(p, destino);
        return pecaCapturada;
    }

    private void validarPosicaoOrigem(Posicao posicao) {
        if (!tabuleiro.temUmaPeca(posicao)) {
            throw new XadrezException("Não há nenhuma peça na posição de origem");
        }
        if (!tabuleiro.peca(posicao).existePossivelMovimento()) {
            throw new XadrezException("Não existe movimentos possíveis para a peça escolhida");
        }
    }

    private void colocarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
        tabuleiro.lugarDaPeca(peca, new XadrezPosicao(coluna, linha).paraPosicionar());
    }

    private void configuracaoInicial() {
        colocarNovaPeca('c', 1, new Rook(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('c', 2, new Rook(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('d', 2, new Rook(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('e', 2, new Rook(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('e', 1, new Rook(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('d', 1, new King(tabuleiro, Cor.BRANCO));

        colocarNovaPeca('c', 7, new Rook(tabuleiro, Cor.PRETO));
        colocarNovaPeca('c', 8, new Rook(tabuleiro, Cor.PRETO));
        colocarNovaPeca('d', 7, new Rook(tabuleiro, Cor.PRETO));
        colocarNovaPeca('e', 7, new Rook(tabuleiro, Cor.PRETO));
        colocarNovaPeca('e', 8, new Rook(tabuleiro, Cor.PRETO));
        colocarNovaPeca('d', 8, new King(tabuleiro, Cor.PRETO));
    }
}
