/*
* Peaklass
 */

import rassid.RassHaldjas;
import rassid.RassInimene;
import rassid.RassOrk;
import rassid.RassPakapikk;

import java.util.Scanner;

public class Programm {
    public static void main(String[] args) {
        System.out.println("Vali rass. Haldjas/Inimene/Ork/Päkapikk");
        Scanner rassIn = new Scanner(System.in);
        String rass = rassIn.nextLine();
        if(rass.equals("Haldjas")){
            RassHaldjas haldjas = new RassHaldjas(rass);
            System.out.println(haldjas.toString());
        }
        if(rass.equals("Ork")){
            RassOrk ork = new RassOrk(rass);
            System.out.println(ork.toString());
        }
        if(rass.equals("Inimene")){
            RassInimene inimene = new RassInimene(rass);
            System.out.println(inimene.toString());
        }
        if(rass.equals("Päkapikk")){
            RassPakapikk pakapikk = new RassPakapikk(rass);
            System.out.println(pakapikk.toString());
        }
    }
}
