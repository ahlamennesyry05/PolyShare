package patrons;

import java.util.ArrayList;
import java.util.List;

import modele.Annonce;

// Patron Observateur : le Sujet gere la liste des observateurs et les notifie.
public abstract class Sujet {

    protected List<Observateur> observateurs = new ArrayList<>();

    public void enregistrerObservateur(Observateur o) {
        if (!observateurs.contains(o)) {
            observateurs.add(o);
        }
    }

    public void supprimerObservateur(Observateur o) {
        observateurs.remove(o);
    }

    public void notifierObservateurs(Annonce annonce, String details) {
        for (Observateur o : observateurs) {
            o.update(annonce, details);
        }
    }
}
