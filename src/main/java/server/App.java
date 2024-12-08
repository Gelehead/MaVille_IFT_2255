package server;

import java.time.LocalDateTime;
import java.util.Random;
import backend.Database;
import io.javalin.Javalin;
import Utils.Parser.Impediment;
import Utils.Schedule;
import metrics.*;
import Instances.*;
import java.time.Instant;
import java.time.ZoneId;
import Utils.Parser;
import io.javalin.http.staticfiles.Location;



/**
 * Classe principale de l'application serveur qui gère les endpoints API REST.
 * Cette classe configure et démarre le serveur Javalin et définit tous les endpoints disponibles.
 */
public class App {
    /**
     * Instance de la base de données utilisée par l'application
     */
    private static Database database;

    /**
     * Point d'entrée principal de l'application.
     * Configure et démarre le serveur avec tous les endpoints REST.
     * @param args Arguments de ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        database = new Database();

        Javalin app = Javalin.create(config -> {
            // Configuration CORS existante
            config.plugins.enableCors(cors -> {
                cors.add(corsConfig -> {
                    corsConfig.allowHost("http://127.0.0.1:5500");
                    corsConfig.allowCredentials = true;
                });
            });
            // Configuration des fichiers statiques
            config.staticFiles.add(staticFileConfig -> {
                staticFileConfig.hostedPath = "/";
                staticFileConfig.directory = "/public";
                staticFileConfig.location = Location.CLASSPATH;
            });
        }).start(3000);

        System.out.println("Server started on port 3000 , Accéder au site MaVille visiter : http://localhost:3000");

        /**
         * GET /api/test
         * Point de test pour vérifier que le serveur fonctionne.
         * @return Message de confirmation
         */
        app.get("/api/test", ctx -> {
            System.out.println("Test endpoint hit!");
            ctx.result("Test successful!");
        });

        /**
         * POST /api/login
         * Authentifie un utilisateur et renvoie son type (résident ou intervenant).
         * @body LoginRequest {email, password}
         * @return LoginResponse avec message et type d'utilisateur
         */
        app.post("/api/login", ctx -> {
            System.out.println("Received login request");
            try {
                LoginRequest request = ctx.bodyAsClass(LoginRequest.class);
                System.out.println("Email received: " + request.email);
                boolean isAuthenticated = database.authentify(request.email, request.password);
                if (isAuthenticated) {
                    User loggedInUser = database.getUser(request.email);
                    String userType = "user";
                    if (loggedInUser instanceof Resident) {
                        userType = "resident";
                    } else if (loggedInUser instanceof Intervenant) {
                        userType = "intervenant";
                    }
                    ctx.json(new LoginResponse("Connexion réussie !", userType));
                } else {
                    ctx.status(401).json(new Message("Email ou mot de passe incorrect."));
                }
            } catch (Exception e) {
                System.out.println("Error in login endpoint: " + e.getMessage());
                ctx.status(500).json(new Message("Erreur serveur: " + e.getMessage()));
            }
        });

        /**
         * POST /api/logout
         * Déconnecte l'utilisateur actuel.
         * @return Message de confirmation
         */
        app.post("/api/logout", ctx -> {
            // Pour l'instant, pas de gestion d'état. On renvoie juste un message.
            System.out.println("Received logout request");
            ctx.json(new Message("Déconnexion réussie !"));
        }); 

