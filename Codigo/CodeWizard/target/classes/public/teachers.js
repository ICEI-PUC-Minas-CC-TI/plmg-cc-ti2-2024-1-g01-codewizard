let toggleBtn = document.getElementById('toggle-btn');
let body = document.body;
let darkMode = localStorage.getItem('dark-mode');

const enableDarkMode = () =>{
   toggleBtn.classList.replace('fa-sun', 'fa-moon');
   body.classList.add('dark');
   localStorage.setItem('dark-mode', 'enabled');
}

const disableDarkMode = () =>{
   toggleBtn.classList.replace('fa-moon', 'fa-sun');
   body.classList.remove('dark');
   localStorage.setItem('dark-mode', 'disabled');
}

if(darkMode === 'enabled'){
   enableDarkMode();
}

toggleBtn.onclick = (e) =>{
   darkMode = localStorage.getItem('dark-mode');
   if(darkMode === 'disabled'){
      enableDarkMode();
   }else{
      disableDarkMode();
   }
}

let profile = document.querySelector('.header .flex .profile');

document.querySelector('#user-btn').onclick = () =>{
   profile.classList.toggle('active');
   search.classList.remove('active');
}

let search = document.querySelector('.header .flex .search-form');

document.querySelector('#search-btn').onclick = () =>{
   search.classList.toggle('active');
   profile.classList.remove('active');
}

let sideBar = document.querySelector('.side-bar');

document.querySelector('#menu-btn').onclick = () =>{
   sideBar.classList.toggle('active');
   body.classList.toggle('active');
}

document.querySelector('#close-btn').onclick = () =>{
   sideBar.classList.remove('active');
   body.classList.remove('active');
}

window.onscroll = () =>{
   profile.classList.remove('active');
   search.classList.remove('active');

   if(window.innerWidth < 1200){
      sideBar.classList.remove('active');
      body.classList.remove('active');
   }
}
// Array com URLs de imagens para escolher aleatoriamente
const imageUrls = [
    "images/pic-2.jpg",
    "images/pic-3.jpg",
    "images/pic-4.jpg",
    "images/pic-6.jpg"
];

function getRandomImage() {
    return imageUrls[Math.floor(Math.random() * imageUrls.length)];
}

function loadProfessors() {
    axios.get('http://localhost:4567/professors')
        .then(function(response) {
            const professors = response.data;
            const container = document.getElementById('professor-container');
            professors.forEach(professor => {
                const randomImage = getRandomImage();
                const div = document.createElement('div');
                div.classList.add('box');
                div.innerHTML = `
                    <div class="tutor">
                        <img src="${randomImage}" alt="Foto de ${professor.username}">
                        <div>
                            <h3>${professor.username}</h3>
                            <span>${professor.email}</span>
                        </div>
                    </div>
                    <p>total de playlists : <span>${Math.floor(Math.random() * 10) + 1}</span></p>
                    <p>total de vídeos : <span>${Math.floor(Math.random() * 50) + 10}</span></p>
                    <p>total de likes : <span>${Math.floor(Math.random() * 5000) + 1000}</span></p>
                    <a href="home.html" class="inline-btn">ver perfil</a>
                `;
                container.appendChild(div);
            });
        })
        .catch(function(error) {
            console.error('Error loading professors:', error);
        });
}

window.onload = loadProfessors;
document.addEventListener('DOMContentLoaded', function() {
    axios.get('/user-info')
        .then(function(response) {
            if (response.status === 200) {
                const userData = response.data;
                document.getElementById('userName').textContent = userData.name;
                document.getElementById('userRole').textContent = userData.role;
            } else {
                console.error('Failed to load user data:', response.status);
            }
        })
        .catch(function(error) {
            console.error('Error fetching user info:', error);
        });
});
document.addEventListener('DOMContentLoaded', function() {
    axios.get('/user-info')
        .then(function(response) {
            if (response.status === 200) {
                const userData = response.data;
                document.getElementById('username').textContent = userData.name;
                document.getElementById('userrole').textContent = userData.role;
                 // Mostrar o botão 'Enviar Pergunta' apenas para professores
                if (userData.role === 'professor') {
                    const sendQuestionBtn = document.getElementById('send-question-btn');
                    sendQuestionBtn.style.display = 'inline-block';
                    sendQuestionBtn.addEventListener('click', function() {
                        window.location.href = 'enviapergunta.html'; // Redireciona para a página de envio de perguntas
                    });
                }
            } else {
                console.error('Failed to load user data:', response.status);
            }
        })
        .catch(function(error) {
            console.error('Error fetching user info:', error);
        });
});

document.addEventListener('DOMContentLoaded', function() {
    var logoutButton = document.getElementById('logout-btn');
    if (logoutButton) {
        logoutButton.addEventListener('click', function() {
            // Enviar requisição para o endpoint de logout
            window.location.href = '/logout'; // Simples redirecionamento para a rota de logout
        });
    }
});