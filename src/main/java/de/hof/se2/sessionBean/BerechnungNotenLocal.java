/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hof.se2.sessionBean;

import javax.ejb.Local;

/**
 *
 * @author markus
 */
@Local
public interface BerechnungNotenLocal {

    long getEndnote(int matrikelNr);
    long getNoteGrundstudium(int matrikelNr);
    
}