        /**
         * POST /api/users
         * Crée un nouveau compte utilisateur (résident ou intervenant).
         * @body RegistrationRequest avec les détails de l'utilisateur
         * @return Message de confirmation ou erreur
         */
        app.post("/api/users", ctx -> { 
            try {
                RegistrationRequest request = ctx.bodyAsClass(RegistrationRequest.class);

                // Vérifier si l'utilisateur existe déjà
                if (database.exists(request.email)) {
                    ctx.status(409).json(new Message("Un utilisateur avec cet email existe déjà."));
                    return;
                }

                // Séparer le prénom et le nom
                String[] parts = request.fullName.trim().split(" ", 2);
                String firstName = parts[0];
                String lastName = parts.length > 1 ? parts[1] : "";

                if ("resident".equalsIgnoreCase(request.userType)) {
                    // Valider les champs pour les résidents
                    if (request.dob == null || !request.dob.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        ctx.status(400).json(new Message("Date de naissance invalide ou manquante (format attendu: YYYY-MM-DD)."));
                        return;
                    }

                    if (request.address == null || request.address.trim().isEmpty()) {
                        ctx.status(400).json(new Message("Adresse résidentielle manquante."));
                        return;
                    }

                    // Convertir `dob` en `int` (année uniquement)
                    int birthDay = Integer.parseInt(request.dob.substring(0, 4));

                    // Créer un résident
                    Resident newResident = new Resident(
                        firstName,
                        lastName,
                        request.email,
                        request.password,
                        null, // Pas de numéro de téléphone obligatoire
                        request.address,
                        birthDay
                    );

                    database.addResident(newResident);

                } else if ("intervenant".equalsIgnoreCase(request.userType)) {
                    // Valider les champs pour les intervenants
                    if (request.cityId == null || !request.cityId.matches("\\d{8}")) {
                        ctx.status(400).json(new Message("ID de ville invalide ou manquant (8 chiffres requis)."));
                        return;
                    }

                    if (request.intervenantType == null || request.intervenantType.trim().isEmpty()) {
                        ctx.status(400).json(new Message("Type d'intervenant manquant."));
                        return;
                    }

                    // Valider le type d'intervenant
                    Intervenant.InType inType;
                    try {
                        inType = Intervenant.InType.valueOf(request.intervenantType);
                    } catch (IllegalArgumentException e) {
                        ctx.status(400).json(new Message("Type d'intervenant invalide."));
                        return;
                    }

                    // Créer un intervenant
                    Intervenant newIntervenant = new Intervenant(
                        firstName,
                        lastName,
                        request.email,
                        request.password,
                        inType,
                        Integer.parseInt(request.cityId)
                    );

                    database.addIntervenant(newIntervenant);

                } else {
                    ctx.status(400).json(new Message("Type d'utilisateur invalide."));
                    return;
                }

                ctx.json(new Message("Compte créé avec succès !"));
            } catch (Exception e) {
                ctx.status(500).json(new Message("Erreur serveur: " + e.getMessage()));
            }
        });

        /**
         * PUT /api/users/{email}/preferences
         * Met à jour les préférences horaires d'un résident.
         * @param email Email du résident
         * @body PreferenceUpdateRequest avec jour et horaires
         * @return Message de confirmation
         */
        app.put("/api/users/{email}/preferences", ctx -> {
            try {
                String email = ctx.pathParam("email");
                PreferenceUpdateRequest payload = ctx.bodyAsClass(PreferenceUpdateRequest.class);

                // Validation des champs
                if (payload.day < 0 || payload.day > 6 || payload.start == null || payload.end == null || payload.status == null) {
                    ctx.status(400).json(new Message("Tous les champs sont requis et le jour doit être entre 0 et 6."));
                    return;
                }

                // Récupérer l'utilisateur
                User user = database.getUser(email);
                if (user == null) {
                    ctx.status(404).json(new Message("Utilisateur non trouvé."));
                    return;
                }

                if (!(user instanceof Resident)) {
                    ctx.status(403).json(new Message("Seuls les résidents peuvent modifier leurs préférences."));
                    return;
                }

                Resident resident = (Resident) user;

                // Valider les heures au format HH:mm
                if (!payload.start.matches("^([01]\\d|2[0-3]):([0-5]\\d)$") || !payload.end.matches("^([01]\\d|2[0-3]):([0-5]\\d)$")) {
                    ctx.status(400).json(new Message("Les heures doivent être au format HH:mm."));
                    return;
                }

                // Vérifier que start < end
                if (payload.start.compareTo(payload.end) >= 0) {
                    ctx.status(400).json(new Message("L'heure de début doit être avant l'heure de fin."));
                    return;
                }

                // Vérifier les limites du jour
                if (payload.day < 0 || payload.day > 6) {
                    ctx.status(400).json(new Message("Jour invalide. Doit être entre 0 et 6."));
                    return;
                }

                // Mettre à jour les créneaux horaires dans le schedule
                boolean updateResult = resident.getSchedule().updateSlot(payload.day, payload.start, payload.end, payload.status);
                if (!updateResult) {
                    ctx.status(400).json(new Message("Erreur lors de la mise à jour des préférences horaires."));
                    return;
                }

                ctx.json(new Message("Préférences mises à jour avec succès !"));
            } catch (Exception e) {
                System.out.println("Error in update preferences endpoint: " + e.getMessage());
                ctx.status(500).json(new Message("Erreur serveur: " + e.getMessage()));
            }
        });

        /**
         * GET /api/users/{email}/notifications
         * Récupère les notifications d'un utilisateur.
         * @param email Email de l'utilisateur
         * @return NotificationResponse avec la liste des notifications
         */
        app.get("/api/users/{email}/notifications", ctx -> {
            String email = ctx.pathParam("email");
            System.out.println("Received notifications request for user: " + email);

            User user = database.getUser(email);
            if (user == null) {
                ctx.status(404).json(new Message("Utilisateur non trouvé."));
                return;
            }

            if (user.getNotifications() == null || user.getNotifications().isEmpty()) {
                ctx.json(new NotificationResponse("Aucune notification trouvée.", new Notification[0]));
                return;
            }

            // On retourne les notifications sous forme de tableau JSON
            Notification[] notificationsArray = user.getNotifications().toArray(new Notification[0]);
            ctx.json(new NotificationResponse("Notifications récupérées avec succès.", notificationsArray));
        });

