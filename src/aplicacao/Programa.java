package aplicacao;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.XadrezException;
import xadrez.XadrezPosicao;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Programa {

    public static void main(String[] args) {


        Scanner leitura = new Scanner(System.in);
        PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();

        while (true) {
            try {
                UI.limparTela();
                UI.imprimirPartida(partidaDeXadrez);
                System.out.println();
                System.out.print("Origem: ");
                XadrezPosicao origem = UI.lerPosicaoXadrez(leitura);

                boolean[][] possiveisMovimentos = partidaDeXadrez.possiveisMovimentos(origem);
                UI.limparTela();
                UI.impressaoTabuleiro(partidaDeXadrez.pegarPecas(), possiveisMovimentos);
                System.out.println();
                System.out.print("Destino: ");
                XadrezPosicao destino = UI.lerPosicaoXadrez(leitura);

                PecaDeXadrez pecaCapturada = partidaDeXadrez.movimentoDaPeca(origem, destino);
            }
            catch (XadrezException e) {
                System.out.println(e.getMessage());
                leitura.nextLine();
            }
            catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                leitura.nextLine();
            }
        }

    }
}