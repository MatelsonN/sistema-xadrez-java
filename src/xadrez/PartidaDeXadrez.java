package xadrez;

import jogodetabuleiro.Peca;
import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.peca.King;
import xadrez.peca.Pawn;
import xadrez.peca.Rook;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PartidaDeXadrez {

    private int turno;
    private Cor jogadorAtual;
    private Tabuleiro tabuleiro;
    private boolean check;
    private boolean checkMate;

    private List<Peca> pecasnoTabuleiro = new ArrayList<>();
    private List<Peca> pecasCapturadas = new ArrayList<>();

    public PartidaDeXadrez() {
        tabuleiro = new Tabuleiro(8, 8);
        turno = 1;
        jogadorAtual = Cor.BRANCO;
        configuracaoInicial();
    }

    public int getTurno() {
        return turno;
    }

    public Cor getJogadorAtual() {
        return jogadorAtual;
    }

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckMate() {
        return checkMate;
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

    public boolean[][] possiveisMovimentos(XadrezPosicao posicaoInicial) {
        Posicao posicao = posicaoInicial.paraPosicionar();
        validarPosicaoOrigem(posicao);
        return tabuleiro.peca(posicao).possiveisMovimentos();
    }

    public PecaDeXadrez movimentoDaPeca(XadrezPosicao posicaoOrigem, XadrezPosicao posicaoDestino) {
        Posicao origem = posicaoOrigem.paraPosicionar();
        Posicao destino = posicaoDestino.paraPosicionar();
        validarPosicaoOrigem(origem);
        validarPosicaoDeDestino(origem, destino);
        Peca pecaCapturada = fazerMovimento(origem, destino);

        if(testandoCheck(jogadorAtual)) {
            desfazerMovimento(origem, destino, pecaCapturada);
            throw new XadrezException("você não pode se colocar em cheque.");
        }

        check = (testandoCheck(oponente(jogadorAtual))) ? true : false;

        if (testandoCheck(oponente(jogadorAtual))) {
            checkMate = true;
        }
        else {
            proximoTurno();

        }

        return (PecaDeXadrez)pecaCapturada;
    }

    private Peca fazerMovimento(Posicao origem, Posicao destino) {
        PecaDeXadrez pec = (PecaDeXadrez)tabuleiro.removerPeca(origem);
        pec.aumentarContagemDeMovimentos();
        Peca pecaCapturada = tabuleiro.removerPeca(destino);
        tabuleiro.lugarDaPeca(pec, destino);

        if (pecaCapturada != null) {
            pecasnoTabuleiro.remove(pecaCapturada);
            pecasCapturadas.add(pecaCapturada);
        }
        return pecaCapturada;
    }

    private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
        PecaDeXadrez pec = (PecaDeXadrez)tabuleiro.removerPeca(destino);
        pec.diminuirContagemDeMovimentos();
        tabuleiro.lugarDaPeca(pec, origem);

        if (pecaCapturada != null) {
            tabuleiro.lugarDaPeca(pecaCapturada, destino);
            pecasCapturadas.remove(pecaCapturada);
            pecasnoTabuleiro.add(pecaCapturada);
        }
    }

    private void validarPosicaoOrigem(Posicao posicao) {
        if (!tabuleiro.temUmaPeca(posicao)) {
            throw new XadrezException("Não há nenhuma peça na posição de origem");
        }
        if (jogadorAtual != ((PecaDeXadrez)tabuleiro.peca(posicao)).getCor()) {
            throw new XadrezException("A peça escolhida não é sua");

        }
        if (!tabuleiro.peca(posicao).existePossivelMovimento()) {
            throw new XadrezException("Não existe movimentos possíveis para a peça escolhida");
        }
    }

    private void validarPosicaoDeDestino(Posicao origem, Posicao destino) {
        if (!tabuleiro.peca(origem).possivelMovimento(destino)) {
            throw new XadrezException("A peça escolhida não pode se mover para a posição de destino");
        }
    }

    private void proximoTurno() {
        turno ++;
        jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
    }

    private Cor oponente(Cor cor) {
        return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
    }

    private PecaDeXadrez king(Cor cor) {
        List<Peca> lista = pecasnoTabuleiro.stream()
                .filter(x -> ((PecaDeXadrez)x).getCor() == cor).collect(Collectors.toList());
        for (Peca pec : lista) {
            if (pec instanceof King) {
                return (PecaDeXadrez)pec;
            }
        }
        throw new IllegalStateException("Não existe rei na cor " + cor + " no tabuleiro");
    }

    private boolean testandoCheck(Cor cor) {
        Posicao posicaoDoRei = king(cor).getXadrezPosicao().paraPosicionar();
        List<Peca> pecasDoOponente = pecasnoTabuleiro.stream()
                .filter(x -> ((PecaDeXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
        for (Peca pec : pecasDoOponente) {
            boolean[][] matriz = pec.possiveisMovimentos();
            if (matriz[posicaoDoRei.getLinha()][posicaoDoRei.getColuna()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testandoCkeckMate(Cor cor) {
        if (!testandoCheck(cor)) {
            return false;
        }
        List<Peca> lista = pecasnoTabuleiro.stream()
                .filter(x -> ((PecaDeXadrez)x).getCor() == cor).collect(Collectors.toList());
        for (Peca pec : lista) {
            boolean[][] matriz = pec.possiveisMovimentos();
            for (int i = 0; i < tabuleiro.getLinhas(); i++) {
                for (int j = 0; j < tabuleiro.getColunas(); j++) {
                    if (matriz[i][j]) {
                        Posicao origem = ((PecaDeXadrez)pec).getXadrezPosicao().paraPosicionar();
                        Posicao destino = new Posicao(i, j);
                        Peca pecaCapturada = fazerMovimento(origem, destino);
                        boolean testandoCheck = testandoCheck(cor);
                        desfazerMovimento(origem, destino, pecaCapturada);
                        if (!testandoCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void colocarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
        tabuleiro.lugarDaPeca(peca, new XadrezPosicao(coluna, linha).paraPosicionar());
        pecasnoTabuleiro.add(peca);
    }

    private void configuracaoInicial() {
        colocarNovaPeca('a', 1, new Rook(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('e', 1, new King(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('h', 1, new Rook(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('a', 2, new Pawn(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('b', 2, new Pawn(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('c', 2, new Pawn(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('d', 2, new Pawn(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('e', 2, new Pawn(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('f', 2, new Pawn(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('g', 2, new Pawn(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('h', 2, new Pawn(tabuleiro, Cor.BRANCO));


        colocarNovaPeca('a', 8, new Rook(tabuleiro, Cor.PRETO));
        colocarNovaPeca('e', 8, new King(tabuleiro, Cor.PRETO));
        colocarNovaPeca('h', 8, new Rook(tabuleiro, Cor.PRETO));
        colocarNovaPeca('a', 7, new Pawn(tabuleiro, Cor.PRETO));
        colocarNovaPeca('b', 7, new Pawn(tabuleiro, Cor.PRETO));
        colocarNovaPeca('c', 7, new Pawn(tabuleiro, Cor.PRETO));
        colocarNovaPeca('d', 7, new Pawn(tabuleiro, Cor.PRETO));
        colocarNovaPeca('e', 7, new Pawn(tabuleiro, Cor.PRETO));
        colocarNovaPeca('f', 7, new Pawn(tabuleiro, Cor.PRETO));
        colocarNovaPeca('g', 7, new Pawn(tabuleiro, Cor.PRETO));
        colocarNovaPeca('h', 7, new Pawn(tabuleiro, Cor.PRETO));
    }
}