        /**
         * GET /api/projects/current
         * Récupère la liste des projets en cours.
         * @return ProjectListResponse avec les projets en cours
         */
        app.get("/api/projects/current", ctx -> {
            System.out.println("Received request for current (in-progress) projects");
            var allProjects = database.getProjectList();
            var inProgress = allProjects.stream()
                                        .filter(p -> p.getStatus() == Project.Progress.IN_PROGRESS)
                                        .toArray(Project[]::new);

            ctx.json(new ProjectListResponse("Projets en cours récupérés avec succès.", inProgress));
        });

        /**
         * GET /api/projects/upcoming
         * Récupère les projets à venir dans les 3 prochains mois.
         * @return ProjectListResponse avec les projets à venir
         */
        app.get("/api/projects/upcoming", ctx -> {
            System.out.println("Received request for upcoming projects");
            var allProjects = database.getProjectList();
            var now = Instant.now().atZone(ZoneId.systemDefault()).toLocalDateTime();

            var upcoming = allProjects.stream()
                                    .filter(p -> p.getStatus() == Project.Progress.NOT_STARTED)
                                    .filter(p -> {
                                        LocalDateTime start = toLocalDateTime(p.getStart_date());
                                        LocalDateTime in3Months = now.plusMonths(3);
                                        return start.isBefore(in3Months); 
                                    })
                                    .toArray(Project[]::new);

            ctx.json(new ProjectListResponse("Projets à venir dans les 3 prochains mois récupérés avec succès.", upcoming));
        });

        /**
         * GET /api/projects
         * Récupère les projets avec filtre optionnel par quartier.
         * @param district (optionnel) Nom du quartier pour filtrer
         * @return ProjectListResponse avec les projets filtrés
         */
        app.get("/api/projects", ctx -> {
            String districtQuery = ctx.queryParam("district");
            System.out.println("Received request for projects with optional district filter: " + districtQuery);

            var allProjects = database.getProjectList();

            Project[] filtered;
            if (districtQuery == null || districtQuery.isEmpty()) {
                // Pas de filtre, retournez tous les projets
                filtered = allProjects.toArray(new Project[0]);
            } else {
                // Filtrer par district
                filtered = allProjects.stream()
                .filter(p -> p.getDistrict() != null && p.getDistrict().getName().toString().equalsIgnoreCase(districtQuery))
                .toArray(Project[]::new);
            }

            ctx.json(new ProjectListResponse("Projets filtrés récupérés avec succès.", filtered));
        });

        /**
         * GET /api/entraves
         * Récupère les entraves avec filtre optionnel par rue.
         * @param street (optionnel) Nom de la rue pour filtrer
         * @return ImpedimentListResponse avec les entraves
         */
        app.get("/api/entraves", ctx -> {
            String streetQuery = ctx.queryParam("street");
            System.out.println("Received request for entraves with optional street filter: " + streetQuery);

            var allImpediments = database.getImpedimentList();

            Impediment[] filtered;
            if (streetQuery == null || streetQuery.isEmpty()) {
                // Pas de filtre, retournez toutes les entraves
                filtered = allImpediments.toArray(new Impediment[0]);
            } else {
                // Filtrer par rue en utilisant la méthode affects(String s)
                filtered = allImpediments.stream()
                                         .filter(i -> i.affects(streetQuery))
                                         .toArray(Impediment[]::new);
            }

            ctx.json(new ImpedimentListResponse("Entraves récupérées avec succès.", filtered));
        });

        /**
         * POST /api/requests
         * Crée une nouvelle requête par un résident.
         * @body RequestCreationPayload avec les détails de la requête
         * @return Message de confirmation
         */
        app.post("/api/requests", ctx -> {
            System.out.println("Nous avons reçu votre requête !");
            try {
                RequestCreationPayload payload = ctx.bodyAsClass(RequestCreationPayload.class);

                User user = database.getUser(payload.email);
                if (user == null) {
                    ctx.status(404).json(new Message("Utilisateur non trouvé."));
                    return;
                }
                if (!(user instanceof Resident)) {
                    ctx.status(403).json(new Message("Seuls les résidents peuvent soumettre une requête."));
                    return;
                }

                Project.Reason reason = payload.reason;

                District district = database.getDistrict(payload.district);
                if (district == null) {
                    ctx.status(400).json(new Message("District invalide: " + payload.district));
                    return;
                }

                // Parser la date
                Date startDate;
                try {
                    startDate = Date.format(payload.start);
                } catch (Exception e) {
                    ctx.status(400).json(new Message("Format de date invalide: " + payload.start));
                    return;
                }

                // Créer la requête
                Request newRequest = new Request(
                    reason, 
                    district,
                    startDate,
                    payload.streetid,
                    payload.fromname,
                    payload.toname,
                    payload.length,
                    payload.description,
                    payload.title,
                    user
                );

                // Ajouter la requête à la base de données
                database.addRequest(newRequest);

                ctx.json(new Message("Requête soumise avec succès !"));
            } catch (Exception e) {
                System.out.println("Error in request submission endpoint: " + e.getMessage());
                ctx.status(500).json(new Message("Erreur serveur: " + e.getMessage()));
            }
        });

