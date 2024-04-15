package xadrez;

import jogodetabuleiro.Peca;
import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.peca.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PartidaDeXadrez {

    private int turno;
    private Cor jogadorAtual;
    private Tabuleiro tabuleiro;
    private boolean check;
    private boolean checkMate;
    private PecaDeXadrez vulnerabilidadeEnPassant;

    private PecaDeXadrez promocao;

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

    public PecaDeXadrez getVulnerabilidadeEnPassant() {
        return vulnerabilidadeEnPassant;
    }

    public PecaDeXadrez getPromocao() {
        return promocao;
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

        PecaDeXadrez pecaMovida = (PecaDeXadrez)tabuleiro.peca(destino);

        // movimento especial promoção
        promocao = null;
        if (pecaMovida instanceof Pawn) {
            if ((pecaMovida.getCor() == Cor.BRANCO && destino.getLinha() == 0) ||
                    (pecaMovida.getCor() == Cor.PRETO && destino.getLinha() == 7)) {
                promocao = (PecaDeXadrez)tabuleiro.peca(destino);
                promocao = trocarPromocaoDaPeca("Q");
            }
        }

        check = (testandoCheck(oponente(jogadorAtual))) ? true : false;

        if (testandoCkeckMate(oponente(jogadorAtual))) {
            checkMate = true;
        }
        else {
            proximoTurno();

        }

        // movimento especial en passant
        if (pecaMovida instanceof Pawn &&
                (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)) {
            vulnerabilidadeEnPassant = pecaMovida;
        }
        else {
            vulnerabilidadeEnPassant = null;
        }

        return (PecaDeXadrez)pecaCapturada;
    }

    public PecaDeXadrez trocarPromocaoDaPeca(String tipo) {
        if (promocao == null) {
            throw new IllegalStateException("Não tem peça para ser promovida");
        }
        if (!tipo.equals("B") && !tipo.equals("N") && !tipo.equals("R") && !tipo.equals("Q")) {
            return promocao;
        }

        Posicao pos = promocao.getXadrezPosicao().paraPosicionar();
        Peca p = tabuleiro.removerPeca(pos);
        pecasnoTabuleiro.remove(p);

        PecaDeXadrez novaPeca = novaPeca(tipo, promocao.getCor());
        tabuleiro.lugarDaPeca(novaPeca, pos);
        pecasnoTabuleiro.add(novaPeca);

        return novaPeca;
    }

    private PecaDeXadrez novaPeca(String tipo, Cor cor) {
        if (tipo.equals("B")) return new Bishop(tabuleiro, cor);
        if (tipo.equals("N")) return new Knight(tabuleiro, cor);
        if (tipo.equals("Q")) return new Queen(tabuleiro, cor);
        return new Rook(tabuleiro, cor);
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

        // movimento especial do lado direito
        if (pec instanceof King && destino.getColuna() == origem.getColuna() + 2) {
            Posicao origemDaTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao destinoDaTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            PecaDeXadrez rook = (PecaDeXadrez)tabuleiro.removerPeca(origemDaTorre);
            tabuleiro.lugarDaPeca(rook, destinoDaTorre);
            rook.aumentarContagemDeMovimentos();
        }

        // movimento especial do lado esquerdo
        if (pec instanceof King && destino.getColuna() == origem.getColuna() -2) {
            Posicao origemDaTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao destinoDaTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            PecaDeXadrez rook = (PecaDeXadrez)tabuleiro.removerPeca(origemDaTorre);
            tabuleiro.lugarDaPeca(rook, destinoDaTorre);
            rook.aumentarContagemDeMovimentos();
        }

        // movimento especial en passant
        if (pec instanceof Pawn) {
            if (origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
                Posicao pawnPosicao;
                if (pec.getCor() == Cor.BRANCO) {
                    pawnPosicao = new Posicao(destino.getLinha() + 1, destino.getColuna());
                }
                else {
                    pawnPosicao = new Posicao(destino.getLinha() - 1, destino.getColuna());
                }
                pecaCapturada = tabuleiro.removerPeca(pawnPosicao);
                pecasCapturadas.add(pecaCapturada);
                pecasnoTabuleiro.remove(pecaCapturada);
            }
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

        // desfazendo movimento especial do lado direito
        if (pec instanceof King && destino.getColuna() == origem.getColuna() + 2) {
            Posicao origemDaTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao destinoDaTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            PecaDeXadrez rook = (PecaDeXadrez)tabuleiro.removerPeca(destinoDaTorre);
            tabuleiro.lugarDaPeca(rook, origemDaTorre);
            rook.diminuirContagemDeMovimentos();
        }

        // desfazendo movimento especial do lado esquerdo
        if (pec instanceof King && destino.getColuna() == origem.getColuna() -2) {
            Posicao origemDaTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao destinoDaTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            PecaDeXadrez rook = (PecaDeXadrez)tabuleiro.removerPeca(destinoDaTorre);
            tabuleiro.lugarDaPeca(rook, origemDaTorre);
            rook.diminuirContagemDeMovimentos();
        }

        // movimento especial en passant
        if (pec instanceof Pawn) {
            if (origem.getColuna() != destino.getColuna() && pecaCapturada == vulnerabilidadeEnPassant) {
                PecaDeXadrez pawn = (PecaDeXadrez)tabuleiro.removerPeca(destino);
                Posicao pawnPosicao;
                if (pec.getCor() == Cor.BRANCO) {
                    pawnPosicao = new Posicao(3, destino.getColuna());
                }
                else {
                    pawnPosicao = new Posicao(4, destino.getColuna());
                }
                tabuleiro.lugarDaPeca(pawn, pawnPosicao);

            }
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
        colocarNovaPeca('b', 1, new Knight(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('c', 1, new Bishop(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('d', 1, new Queen(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('e', 1, new King(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeca('f', 1, new Bishop(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('g', 1, new Knight(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('h', 1, new Rook(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('a', 2, new Pawn(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeca('b', 2, new Pawn(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeca('c', 2, new Pawn(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeca('d', 2, new Pawn(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeca('e', 2, new Pawn(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeca('f', 2, new Pawn(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeca('g', 2, new Pawn(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeca('h', 2, new Pawn(tabuleiro, Cor.BRANCO, this));


        colocarNovaPeca('a', 8, new Rook(tabuleiro, Cor.PRETO));
        colocarNovaPeca('b', 8, new Knight(tabuleiro, Cor.PRETO));
        colocarNovaPeca('c', 8, new Bishop(tabuleiro, Cor.PRETO));
        colocarNovaPeca('d', 8, new Queen(tabuleiro, Cor.PRETO));
        colocarNovaPeca('e', 8, new King(tabuleiro, Cor.PRETO, this));
        colocarNovaPeca('f', 8, new Bishop(tabuleiro, Cor.PRETO));
        colocarNovaPeca('g', 8, new Knight(tabuleiro, Cor.PRETO));
        colocarNovaPeca('h', 8, new Rook(tabuleiro, Cor.PRETO));
        colocarNovaPeca('a', 7, new Pawn(tabuleiro, Cor.PRETO, this));
        colocarNovaPeca('b', 7, new Pawn(tabuleiro, Cor.PRETO, this));
        colocarNovaPeca('c', 7, new Pawn(tabuleiro, Cor.PRETO, this));
        colocarNovaPeca('d', 7, new Pawn(tabuleiro, Cor.PRETO, this));
        colocarNovaPeca('e', 7, new Pawn(tabuleiro, Cor.PRETO, this));
        colocarNovaPeca('f', 7, new Pawn(tabuleiro, Cor.PRETO, this));
        colocarNovaPeca('g', 7, new Pawn(tabuleiro, Cor.PRETO, this));
        colocarNovaPeca('h', 7, new Pawn(tabuleiro, Cor.PRETO, this));
    }
}
