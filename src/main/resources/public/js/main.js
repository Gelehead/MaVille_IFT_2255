// main.js

// Fonction pour tester la connexion

document.addEventListener('DOMContentLoaded', async function() {
    // Test de connexion 
    try {
        const testResult = await testConnection();
        console.log('Connection test result:', testResult);
    } catch (error) {
        console.error('Connection test failed:', error);
    }

    // Ajouter l'écouteur d'événement sur le bouton logout
    const logoutBtn = document.getElementById('logout-btn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', async () => {
        try {
            const response = await logoutUser();
            console.log('Déconnexion réussie:', response);

            // Supprimer l'email du localStorage
            localStorage.removeItem('email');

            alert('Déconnexion réussie !');
            window.location.href = 'login.html';
        } catch (error) {
            alert(error.message || 'Erreur lors de la déconnexion');
        }
    });
}

});


// Fonction pour le formulaire d'inscription

document.addEventListener('DOMContentLoaded', function () {
  const registerForm = document.getElementById('register-form');
  const userType = document.getElementById('user-type');
  const intervenantFields = document.getElementById('intervenant-fields');

  if (userType) {
    userType.addEventListener('change', () => {
      if (userType.value === 'intervenant') {
        intervenantFields.classList.remove('d-none');
      } else {
        intervenantFields.classList.add('d-none');
      }
    });
  }

  if (registerForm) {
    registerForm.addEventListener('submit', async function (e) {
      e.preventDefault();

      const fullName = document.getElementById('full-name').value;
      const dob = document.getElementById('dob').value;
      const email = document.getElementById('email').value;
      const password = document.getElementById('password').value;
      const address = document.getElementById('address').value;
      const userTypeValue = userType.value;

      let cityId = null;
      let intervenantType = null;

      if (userTypeValue === 'intervenant') {
        cityId = document.getElementById('city-id').value;
        intervenantType = document.getElementById('intervenant-type').value;
      }

      try {
        const response = await registerUser(fullName, dob, email, password, address, userTypeValue, cityId, intervenantType);
        alert('Compte créé avec succès !');

        // Redirection basée sur le type d'utilisateur
        if (userTypeValue === 'resident') {
          window.location.href = 'resident.html';
        } else if (userTypeValue === 'intervenant') {
          window.location.href = 'intervenant.html';
        }
      } catch (error) {
        alert(error.message || 'Erreur lors de la création du compte');
      }
    });
  }
});

// Fonction pour le formulaire de préférences

