package aplicacao;

import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.XadrezPosicao;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UI {

    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    // https://stackoverflow.com/questions/2979383/java-clear-the-console
    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static XadrezPosicao lerPosicaoXadrez(Scanner leitura) {
        try {
            String caracter = leitura.nextLine();
            char coluna = caracter.charAt(0);
            int linha = Integer.parseInt(caracter.substring(1));
            return new XadrezPosicao(coluna, linha);
        }
        catch (RuntimeException e) {
            throw new InputMismatchException("Erro instanciando a posição. Valores válidos são de a1 até h8");
        }
    }

    public static void imprimirPartida(PartidaDeXadrez partidaDeXadrez, List<PecaDeXadrez> capturada) {
        impressaoTabuleiro(partidaDeXadrez.pegarPecas());
        System.out.println();
        imprimirPecasCapturadas(capturada);
        System.out.println();
        System.out.println("Turno : " + partidaDeXadrez.getTurno());
        if (!partidaDeXadrez.getCheckMate()) {
            System.out.println("Esperando jogador: " + partidaDeXadrez.getJogadorAtual());
            if (partidaDeXadrez.getCheck()) {
                System.out.println("CHECK!");
            }
        }
        else {
            System.out.println("CHECKMATE!");
            System.out.println("Vencedor: " + partidaDeXadrez.getJogadorAtual());
        }
    }

    public static void impressaoTabuleiro(PecaDeXadrez[][] pecas) {
        for (int i = 0; i < pecas.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j= 0; j < pecas.length; j++){
                impressaoPeca(pecas[i][j], false);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    public static void impressaoTabuleiro(PecaDeXadrez[][] pecas, boolean[][] possiveisMovimentos) {
        for (int i = 0; i < pecas.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j= 0; j < pecas.length; j++){
                impressaoPeca(pecas[i][j], possiveisMovimentos[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    private static void impressaoPeca(PecaDeXadrez peca, boolean fundo) {
        if (fundo) {
            System.out.print(ANSI_BLUE_BACKGROUND);
        }
        if (peca == null) {
            System.out.print("-" + ANSI_RESET);
        }
        else {
            if (peca.getCor() == Cor.BRANCO) {
                System.out.print(ANSI_WHITE + peca + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }

    private static void imprimirPecasCapturadas(List<PecaDeXadrez> capturada) {
        List<PecaDeXadrez> branca = capturada.stream().filter(x -> x.getCor() == Cor.BRANCO).collect(Collectors.toList());
        List<PecaDeXadrez> preta = capturada.stream().filter(x -> x.getCor() == Cor.PRETO).collect(Collectors.toList());
        System.out.println("Peças capturadas:");
        System.out.print("Branca: ");
        System.out.print(ANSI_WHITE);
        System.out.println(Arrays.toString(branca.toArray()));
        System.out.print(ANSI_RESET);
        System.out.print("Preta: ");
        System.out.print(ANSI_YELLOW);
        System.out.println(Arrays.toString(preta.toArray()));
        System.out.print(ANSI_RESET);

    }
}
