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
window.onload = function() {
    axios.get('/videos')
        .then(function(response) {
            const videos = response.data;
            const videoListContainer = document.querySelector('.video-list');
            const mainVideo = document.querySelector('.main-video video');
            const title = document.querySelector('.main-video .title');

            if (videos.length > 0) {
                mainVideo.src = `videos/${videos[0]}`;
                title.innerHTML = formatTitle(videos[0]);
            }

            videos.forEach((video, index) => {
                const videoElement = `
                    <div class="vid${index === 0 ? ' active' : ''}">
                        <video src="videos/${video}" muted></video>
                        <h3 class="title">${formatTitle(video)}</h3>
                    </div>
                `;
                videoListContainer.innerHTML += videoElement;
            });

            setupVideoSwitching();
        })
        .catch(function(error) {
            console.log('Error loading videos:', error);
        });
};

function setupVideoSwitching() {
    let listvideo = document.querySelectorAll('.video-list .vid');
    let mainVideo = document.querySelector('.main-video video');
    let title = document.querySelector('.main-video .title');

    listvideo.forEach(video => {
        video.onclick = () => {
            listvideo.forEach(vid => vid.classList.remove('active'));
            video.classList.add('active');
            if(video.classList.contains('active')){
                let src = video.children[0].getAttribute('src');
                mainVideo.src = src;
                let text = video.children[1].innerHTML;
                title.innerHTML = text;
            }
        };
    });
}

function formatTitle(videoFileName) {
    // Remove the file extension and replace underscores with spaces
    return videoFileName.replace(/\.mp4$/i, '').replace(/_/g, ' ');
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
