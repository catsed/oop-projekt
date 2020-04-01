package tegelased;

import klassid.KlassMaag;
import klassid.KlassSodalane;
import klassid.KlassVibukutt;
import rassid.RassHaldjas;
import rassid.RassInimene;
import rassid.RassOrk;
import rassid.RassPakapikk;

abstract public class Tegelane {
    private String klass;
    private String rass;

    public Tegelane(String klass, String rass) {
        this.klass = klass;
        this.rass = rass;
    }

    public String getKlass() {
        return klass;
    }

    public String getRass() {
        return rass;
    }

    private void looTegelane(){
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
        if(klass.equals("Maag")){
            KlassMaag maag = new KlassMaag(klass);
            System.out.println(maag.toString());
        }
        if(klass.equals("Sõdalane")){
            KlassSodalane sodalane = new KlassSodalane(klass);
            System.out.println(sodalane.toString());
        }
        if(klass.equals("Vibukütt")){
            KlassVibukutt vibukutt = new KlassVibukutt(klass);
            System.out.println(vibukutt.toString());
        }
    }

}