        /**
         * GET /api/requests/by-resident
         * Récupère les requêtes d'un résident spécifique.
         * @param email Email du résident
         * @return RequestListResponse avec les requêtes
         */
        app.get("/api/requests/by-resident", ctx -> {
            String email = ctx.queryParam("email");
            if (email == null || email.isEmpty()) {
                ctx.status(400).json(new Message("Email est requis pour filtrer les requêtes."));
                return;
            }

            User user = database.getUser(email);
            if (user == null) {
                ctx.status(404).json(new Message("Utilisateur non trouvé."));
                return;
            }

            // On récupère toutes les requêtes
            var allRequests = database.getRequestList();

            // Filtrer par l'utilisateur demandant
            var userRequests = allRequests.stream()
                                          .filter(r -> r.getRequestingUser().getMail().equals(email))
                                          .toArray(Request[]::new);

            ctx.json(new RequestListResponse("Requêtes de l'utilisateur récupérées avec succès.", userRequests));
        });

        /**
         * POST /api/requests/{requestId}/apply
         * Permet à un intervenant de postuler pour une requête.
         * @param requestId ID de la requête
         * @body CandidaturePayload avec email de l'intervenant
         * @return Message de confirmation
         */
        app.post("/api/requests/{requestId}/apply", ctx -> {
            System.out.println("Received apply request for a request");
            try {
                long requestId = Long.parseLong(ctx.pathParam("requestId"));
                CandidaturePayload payload = ctx.bodyAsClass(CandidaturePayload.class);

                // Récupérer l'utilisateur intervenant
                User user = database.getUser(payload.email);
                if (user == null) {
                    ctx.status(404).json(new Message("Utilisateur non trouvé."));
                    return;
                }
                if (!(user instanceof Intervenant)) {
                    ctx.status(403).json(new Message("Seuls les intervenants peuvent postuler."));
                    return;
                }

                // Récupérer la requête
                Request req = database.getRequest(requestId);
                if (req == null) {
                    ctx.status(404).json(new Message("Requête non trouvée."));
                    return;
                }

                Intervenant inter = (Intervenant) user;

                // Vérifier s'il n'est pas déjà dans la liste
                if (req.getSupportingIntervenants().contains(inter)) {
                    ctx.status(400).json(new Message("Vous avez déjà postulé à cette requête."));
                    return;
                }

                // Ajouter l'intervenant
                req.addSupportingIntervenant(inter);

                ctx.json(new Message("Candidature soumise avec succès !"));
            } catch (Exception e) {
                System.out.println("Error in apply endpoint: " + e.getMessage());
                ctx.status(500).json(new Message("Erreur serveur: " + e.getMessage()));
            }
        });

        /**
         * DELETE /api/requests/{requestId}/apply
         * Permet à un intervenant de retirer sa candidature pour une requête.
         * @param requestId ID de la requête
         * @body CandidaturePayload avec email de l'intervenant
         * @return Message de confirmation
         */
        app.delete("/api/requests/{requestId}/apply", ctx -> {
            System.out.println("Received request to remove candidature");
            try {
                long requestId = Long.parseLong(ctx.pathParam("requestId"));
                CandidaturePayload payload = ctx.bodyAsClass(CandidaturePayload.class);

                // Récupérer l'utilisateur intervenant
                User user = database.getUser(payload.email);
                if (user == null) {
                    ctx.status(404).json(new Message("Utilisateur non trouvé."));
                    return;
                }
                if (!(user instanceof Intervenant)) {
                    ctx.status(403).json(new Message("Seuls les intervenants peuvent retirer une candidature."));
                    return;
                }

                Request req = database.getRequest(requestId);
                if (req == null) {
                    ctx.status(404).json(new Message("Requête non trouvée."));
                    return;
                }

                Intervenant inter = (Intervenant) user;

                if (!req.getSupportingIntervenants().contains(inter)) {
                    ctx.status(400).json(new Message("Aucune candidature trouvée pour cet intervenant sur cette requête."));
                    return;
                }

                // Retirer l'intervenant
                req.getSupportingIntervenants().remove(inter);

                ctx.json(new Message("Candidature retirée avec succès !"));
            } catch (Exception e) {
                System.out.println("Error in remove candidature endpoint: " + e.getMessage());
                ctx.status(500).json(new Message("Erreur serveur: " + e.getMessage()));
            }
        });