document.addEventListener('DOMContentLoaded', function () {
  const preferencesForm = document.getElementById('preferences-form');
  
  if (preferencesForm) {
      preferencesForm.addEventListener('submit', async function(e) {
          e.preventDefault();
          
          const email = document.getElementById('email').value;
          const day = parseInt(document.getElementById('day').value, 10); // Convertir en entier
          const startTime = document.getElementById('start-time').value; // Format HH:mm
          const endTime = document.getElementById('end-time').value; // Format HH:mm
          const status = document.querySelector('input[name="status"]:checked').value;

          // Validation supplémentaire si nécessaire
          if (!email || isNaN(day) || day < 0 || day > 6 || !startTime || !endTime || !status) {
              alert('Tous les champs sont obligatoires et le jour doit être entre 0 et 6.');
              return;
          }

          try {
              const response = await updatePreferences(email, day, startTime, endTime, status);
              alert('Préférences mises à jour avec succès !');
              preferencesForm.reset();
          } catch (error) {
              alert(error.message || 'Erreur lors de la mise à jour des préférences');
          }
      });
  }
});




  // Fonction pour le formulaire de notifications

  document.addEventListener('DOMContentLoaded', function() {
    const notificationsForm = document.getElementById('notifications-form');
    if (notificationsForm) {
      notificationsForm.addEventListener('submit', async function(e) {
        e.preventDefault();
        const email = document.getElementById('email').value;
  
        try {
          const response = await getUserNotifications(email);
          console.log('Notifications récupérées:', response);
          afficherNotifications(response.notifications);
        } catch (error) {
          alert(error.message || 'Erreur lors de la récupération des notifications');
        }
      });
    }
  });
  
  function afficherNotifications(notifications) {
    const container = document.getElementById('notifications-list');
    container.innerHTML = '';
  
    if (notifications.length === 0) {
      container.innerHTML = '<p>Aucune notification.</p>';
      return;
    }
  
    const ul = document.createElement('ul');
    ul.classList.add('list-group');
  
    notifications.forEach(notif => {
      const li = document.createElement('li');
      li.classList.add('list-group-item');
      li.innerHTML = `<strong>${notif.resume}</strong><br>${notif.description}`;
      ul.appendChild(li);
    });
  
    container.appendChild(ul);
  }
  
  // Fonction pour le formulaire de requêtes
  document.addEventListener('DOMContentLoaded', function() {
    const currentBtn = document.getElementById('current-btn');
    const upcomingBtn = document.getElementById('upcoming-btn');
    const filterBtn = document.getElementById('filter-btn');
    const districtSelect = document.getElementById('district-select');
  
    // Projets en cours
    currentBtn.addEventListener('click', async () => {
      try {
        const data = await getCurrentProjects();
        afficherProjets(data.projects);
      } catch (error) {
        alert(error.message || 'Erreur lors de la récupération des projets en cours');
      }
    });
  
    // Projets à venir
    upcomingBtn.addEventListener('click', async () => {
      try {
        const data = await getUpcomingProjects();
        afficherProjets(data.projects);
      } catch (error) {
        alert(error.message || 'Erreur lors de la récupération des projets à venir');
      }
    });
  
    // Filtrer par district
    filterBtn.addEventListener('click', async () => {
      const district = districtSelect.value;
      if (!district) {
        alert('Veuillez sélectionner un district');
        return;
      }
  
      try {
        const data = await getProjectsByDistrict(district);
        afficherProjets(data.projects);
      } catch (error) {
        alert(error.message || 'Erreur lors de la récupération des projets pour ce district');
      }
    });
  });
  
  
    
  function afficherProjets(projects) {
    const container = document.getElementById('projects-list');
    container.innerHTML = '';
    
    if (!projects || projects.length === 0) {
      container.innerHTML = '<p>Aucun projet trouvé.</p>';
      return;
    }
  
    const ul = document.createElement('ul');
    ul.classList.add('list-group');
  
    projects.forEach(p => {
      const li = document.createElement('li');
      li.classList.add('list-group-item');
      li.innerHTML = `<strong>${p.title}</strong><br>Statut: ${p.status}<br>District: ${p.district ? p.district.name : 'N/A'}<br>Raison: ${p.reason}`;
      ul.appendChild(li);
    });
  
    container.appendChild(ul);
  }

  document.addEventListener('DOMContentLoaded', function() {
    const allEntravesBtn = document.getElementById('all-entraves-btn');
    const filterEntravesBtn = document.getElementById('filter-entraves-btn');
  
    if (allEntravesBtn) {
      allEntravesBtn.addEventListener('click', async () => {
        try {
          const data = await getAllEntraves();
          afficherEntraves(data.impediments);
        } catch (error) {
          alert(error.message);
        }
      });
    }
  
    if (filterEntravesBtn) {
      filterEntravesBtn.addEventListener('click', async () => {
        const street = document.getElementById('street-input').value;
        try {
          const data = await getEntravesByStreet(street);
          afficherEntraves(data.impediments);
        } catch (error) {
          alert(error.message);
        }
      });
    }
  });
  
  function afficherEntraves(impediments) {
    const container = document.getElementById('entraves-list');
    container.innerHTML = '';
  
    if (!impediments || impediments.length === 0) {
      container.innerHTML = '<p>Aucune entrave trouvée.</p>';
      return;
    }
  
    const ul = document.createElement('ul');
    ul.classList.add('list-group');
  
    impediments.forEach(i => {
      const li = document.createElement('li');
      li.classList.add('list-group-item');
      li.innerHTML = `<strong>Rue: ${i.name}</strong><br>De: ${i.fromname || 'N/A'} à ${i.toname || 'N/A'}<br>Longueur: ${i.length || 'N/A'}<br>Arterielle: ${i.isarterial || 'N/A'}`;
      ul.appendChild(li);
    });
  
    container.appendChild(ul);
  }
  
