// api.js

// Fonctions pour les requêtes API de connexion
async function loginUser(email, password) {
  try {
      console.log('Attempting to login with:', email); // Debug log
      const response = await fetch('http://localhost:3000/api/login', { // Changé pour 3000
          method: 'POST',
          headers: {
              'Content-Type': 'application/json',
          },
          body: JSON.stringify({
              email: email,
              password: password
          }),
          credentials: 'include'
      });

      const data = await response.json();
      console.log('Server response:', data); // Debug log
      
      if (!response.ok) {
          throw new Error(data.message || 'Erreur lors de la connexion');
      }

      return data;
  } catch (error) {
      console.error('Erreur:', error);
      throw error;
  }


}

// Fonction pour deconnexion
async function logoutUser() {
  try {
    const response = await fetch('http://localhost:3000/api/logout', {
      method: 'POST',
      credentials: 'include'
    });
    const data = await response.json();
    
    if (!response.ok) {
      throw new Error(data.message || 'Erreur lors de la déconnexion');
    }
    return data;
  } catch (error) {
    console.error('Erreur:', error);
    throw error;
  }
}


// Fonction pour l'insctiption
async function registerUser(fullName, dob, email, password, address, userType, cityId = null, intervenantType = null) {
  try {
    const response = await fetch('http://localhost:3000/api/users', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        fullName,
        dob,
        email,
        password,
        address,
        userType,
        cityId,
        intervenantType,
      }),
      credentials: 'include',
    });

    const data = await response.json();

    if (!response.ok) {
      throw new Error(data.message || 'Erreur lors de la création du compte');
    }

    return data;
  } catch (error) {
    console.error('Erreur:', error);
    throw error;
  }
}



async function updatePreferences(email, day, start, end, status) {
  try {
      const response = await fetch(`http://localhost:3000/api/users/${encodeURIComponent(email)}/preferences`, {
          method: 'PUT',
          headers: {
              'Content-Type': 'application/json',
          },
          body: JSON.stringify({ day, start, end, status }), // `day` est maintenant un entier
          credentials: 'include'
      });

      const data = await response.json();
      
      if (!response.ok) {
          throw new Error(data.message || 'Erreur lors de la mise à jour des préférences');
      }

      return data;
  } catch (error) {
      console.error('Erreur:', error);
      throw error;
  }
}



async function getUserNotifications(email) {
  try {
    const response = await fetch(`http://localhost:3000/api/users/${email}/notifications`, {
      method: 'GET',
      credentials: 'include'
    });

    const data = await response.json();
    
    if (!response.ok) {
      throw new Error(data.message || 'Erreur lors de la récupération des notifications');
    }

    return data;
  } catch (error) {
    console.error('Erreur:', error);
    throw error;
  }
}

async function updateNotificationCount() {
  try {
    const email = localStorage.getItem('email'); // Suppose que email est stocké après la connexion
    const response = await getUserNotifications(email);
    const unreadCount = response.notifications.filter(n => !n.read).length;
    if (unreadCount > 0) {
      document.getElementById('new-notifications').textContent = unreadCount;
      document.getElementById('notification-count').style.display = 'block';
    }
  } catch (error) {
    console.error('Erreur lors de la récupération des notifications :', error);
  }
}

document.addEventListener('DOMContentLoaded', function () {
  updateNotificationCount();
});


async function getCurrentProjects() {
  const response = await fetch('http://localhost:3000/api/projects/current', { credentials: 'include' });
  const data = await response.json();
  if (!response.ok) {
    throw new Error(data.message || 'Erreur lors de la récupération des projets en cours');
  }
  return data;
}

async function getUpcomingProjects() {
  const response = await fetch('http://localhost:3000/api/projects/upcoming', { credentials: 'include' });
  const data = await response.json();
  if (!response.ok) {
    throw new Error(data.message || 'Erreur lors de la récupération des projets à venir');
  }
  return data;
}

async function getProjectsByDistrict(district) {
  const url = district ? `http://localhost:3000/api/projects?district=${encodeURIComponent(district)}` : 'http://localhost:3000/api/projects';
  const response = await fetch(url, { credentials: 'include' });
  const data = await response.json();
  if (!response.ok) {
    throw new Error(data.message || 'Erreur lors de la récupération des projets filtrés');
  }
  return data;
}


async function getAllEntraves() {
  const response = await fetch('http://localhost:3000/api/entraves', { credentials: 'include' });
  const data = await response.json();
  if (!response.ok) {
    throw new Error(data.message || 'Erreur lors de la récupération des entraves');
  }
  return data;
}

async function getEntravesByStreet(street) {
  const url = street ? `http://localhost:3000/api/entraves?street=${encodeURIComponent(street)}` : 'http://localhost:3000/api/entraves';
  const response = await fetch(url, { credentials: 'include' });
  const data = await response.json();
  if (!response.ok) {
    throw new Error(data.message || 'Erreur lors de la récupération des entraves filtrées');
  }
  return data;
}