        /**
         * GET /api/requests/by-intervenant
         * Récupère les requêtes auxquelles un intervenant spécifique a postulé.
         * @param emailResident (optionnel) Email du résident
         * @param emailIntervenant (optionnel) Email de l'intervenant
         * @return RequestListResponse avec les requêtes filtrées
         */
        app.get("/api/requests/by-intervenant", ctx -> {
            String emailResident = ctx.queryParam("email");
            String emailIntervenant = ctx.queryParam("intervenantEmail");

            // Si on cherche par resident
            if (emailResident != null && !emailResident.isEmpty()) {
                User user = database.getUser(emailResident);
                if (user == null) {
                    ctx.status(404).json(new Message("Utilisateur non trouvé."));
                    return;
                }
                var allRequests = database.getRequestList();
                var userRequests = allRequests.stream()
                                            .filter(r -> r.getRequestingUser().getMail().equals(emailResident))
                                            .toArray(Request[]::new);
                ctx.json(new RequestListResponse("Requêtes de l'utilisateur récupérées avec succès.", userRequests));
                return;
            }

            // Sinon, si on cherche par intervenant
            if (emailIntervenant != null && !emailIntervenant.isEmpty()) {
                User userInter = database.getUser(emailIntervenant);
                if (userInter == null) {
                    ctx.status(404).json(new Message("Intervenant non trouvé."));
                    return;
                }
                if (!(userInter instanceof Intervenant)) {
                    ctx.status(403).json(new Message("Seuls les intervenants peuvent avoir des candidatures."));
                    return;
                }

                Intervenant inter = (Intervenant) userInter;
                var allRequests = database.getRequestList();
                var intervenantRequests = allRequests.stream()
                                                    .filter(r -> r.getSupportingIntervenants().contains(inter))
                                                    .toArray(Request[]::new);
                ctx.json(new RequestListResponse("Requêtes où l'intervenant a postulé récupérées avec succès.", intervenantRequests));
                return;
            }

            // Si aucun paramètre n'est fourni, on peut retourner une erreur ou toutes les requêtes
            ctx.status(400).json(new Message("Veuillez fournir 'email' (résident) ou 'intervenantEmail' (intervenant)."));
        });

        /**
         * POST /api/projects
         * Crée un nouveau projet par un intervenant.
         * @body ProjectCreationPayload avec les détails du projet
         * @return Message de confirmation avec l'ID du projet
         */
        app.post("/api/projects", ctx -> {
            System.out.println("Received request to create a new project");
            try {
                ProjectCreationPayload payload = ctx.bodyAsClass(ProjectCreationPayload.class);

                // Vérifier que l'utilisateur est un intervenant
                User user = database.getUser(payload.emailIntervenant);
                if (user == null) {
                    ctx.status(404).json(new Message("Utilisateur (intervenant) non trouvé."));
                    return;
                }
                if (!(user instanceof Intervenant)) {
                    ctx.status(403).json(new Message("Seuls les intervenants peuvent soumettre un projet."));
                    return;
                }

                // Convertir reason
                Project.Reason reason;
                try {
                    reason = Project.Reason.valueOf(payload.reason);
                } catch (Exception e) {
                    ctx.status(400).json(new Message("Raison invalide: " + payload.reason));
                    return;
                }

                // Récupérer le district
                Database.District_name dName;
                try {
                    dName = database.toDistrict_name(payload.district);
                } catch (Exception e) {
                    ctx.status(400).json(new Message("District invalide: " + payload.district));
                    return;
                }

                District district = database.getDistrict(dName);
                if (district == null) {
                    ctx.status(400).json(new Message("District introuvable: " + payload.district));
                    return;
                }

                // Dates
                metrics.Date startDate;
                metrics.Date endDate;
                try {
                    startDate = metrics.Date.format(payload.start);
                    endDate = metrics.Date.format(payload.end);
                } catch (Exception e) {
                    ctx.status(400).json(new Message("Format de date invalide. Utilisez un format ISO." ));
                    return;
                }

                // Créer le projet (par défaut PLANNED)
                Project project = new Project(
                    // vous pouvez adapter ces paramètres selon votre constructeur Project
                    database.getProjectList().size(), 
                    "bigId_" + new Random().nextInt(100000),
                    "permit_id_test",
                    "permit_category_test",
                    "123456",
                    reason.toString(),
                    payload.title == null ? "Sans titre" : payload.title,
                    district,
                    payload.start,
                    payload.end,
                    "occupancy_test",
                    "organization_test",
                    "submitter_test",
                    new Coordinates(0,0)
                );

                database.addProject(project);

                ctx.json(new Message("Projet soumis avec succès avec l'ID: " + project.id));
            } catch (Exception e) {
                System.out.println("Error in project submission endpoint: " + e.getMessage());
                ctx.status(500).json(new Message("Erreur serveur: " + e.getMessage()));
            }
        });

