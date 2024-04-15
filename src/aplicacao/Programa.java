package aplicacao;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.XadrezException;
import xadrez.XadrezPosicao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Programa {

    public static void main(String[] args) {


        Scanner leitura = new Scanner(System.in);
        PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
        List<PecaDeXadrez> capturada = new ArrayList<>();

        while (!partidaDeXadrez.getCheckMate()) {
            try {
                UI.limparTela();
                UI.imprimirPartida(partidaDeXadrez, capturada);
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

                if (pecaCapturada != null) {
                    capturada.add(pecaCapturada);
                }

                if (partidaDeXadrez.getPromocao() != null) {
                    System.out.print("Quer promover para qual peça (B/N/R/Q): ");
                    String tipo = leitura.nextLine().toUpperCase();
                    while (!tipo.equals("B") && !tipo.equals("N") && !tipo.equals("R") && !tipo.equals("Q")) {
                        partidaDeXadrez.trocarPromocaoDaPeca(tipo);
                        System.out.print("Valor inválido! Quer promover para qual peça (B/N/R/Q): ");
                        tipo = leitura.nextLine().toUpperCase();
                    }
                    partidaDeXadrez.trocarPromocaoDaPeca(tipo);
                }
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
        UI.limparTela();
        UI.imprimirPartida(partidaDeXadrez, capturada);

    }
}