// Fonction pour soumettre de requêtes
  document.addEventListener('DOMContentLoaded', function () {
    const submitRequestForm = document.getElementById('submit-request-form');

    submitRequestForm.addEventListener('submit', async function (e) {
      e.preventDefault();

      const email = document.getElementById('request-email').value.trim();
      const title = document.getElementById('request-title').value.trim();
      const description = document.getElementById('request-description').value.trim();
      const district = document.getElementById('district-select').value;
      const reason = document.getElementById('reason-select').value;
      const street = document.getElementById('street-name').value.trim();
      const fromname = document.getElementById('fromname').value.trim();
      const toname = document.getElementById('toname').value.trim();
      const length = parseFloat(document.getElementById('length').value);
      const startDateValue = document.getElementById('start-date').value;
      //const email = localStorage.getItem('email')?.trim();

      // Validation de base
      if (!email || !title || !description || !district || !street || !fromname || !toname || isNaN(length) || !startDateValue || !reason) {
        alert('Tous les champs sont obligatoires.');
        return;
      }

      // Conversion de la date (AAAA-MM-JJ) en format ISO avec T00:00:00Z
      const startISO = startDateValue + "T00:00:00Z";

      try {
        const response = await fetch('http://localhost:3000/api/requests', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            email: email,
            title: title,
            description: description,
            district: district,
            start: startISO,
            reason: reason,
            streetid: street,
            fromname: fromname,
            toname: toname,
            length: length
          }),
          credentials: 'include',
        });

        const data = await response.json();

        if (!response.ok) {
          throw new Error(data.message || 'Erreur lors de la soumission de la requête.');
        }

        alert('Requête soumise avec succès !');
        submitRequestForm.reset();
      } catch (error) {
        alert(error.message || 'Erreur lors de la soumission.');
      }
    });
  });
  

  document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('requests-by-intervenant-form');
    const requestsList = document.getElementById('requests-list');

    form.addEventListener('submit', async function(e) {
      e.preventDefault();
      const email = document.getElementById('intervenantEmail').value.trim();

      if (!email) {
        alert('Veuillez entrer votre email');
        return;
      }

      try {
        const response = await fetch(`http://localhost:3000/api/requests/by-intervenant?intervenantEmail=${encodeURIComponent(email)}`, {
          credentials: 'include'
        });
        const data = await response.json();

        if (!response.ok) {
          throw new Error(data.message || "Erreur lors de la récupération des requêtes.");
        }

        const requests = data.requests;
        if (!requests || requests.length === 0) {
          requestsList.innerHTML = '<p>Aucune requête trouvée.</p>';
          return;
        }

        const ul = document.createElement('ul');
        ul.classList.add('list-group');

        requests.forEach(req => {
          const li = document.createElement('li');
          li.classList.add('list-group-item', 'mb-3');

          let content = `
            <h5>${req.title}</h5>
            <p><strong>Description:</strong> ${req.description}</p>
            <p><strong>District:</strong> ${req.district ? req.district.name : 'N/A'}<br>
            <strong>Raison:</strong> ${req.reason}<br>
            <strong>Rue (streetid):</strong> ${req.streetid}<br>
            <strong>De:</strong> ${req.fromname}<br>
            <strong>À:</strong> ${req.toname}<br>
            <strong>Longueur:</strong> ${req.length || 'N/A'}<br>
            <strong>Début:</strong> ${req.start ? req.start.date : 'N/A'}<br>
            <strong>Fermée:</strong> ${req.closed ? 'Oui' : 'Non'}<br>
            <strong>Intervenant choisi:</strong> ${req.chosenIntervenant ? req.chosenIntervenant.mail : 'Aucun'}<br>
            <strong>Confirmé:</strong> ${req.confirmed ? 'Oui' : 'Non'}</p>
          `;

          const candidats = req.supportingIntervenants || [];
          if (candidats.length > 0) {
            content += '<p><strong>Intervenants ayant postulé:</strong></p><ul>';
            candidats.forEach(inter => {
              content += `<li>${inter.mail}</li>`;
            });
            content += '</ul>';
          } else {
            content += '<p><em>Aucun intervenant n\'a encore postulé.</em></p>';
          }

          // Si l'intervenant n'a pas encore postulé et que la requête n'est pas fermée afficher un bouton "Postuler"
          const intervenantDejaPostule = candidats.some(c => c.mail === email);
          if (!req.closed && !req.chosenIntervenant && !intervenantDejaPostule) {
            content += `<button class="btn btn-sm btn-success" onclick="applyRequest(${req.id}, '${email}')">Postuler à cette requête</button>`;
          }

          // Si l'intervenant a déjà postulé et n'est pas choisi ou confirmé  afficher un bouton pour retirer la candidature
          if (!req.closed && intervenantDejaPostule && (!req.chosenIntervenant || req.chosenIntervenant.mail !== email)) {
            content += `<button class="btn btn-sm btn-danger" onclick="removeCandidature(${req.id}, '${email}')">Retirer ma candidature</button>`;
          }

          li.innerHTML = content;
          ul.appendChild(li);
        });

        requestsList.innerHTML = '';
        requestsList.appendChild(ul);

      } catch (error) {
        requestsList.innerHTML = `<div class="alert alert-danger">${error.message}</div>`;
      }
    });
  });



  // Fonction pour postuler à une requête

  document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('submit-project-form');
    form.addEventListener('submit', async function(e) {
      e.preventDefault();

      const emailIntervenant = document.getElementById('emailIntervenant').value.trim();
      const district = document.getElementById('project-district').value;
      const reason = document.getElementById('project-reason').value;
      const title = document.getElementById('project-title').value.trim();
      const description = document.getElementById('project-description').value.trim();
      const startDate = document.getElementById('project-start').value;
      const endDate = document.getElementById('project-end').value;

      if (!emailIntervenant || !district || !reason || !title || !description || !startDate || !endDate) {
        alert('Tous les champs sont obligatoires.');
        return;
      }

      const startISO = startDate + "T00:00:00Z";
      const endISO = endDate + "T00:00:00Z";

      try {
        const response = await fetch('http://localhost:3000/api/projects', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          credentials: 'include',
          body: JSON.stringify({
            emailIntervenant,
            reason,
            district,
            start: startISO,
            end: endISO,
            title,
            description
          })
        });

        const data = await response.json();
        if (!response.ok) {
          throw new Error(data.message || 'Erreur lors de la soumission du projet.');
        }

        alert('Projet soumis avec succès !');
        form.reset();
      } catch (error) {
        alert(error.message);
      }
    });
  });


  // Fonction pour afficher les projets


  document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('send-notification-form');
    
    form.addEventListener('submit', async function(e) {
      e.preventDefault();
      
      const district = document.getElementById('district-select').value;
      const message = document.getElementById('notification-message').value.trim();

      if (!district || !message) {
        alert('Tous les champs sont obligatoires.');
        return;
      }

      try {
        const response = await fetch('http://localhost:3000/api/notifications', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          credentials: 'include',
          body: JSON.stringify({ district, message })
        });

        const data = await response.json();
        if (!response.ok) {
          throw new Error(data.message || 'Erreur lors de l\'envoi de la notification.');
        }

        alert('Notification envoyée avec succès !');
        form.reset();
      } catch (error) {
        alert(error.message);
      }
    });
  });

  // Fonction de connexion

  document.addEventListener('DOMContentLoaded', function() {
      const loginForm = document.getElementById('login-form');

      loginForm.addEventListener('submit', async function(e) {
          e.preventDefault();

          const email = document.getElementById('email').value;
          const password = document.getElementById('password').value;

          if (!email || !password) {
              alert('Veuillez remplir tous les champs');
              return;
          }

          try {
              const response = await loginUser(email, password);
              console.log('Connexion réussie:', response);
              console.log('Type utilisateur:', response.userType);

              if (response.message === "Connexion réussie !") {
                  console.log('Tentative de redirection...');
                  localStorage.setItem('email', email);

                  if (response.userType === "resident") {
                      console.log('Redirection vers resident.html');
                      window.location.href = 'resident.html';
                  } else if (response.userType === "intervenant") {
                      console.log('Redirection vers intervenant.html');
                      window.location.href = 'intervenant.html';
                  } else {
                      console.log('Redirection vers index.html');
                      window.location.href = '/index.html';
                  }
              }
          } catch (error) {
              console.error('Erreur:', error);
              alert(error.message || 'Erreur lors de la connexion');
          }
      });
  });