document.addEventListener('DOMContentLoaded', async function() {
  const requestsContainer = document.getElementById('requests-container');
  const email = localStorage.getItem('email');

  if (!email) {
      requestsContainer.innerHTML = '<div class="alert alert-warning">Veuillez vous connecter pour voir vos requêtes.</div>';
      return;
  }

  try {
      // Récupération des requêtes du résident
      const response = await fetch(`http://localhost:3000/api/requests/by-resident?email=${encodeURIComponent(email)}`, {
          credentials: 'include'
      });
      const data = await response.json();

      if (!response.ok) {
          throw new Error(data.message || "Erreur lors de la récupération des requêtes.");
      }

      const requests = data.requests;
      if (!requests || requests.length === 0) {
          requestsContainer.innerHTML = '<p>Aucune requête trouvée.</p>';
          return;
      }


    // Afficher chaque requête
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

      // Affichage des intervenants ayant postulé
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

      
      // Si aucune candidature choisie et des candidats existent  bouton pour choisir
      if (!req.chosenIntervenant && candidats.length > 0 && !req.closed) {
        content += `
          <div class="mb-2">
            <label for="choose-${req.id}" class="form-label">Choisir un intervenant :</label>
            <select id="choose-${req.id}" class="form-select form-select-sm mb-2">
              <option value="">Sélectionnez un intervenant</option>
              ${candidats.map(inter => `<option value="${inter.mail}">${inter.mail}</option>`).join('')}
            </select>
            <button class="btn btn-sm btn-success" onclick="chooseCandidate(${req.id})">Choisir cet intervenant</button>
          </div>`;
      }

      // Si un intervenant est choisi mais pas encore confirmé  on affiche un message d'attente
      if (req.chosenIntervenant && !req.confirmed && !req.closed) {
        content += `<p><em>En attente de confirmation de l'intervenant choisi (${req.chosenIntervenant.mail}).</em></p>`;
      }

      // Si un intervenant est confirmé et la requête n'est pas fermée  bouton pour fermer la requête
      if (req.chosenIntervenant && req.confirmed && !req.closed) {
        content += `<button class="btn btn-sm btn-dark" onclick="closeRequest(${req.id})">Fermer la requête</button>`;
      }

      li.innerHTML = content;
      ul.appendChild(li);
    });

    requestsContainer.appendChild(ul);

  } catch (error) {
    requestsContainer.innerHTML = `<div class="alert alert-danger">${error.message}</div>`;
  }
});

// Fonction pour choisir un intervenant
async function chooseCandidate(requestId) {
  const select = document.getElementById(`choose-${requestId}`);
  const emailResident = localStorage.getItem('email');
  const emailIntervenant = select.value;
  if (!emailIntervenant) {
    alert('Veuillez sélectionner un intervenant.');
    return;
  }

  const payload = {
    emailResident: emailResident,
    emailIntervenant: emailIntervenant,
    messageOptionnel: "" // optionnel
  };

  try {
    const response = await fetch(`http://localhost:3000/api/requests/${requestId}/choose`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
      body: JSON.stringify(payload)
    });

    const data = await response.json();
    if (!response.ok) {
      throw new Error(data.message || 'Erreur lors du choix de la candidature');
    }

    alert('Candidature choisie avec succès !');
    window.location.reload();
  } catch (error) {
    alert(error.message);
  }
}

// Fonction pour fermer la requête
async function closeRequest(requestId) {
  const emailResident = localStorage.getItem('email');
  const payload = { emailResident };

  try {
    const response = await fetch(`http://localhost:3000/api/requests/${requestId}/close`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
      body: JSON.stringify(payload)
    });

    const data = await response.json();
    if (!response.ok) {
      throw new Error(data.message || 'Erreur lors de la fermeture de la requête');
    }

    alert('Requête fermée avec succès !');
    window.location.reload();
  } catch (error) {
    alert(error.message);
  }
}


async function applyRequest(requestId, emailIntervenant) {
  try {
    const response = await fetch(`http://localhost:3000/api/requests/${requestId}/apply`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
      body: JSON.stringify({ email: emailIntervenant })
    });

    const data = await response.json();
    if (!response.ok) {
      throw new Error(data.message || 'Erreur lors de la postulation');
    }

    alert('Candidature soumise avec succès !');
    window.location.reload();
  } catch (error) {
    alert(error.message);
  }
}

async function removeCandidature(requestId, emailIntervenant) {
  try {
    const response = await fetch(`http://localhost:3000/api/requests/${requestId}/apply`, {
      method: 'DELETE',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
      body: JSON.stringify({ email: emailIntervenant })
    });

    const data = await response.json();
    if (!response.ok) {
      throw new Error(data.message || 'Erreur lors du retrait de la candidature');
    }

    alert('Candidature retirée avec succès !');
    window.location.reload();
  } catch (error) {
    alert(error.message);
  }
}

document.addEventListener('DOMContentLoaded', function() {
  const form = document.getElementById('update-project-form');
  
  form.addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const projectId = document.getElementById('project-id').value;
    const emailIntervenant = document.getElementById('emailIntervenant').value.trim();
    const newStatus = document.getElementById('new-status').value;

    if (!projectId || !emailIntervenant || !newStatus) {
      alert('Tous les champs sont obligatoires.');
      return;
    }

    try {
      const response = await fetch(`http://localhost:3000/api/projects/${projectId}/status`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify({ emailIntervenant, newStatus })
      });

      const data = await response.json();
      if (!response.ok) {
        throw new Error(data.message || 'Erreur lors de la mise à jour du projet.');
      }

      alert('Statut mis à jour avec succès !');
      form.reset();
    } catch (error) {
      alert(error.message);
    }
  });
});



async function testConnection() {
  try {
      const response = await fetch('http://localhost:3000/api/test', {
          method: 'GET'
      });
      const data = await response.text();
      console.log('Test response:', data);
      return data;
  } catch (error) {
      console.error('Test error:', error);
      throw error;
  }
}