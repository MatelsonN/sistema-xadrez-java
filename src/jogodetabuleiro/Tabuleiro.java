package jogodetabuleiro;

public class Tabuleiro {

    private int linhas;
    private int colunas;
    private Peca[][] pecas;

    public Tabuleiro(int linhas, int colunas) {
        if (linhas < 1 || colunas < 1) {
            throw new TabuleiroException("Erro na criação do tabuleiro: é preciso que tenha pelo menos 1 linha e 1 " +
                    "coluna");
        }
        this.linhas = linhas;
        this.colunas = colunas;
        pecas = new Peca[linhas][colunas];
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public Peca peca(int linha, int coluna) {
        if (!posicaoExiste(linha, coluna)) {
            throw new TabuleiroException("Essa posição não existe no tabuleiro.");
        }
        return pecas[linha][coluna];
    }

    public Peca peca(Posicao posicao) {
        if (!posicaoExiste(posicao)) {
            throw new TabuleiroException("Essa posição não existe no tabuleiro");
        }
        return pecas[posicao.getLinha()][posicao.getColuna()];
    }

    public void lugarDaPeca(Peca peca, Posicao posicao) {
        if (temUmaPeca(posicao)) {
            throw new TabuleiroException("Já existe uma peça nessa posição " + posicao);
        }
        pecas[posicao.getLinha()][posicao.getColuna()] = peca;
        peca.posicao = posicao;
    }

    public Peca removerPeca(Posicao posicao) {
        if (!posicaoExiste(posicao)) {
            throw new TabuleiroException("Essa posição não existe no tabuleiro");
        }
        if (peca(posicao) == null) {
            return null;
        }
        Peca pecaRemovida = peca(posicao);
        pecaRemovida.posicao = null;
        pecas[posicao.getLinha()][posicao.getColuna()] = null;
        return pecaRemovida;
    }

    private boolean posicaoExiste(int linha, int coluna) {
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
    }

    public boolean posicaoExiste(Posicao posicao) {
        return posicaoExiste(posicao.getLinha(), posicao.getColuna());
    }

    public boolean temUmaPeca(Posicao posicao) {
        if (!posicaoExiste(posicao)) {
            throw new TabuleiroException("Já existe uma peça nessa posição " + posicao);
        }
        return peca(posicao) != null;
    }
}
