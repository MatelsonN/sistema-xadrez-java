package jogodetabuleiro;

public abstract class Peca {

    protected Posicao posicao;
    private Tabuleiro tabuleiro;

    public Peca(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
        posicao = null;
    }

    protected Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public abstract boolean[][] possiveisMovimentos();

    public boolean possivelMovimento(Posicao posicao) {
        return possiveisMovimentos()[posicao.getLinha()][posicao.getColuna()];
    }

    public boolean existePossivelMovimento() {
        boolean[][] matriz = possiveisMovimentos();
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                if (matriz[i][j]) {
                    return true;
              }
            }
        }
        return false;
    }
}
