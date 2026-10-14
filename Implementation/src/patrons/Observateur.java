package patrons;

import modele.Annonce;

// Patron Observateur : interface implementee par les utilisateurs.

public interface Observateur {
    void update(Annonce annonce, String details);
}
