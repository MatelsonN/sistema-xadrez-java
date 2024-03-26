package aplicacao;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.XadrezPosicao;

import java.util.Scanner;

public class Programa {

    public static void main(String[] args) {


        Scanner leitura = new Scanner(System.in);
        PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();

        while (true) {
            UI.impressaoTabuleiro(partidaDeXadrez.pegarPecas());
            System.out.println();
            System.out.print("Origem: ");
            XadrezPosicao origem = UI.lerPosicaoXadrez(leitura);

            System.out.println();
            System.out.print("Destino: ");
            XadrezPosicao destino = UI.lerPosicaoXadrez(leitura);

            PecaDeXadrez pecaCapturada = partidaDeXadrez.movimentoDaPeca(origem, destino);
        }


    }
}