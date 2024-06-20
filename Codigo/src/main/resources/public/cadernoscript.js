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


document.getElementById('uploadForm').addEventListener('submit', function(event) {
       event.preventDefault();
       var formData = new FormData();
       formData.append('image', document.getElementById('imageInput').files[0]);

       fetch('/api/recognize-text', {
           method: 'POST',
           body: formData
       })
       .then(response => response.text())
       .then(text => {
        document.getElementById('resultText').innerHTML = text; // Mostra o resultado
        document.getElementById('comment_box').value = text; // Insere automaticamente no comentário
    })
       .catch(error => console.error('Error:', error));
   });