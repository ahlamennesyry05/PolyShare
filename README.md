# PolyShare

Plateforme collaborative universitaire permettant aux étudiants et enseignants de partager, prêter et échanger des biens et des services. Elle facilite la mise en relation entre les membres de Polytech Annecy pour répondre à leurs besoins quotidiens. Développé en Java dans le cadre d'un projet de programmation orientée objet (module INFO732).

## Fonctionnalités

- **Comptes** : l'administrateur crée les comptes étudiants et enseignants (pas d'auto-inscription) et peut réinitialiser un mot de passe.
- **Connexion** : vérification de l'email et du mot de passe, puis détermination du rôle.
- **Annonces** : publication de biens (prêt, don…) et de services (tutorat, covoiturage…).
- **Notifications** : à chaque nouvelle annonce, les autres utilisateurs sont prévenus.
- **Demandes** : cycle de vie `EN_ATTENTE → ACCEPTEE / REFUSEE → TERMINEE`.
- **Évaluations** : note de 1 à 5 avec commentaire une fois l'échange terminé, avec mise à jour de la note moyenne.
- **Modération** : l'administrateur peut valider ou supprimer une annonce.

## Patrons de conception

| Patron | Classes | Rôle |
|---|---|---|
| Simple Factory | `AnnonceFactory` | Crée un `Bien` ou un `Service` selon le type demandé, sans que l'appelant connaisse la classe concrète. |
| Observateur | `Sujet`, `Observateur`, `PolyShareSubject` | Notifie les utilisateurs concernés lorsqu'une annonce est publiée. `Utilisateur` implémente `Observateur`. |
| DAO | `Depot` | Centralise le stockage des utilisateurs et des annonces. |
| Contrôleur | `GestionnaireAuthentification` | Orchestre la connexion : recherche dans le dépôt, vérification du mot de passe, rôle. |

## Organisation du code

```
Implementation/src/
├── modele/       Utilisateur, Etudiant, Enseignant, Admin, Annonce, Bien, Service,
│                 Demande, Evaluation, StatutAnnonce, StatutDemande
├── dao/          Depot
├── patrons/      AnnonceFactory, Sujet, Observateur, PolyShareSubject
├── controleur/   GestionnaireAuthentification
└── main/         Main (scénario de démonstration)
```

## Lancer le projet

1. Ouvrir le dossier `Implementation` avec IntelliJ IDEA (Java 17 ou plus).
2. Clic droit sur `src/main/Main.java` → **Run 'Main.main()'**.

### Scénario de démonstration

Le `Main` déroule cinq étapes :

1. L'administrateur crée les comptes.
2. Connexion réussie, mauvais mot de passe, email inconnu.
3. Publication d'un service, d'une demande de service et d'un bien, avec notifications.
4. Demande, acceptation, fin de l'échange et évaluation (avec les cas refusés).
5. Modération : suppression d'une annonce et réinitialisation d'un mot de passe.

## Diagrammes UML

### Cas d'utilisation
<img width="418" height="316" alt="image" src="https://github.com/user-attachments/assets/f4f508dc-ca0d-430e-92ed-b11be9c5b2b7" />

### Diagramme de classes
<img width="382" height="410" alt="image" src="https://github.com/user-attachments/assets/d09519c5-884d-4910-89a4-fec390180844" />

### Séquence : authentification (Contrôleur)
<img width="362" height="326" alt="image" src="https://github.com/user-attachments/assets/e0589b8e-e21e-4a57-b6b4-377ecb057098" />

### Séquence : création d'annonce (Factory)
<img width="491" height="413" alt="image" src="https://github.com/user-attachments/assets/77d92190-113b-4b51-9410-77e76c55da0a" />

### Séquence : notification (Observateur)
<img width="424" height="413" alt="image" src="https://github.com/user-attachments/assets/bd5fc4e2-91c1-4160-b99d-e60e64a3b109" />

## Équipe

Ahlam En-nesyry · Hiba Sebban · Sara Bouarafa · Salma Aittarga
