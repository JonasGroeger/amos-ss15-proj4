/**
 * Personalfragebogen 2.0. Revolutionize form data entry for taxation and
 * other purposes.
 * Copyright (C) 2015 Attila Bujaki, Werner Sembach, Jonas Gröger, Oswaldo
 *     Bejarano, Ardhi Sutadi, Nikitha Mohan, Benedikt Rauh
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.fau.amos4.util;

import java.util.HashMap;

/**
 * Created by Yao Bochao on 07/06/2015.
 * This class handles the data output according to the LODAS interface.
 */
public class LODAS {

    /*
    [Allgemein]
    Ziel=LODAS
    Version_SST (optional, default: current version)
    Version_DB  (optional, default: current version)
    BeraterNr   (mandatory)
    MandatenNr  (mandatory)
    Kommentarzeichen (optional, default: * )
    Feldtrennzeichen (optional, default: ; )
    Zahlenkomma      (optional, default: ',' also possible '.')
    Datumsformat     (optional, default: TT.MM.JJJJ)
    StammdatenGueltigAb (optional, TT.MM.JJJJ default: Abrechnungsstand +1 als GültigAb-Monat)
     */
    private final HashMap<String,String> Allgemein;

    public LODAS() {
        Allgemein = new HashMap<String,String>();
        Allgemein.put("Ziel","LODAS");
        Allgemein.put("Version_SST","1.0");
        Allgemein.put("Version_DB","9.85");
        Allgemein.put("BeraterNr","1111111");
        Allgemein.put("MandatenNr","22222"); //actually: 1-5 digits
        Allgemein.put("Kommentarzeichen","*");
        Allgemein.put("Feldtrennzeichen",";");
        Allgemein.put("Zahlenkomma",",");
        Allgemein.put("Datumsformat","TT/MM/JJJJ");
        Allgemein.put("StammdatenGueltigAb","01.01.2015"); //Necessary?
    }



}