        /**
         * PUT /api/projects/{id}/status
         * Met à jour le statut d'un projet.
         * @param id ID du projet
         * @body ProjectStatusUpdatePayload avec nouveau statut
         * @return Message de confirmation
         */
        app.put("/api/projects/{id}/status", ctx -> {
            System.out.println("Received request to update project status");
            try {
                int projectId = Integer.parseInt(ctx.pathParam("id"));
                ProjectStatusUpdatePayload payload = ctx.bodyAsClass(ProjectStatusUpdatePayload.class);

                User user = database.getUser(payload.emailIntervenant);
                if (user == null) {
                    ctx.status(404).json(new Message("Intervenant non trouvé."));
                    return;
                }
                if (!(user instanceof Intervenant)) {
                    ctx.status(403).json(new Message("Seuls les intervenants peuvent modifier le statut."));
                    return;
                }

                Project project = database.getProjectList().stream()
                                          .filter(p -> p.id == projectId)
                                          .findFirst().orElse(null);

                if (project == null) {
                    ctx.status(404).json(new Message("Projet non trouvé."));
                    return;
                }

                Project.Progress newStatus;
                try {
                    newStatus = Project.Progress.valueOf(payload.newStatus);
                } catch (Exception e) {
                    ctx.status(400).json(new Message("Statut invalide: " + payload.newStatus));
                    return;
                }

                project.setStatus(newStatus);

                if (newStatus == Project.Progress.IN_PROGRESS || newStatus == Project.Progress.FINISHED) {
                    // Appel à la méthode du database
                    database.sendNotificationToDistrict(project.getDistrict().getName(), 
                        "Le projet " + project.getTitle() + " est maintenant " + newStatus.toString());
                }

                ctx.json(new Message("Statut mis à jour avec succès !"));
            } catch (Exception e) {
                System.out.println("Error in project status update endpoint: " + e.getMessage());
                ctx.status(500).json(new Message("Erreur serveur: " + e.getMessage()));
            }
        });

        /**
         * PUT /api/requests/{requestId}/choose
         * Permet à un résident de choisir un intervenant pour sa requête.
         * @param requestId ID de la requête
         * @body ChooseCandidatePayload avec emails et message
         * @return Message de confirmation
         */
        app.put("/api/requests/{requestId}/choose", ctx -> {
            try {
                long requestId = Long.parseLong(ctx.pathParam("requestId"));
                ChooseCandidatePayload payload = ctx.bodyAsClass(ChooseCandidatePayload.class);

                // Vérifier que l'utilisateur est le résident de la requête
                User user = database.getUser(payload.emailResident);
                if (user == null) {
                    ctx.status(404).json(new Message("Utilisateur (résident) non trouvé."));
                    return;
                }
                if (!(user instanceof Resident)) {
                    ctx.status(403).json(new Message("Seuls les résidents peuvent choisir une candidature."));
                    return;
                }

                Request req = database.getRequest(requestId);
                if (req == null) {
                    ctx.status(404).json(new Message("Requête non trouvée."));
                    return;
                }

                // Vérifier que ce résident est le propriétaire de la requête
                if (!req.getRequestingUser().getMail().equals(payload.emailResident)) {
                    ctx.status(403).json(new Message("Vous n'êtes pas le propriétaire de cette requête."));
                    return;
                }

                // Vérifier que l'intervenant a bien postulé
                User chosenUser = database.getUser(payload.emailIntervenant);
                if (chosenUser == null || !(chosenUser instanceof Intervenant)) {
                    ctx.status(404).json(new Message("Intervenant choisi non trouvé."));
                    return;
                }
                Intervenant chosenInter = (Intervenant) chosenUser;

                if (!req.getSupportingIntervenants().contains(chosenInter)) {
                    ctx.status(400).json(new Message("Cet intervenant n'a pas postulé à cette requête."));
                    return;
                }

                // On enregistre le choix
                req.setChosenIntervenant(chosenInter);
                req.setChoiceMessage(payload.messageOptionnel); // message optionnel

                ctx.json(new Message("Candidature choisie avec succès !"));
            } catch (Exception e) {
                System.out.println("Error in choose candidate endpoint: " + e.getMessage());
                ctx.status(500).json(new Message("Erreur serveur: " + e.getMessage()));
            }
        });

