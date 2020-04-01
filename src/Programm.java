/*
* Peaklass
 */

import rassid.RassHaldjas;

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
    }
}
