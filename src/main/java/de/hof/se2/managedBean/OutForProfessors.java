/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hof.se2.managedBean;

import de.hof.se2.test.Professors;
import de.hof.se2.entity.Noten;
import de.hof.se2.entity.Personen;
import de.hof.se2.sessionBean.BerechnungNotenLocal;
import de.hof.se2.sessionBean.StatistikBeanLocal;
import de.hof.se2.test.Statistik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.w3c.dom.Document;

/**
 * Managed Bean mit der die Funktionalität für Dozenten erzeugt wird
 *
 * @author Christoph
 * @version 0.1
 * @since 10.11.2015
 */
@Named(value = "outForProfessors")
@Dependent
public class OutForProfessors implements Serializable {

    @Default
    Document doc;
    @LoggedIn
    UserDaten user;

    //    @Current Document doc;
//    @LoggedIn User user;
    @EJB
    private BerechnungNotenLocal berechnungNoten;

    @EJB
    private StatistikBeanLocal statistikBeanLocal;

    @PersistenceContext
    EntityManager em;

    /**
     * Creates a new instance of OutForProfessors
     */
    public OutForProfessors() {
    }

    /**
     * Klasse um ein Objekt vom Typ Personen zurück zu liefern um in der Ausgabe
     * einige Parameter gleich bei der Hand zu haben
     *
     * @author Christoph
     * @version 0.1
     * @since 10.11.2015
     * @param Dozent_id
     * @return Objekt vom Typ "Personen" zu der jeweiligen PersnenId.
     *
     *
     */
    public Personen getProfessor(int Dozent_id) {
        // Die Personen sind unique, deshalb kann hier ohne Bedenken das erste Element aus der Mange genommen werden, keine schöne Lösung
        // aber derzeit die stressfreiste
        // To do: 
        // - andere Lösung suchen
        List<Personen> person = em.createNamedQuery("Personen.findByIdPersonen", Personen.class).setParameter("idPersonen", Dozent_id).getResultList();
        return person.get(0);
    }

    /**
     *
     * @param dozentID
     * @return
     */
    @Named
    public List<Noten> notenListByProfessor(int dozentID) {

        List<Noten> notenList = em.createNativeQuery("select noten.* from noten, studienfaecher, personen where noten.studienfach_id = studienfaecher.idStudienfach and personen.idPersonen = studienfaecher.dozent_id and personen.idPersonen = " + dozentID, Noten.class).getResultList();

//        Noten get = notenList.get(0);
        //  List<Noten> liste_noten_professor  = (List<Noten>) em.createNativeQuery("select noten.* from noten, studienfaecher, personen where noten.studienfach_id= studienfaecher.idStudienfach and personen.idPersonen = studienfaecher.dozent_id and personen.idPersonen=" + person.getIdPersonen(), Noten.class).getResultList();
        return notenList;

    }

    /**
     *
     * @param person
     * @return
     */
    public Noten noteFirstProfessor(Personen person) {

        List<Noten> notenList = person.getNotenList();
        Noten element = notenList.get(0);
        return element;
    }

    /**
     * @author max
     * @param idStudienfach
     * @return Arithmetisches Mittel des Studiengangs aus der berechnungNoten
     * Bean
     */
    @Named
    public double getArithmetischesMittel(int idStudienfach) {
        return this.berechnungNoten.getArithmethischesMittel(idStudienfach);
    }

    /**
     * @author max
     * @param idStudienfach
     * @return Varianz des Studiengangs aus der berechnungNoten Bean
     */
    @Named
    public double getVarianz(int idStudienfach) {
        return this.berechnungNoten.getVarianz(idStudienfach);
    }

    /**
     * @author max
     * @param idStudienfach
     * @return Standardabweichung des Studiengangs aus der berechnungNoten Bean
     */
    @Named
    public double getStandardabweichung(int idStudienfach) {
        return this.berechnungNoten.getStandardabweichung(idStudienfach);
    }

    /**
     * @author max
     * @param idStudienfach
     * @return Median des Studiengangs aus der berechnungNoten Bean
     */
    @Named
    public int getMedian(int idStudienfach) {
        return this.berechnungNoten.getMedian(idStudienfach);
    }

    @Named
    public Statistik getStatistik(int idStudienfach) {

        return this.statistikBeanLocal.getStatistik(idStudienfach);
    }

    @Named
    public Personen getPerson(int personId) {
        List<Personen> liste = em.createNativeQuery("select * from personen where idPersonen = " + personId, Personen.class).getResultList();
        return liste.get(0);
    }
}