        /**
         * PUT /api/requests/{requestId}/confirm
         * Permet à un intervenant de confirmer sa participation à une requête.
         * @param requestId ID de la requête
         * @body ConfirmCandidaturePayload avec email de l'intervenant
         * @return Message de confirmation
         */
        app.put("/api/requests/{requestId}/confirm", ctx -> {
            try {
                long requestId = Long.parseLong(ctx.pathParam("requestId"));
                ConfirmCandidaturePayload payload = ctx.bodyAsClass(ConfirmCandidaturePayload.class);

                User user = database.getUser(payload.emailIntervenant);
                if (user == null) {
                    ctx.status(404).json(new Message("Intervenant non trouvé."));
                    return;
                }
                if (!(user instanceof Intervenant)) {
                    ctx.status(403).json(new Message("Seuls les intervenants peuvent confirmer une candidature."));
                    return;
                }
                Intervenant inter = (Intervenant) user;

                Request req = database.getRequest(requestId);
                if (req == null) {
                    ctx.status(404).json(new Message("Requête non trouvée."));
                    return;
                }

                // Vérifier que cet intervenant est le chosenIntervenant
                if (!req.getChosenIntervenant().equals(inter)) {
                    ctx.status(403).json(new Message("Vous n'êtes pas l'intervenant choisi pour cette requête."));
                    return;
                }

                req.setConfirmed(true); // marquer la candidature comme confirmée

                ctx.json(new Message("Candidature confirmée avec succès !"));
            } catch (Exception e) {
                System.out.println("Error in confirm candidature endpoint: " + e.getMessage());
                ctx.status(500).json(new Message("Erreur serveur: " + e.getMessage()));
            }
        });

        /**
         * PUT /api/requests/{requestId}/close
         * Ferme une requête par un résident.
         * @param requestId ID de la requête
         * @body CloseRequestPayload avec email du résident
         * @return Message de confirmation
         */
        app.put("/api/requests/{requestId}/close", ctx -> {
            try {
                long requestId = Long.parseLong(ctx.pathParam("requestId"));
                CloseRequestPayload payload = ctx.bodyAsClass(CloseRequestPayload.class);

                User user = database.getUser(payload.emailResident);
                if (user == null) {
                    ctx.status(404).json(new Message("Utilisateur non trouvé."));
                    return;
                }
                if (!(user instanceof Resident)) {
                    ctx.status(403).json(new Message("Seuls les résidents peuvent fermer la requête."));
                    return;
                }

                Request req = database.getRequest(requestId);
                if (req == null) {
                    ctx.status(404).json(new Message("Requête non trouvée."));
                    return;
                }

                if (!req.getRequestingUser().getMail().equals(payload.emailResident)) {
                    ctx.status(403).json(new Message("Vous n'êtes pas le propriétaire de cette requête."));
                    return;
                }

                req.setClosed(true); // Marquer la requête comme fermée
                ctx.json(new Message("Requête fermée avec succès !"));
            } catch (Exception e) {
                System.out.println("Error in close request endpoint: " + e.getMessage());
                ctx.status(500).json(new Message("Erreur serveur: " + e.getMessage()));
            }
        });

        /**
         * GET /api/requests
         * Récupère toutes les requêtes.
         * @return RequestListResponse avec toutes les requêtes
         */
        app.get("/api/requests", ctx -> {
            System.out.println("Received request for all requests");
            var allRequests = database.getRequestList().toArray(new Request[0]);
            ctx.json(new RequestListResponse("Toutes les requêtes récupérées avec succès.", allRequests));
        });

        /**
         * POST /api/notifications
         * Envoie une notification à tous les résidents d'un district.
         * @body NotificationPayload avec district et message
         * @return Message de confirmation
         */
        app.post("/api/notifications", ctx -> {
            System.out.println("Received request to send notifications");
            try {
                NotificationPayload payload = ctx.bodyAsClass(NotificationPayload.class);

                // Vérifiez que le district existe
                District district = Database.getDistrict(payload.district); // Appel via la classe Database
                if (district == null) {
                    ctx.status(400).json(new Message("District invalide: " + payload.district));
                    return;
                }

                // Envoyez une notification
                database.sendNotificationToDistrict(district.getName(), payload.message); // Appel via l'instance

                ctx.json(new Message("Notification envoyée avec succès au district: " + district.getName()));
            } catch (Exception e) {
                System.out.println("Error in send notification endpoint: " + e.getMessage());
                ctx.status(500).json(new Message("Erreur serveur: " + e.getMessage()));
            }
        });

        // GET /api/projects/{id} : Récupérer une requête par ID (Commenté car non implémenté)
        // Vous pouvez ajouter ce endpoint si nécessaire
    }

    /**
     * Convertit un objet metrics.Date en LocalDateTime.
     * @param d L'objet metrics.Date à convertir
     * @return LocalDateTime correspondant
     */
    private static LocalDateTime toLocalDateTime(metrics.Date d) {
        return LocalDateTime.of(d.getYear(), d.getMonth(), d.getDay(), d.getHour(), d.getMinute(), d.getSecond());
    }

    // Classes internes pour connex/inscriptions

    /**
     * Classe représentant une requête de connexion
     */
    static class LoginRequest {
        public String email;
        public String password;
    }

    /**
     * Classe représentant la réponse à une connexion réussie
     */
    static class LoginResponse {
        public String message;
        public String userType;

        public LoginResponse(String message, String userType) {
            this.message = message;
            this.userType = userType;
        }
    }

    /**
     * Classe représentant un message simple
     */
    static class Message {
        public String message;

        public Message(String message) {
            this.message = message;
        }
    }

    // Classe interne pour la requête

    /**
     * Classe représentant une requête d'inscription
     */
    static class RegistrationRequest {
        public String fullName;
        public String email;
        public String password;
        public String userType;       // "resident" ou "intervenant"
        public String dob;            // date de naissance, ex: "1990-05-20"
        public String address;        // adresse résidentielle
        public String cityId;         // Pour intervenant, code à 8 chiffres
        public String intervenantType; // "public_enterprise", "private_entrepreneur", "individual", etc.
    }

    /**
     * Classe représentant une requête de mise à jour des préférences
     */
    static class PreferenceUpdateRequest {
        public int day;       // 0 = Lundi, 1 = Mardi, ..., 6 = Dimanche
        public String start;  // Heure de début au format HH:mm
        public String end;    // Heure de fin au format HH:mm
        public String status; // ### pour occupé, --- pour libre
    }

    // Classe interne pour la réponse JSON
    /**
     * Classe de réponse JSON pour les notifications
     */
    static class NotificationResponse {
        public String message;
        public Notification[] notifications;

        public NotificationResponse(String message, Notification[] notifications) {
            this.message = message;
            this.notifications = notifications;
        }
    }

    /**
     * Classe de réponse JSON pour la liste des projets
     */
    static class ProjectListResponse {
        public String message;
        public Project[] projects;

        public ProjectListResponse(String message, Project[] projects) {
            this.message = message;
            this.projects = projects;
        }
    }

    /**
     * Classe de réponse JSON pour les entraves
     */
    static class ImpedimentListResponse {
        public String message;
        public Parser.Impediment[] impediments;

        public ImpedimentListResponse(String message, Parser.Impediment[] impediments) {
            this.message = message;
            this.impediments = impediments;
        }
    }

    /**
     * Classe représentant le payload pour la création d'une requête
     */
    static class RequestCreationPayload {
        public String email;
        public Project.Reason reason; // ex: "3"
        public Database.District_name district; // ex: "LaSalle"
        public String start;    // ex: "2024-12-06T00:00:00Z"
        public String streetid;
        public String fromname;
        public String toname;
        public double length;
        public String description;
        public String title;
    }

    /**
     * Classe de réponse JSON pour la liste des requêtes
     */
    static class RequestListResponse {
        public String message;
        public Request[] requests;

        public RequestListResponse(String message, Request[] requests) {
            this.message = message;
            this.requests = requests;
        }
    }

    /**
     * Classe représentant le payload pour une candidature
     */
    static class CandidaturePayload {
        public String email; // l'email de l'intervenant
    }

    /**
     * Classe représentant le payload pour la création d'un projet
     */
    static class ProjectCreationPayload {
        public String emailIntervenant;  
        public String reason;            
        public String district;          
        public String start;             
        public String end;               
        public String title;
        public String description;
    }

    /**
     * Classe représentant le payload pour la mise à jour du statut d'un projet
     */
    static class ProjectStatusUpdatePayload {
        public String emailIntervenant; // email de l'intervenant qui effectue la modification
        public String newStatus;        // ex: "IN_PROGRESS" ou "FINISHED"
    }

    /**
     * Classe représentant le payload pour choisir un candidat
     */
    static class ChooseCandidatePayload {
        public String emailResident;      // email du résident (demandeur)
        public String emailIntervenant;   // email de l’intervenant choisi
        public String messageOptionnel;   // message optionnel
    }

    /**
     * Classe représentant le payload pour confirmer une candidature
     */
    static class ConfirmCandidaturePayload {
        public String emailIntervenant; // l'intervenant qui confirme
    }

    /**
     * Classe représentant le payload pour fermer une requête
     */
    static class CloseRequestPayload {
        public String emailResident;
    }

    /**
     * Classe représentant le payload pour envoyer une notification
     */
    static class NotificationPayload {
        public String district;
        public String message;
    }
}
