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

let questions = [];
let currentQuestionIndex = 0;

window.onload = function() {
    loadQuestions();
};

function loadQuestions() {
    axios.get('/get-questions')
        .then(function(response) {
            questions = response.data; // Certifique-se de que todas as perguntas estão sendo carregadas aqui
            displayQuestion(); // Exibe a primeira pergunta
        })
        .catch(function(error) {
            console.log('Error fetching questions:', error);
        });
}

function displayQuestion() {
    const questionContainer = document.getElementById('question-container');
    if (currentQuestionIndex < questions.length) {
        const question = questions[currentQuestionIndex];
        questionContainer.innerHTML = `
            <p>${question.content}</p>
            <label class="option-label">
                <input type="radio" name="answer" value="true" id="true${currentQuestionIndex}">
                <span onclick="selectOption('true${currentQuestionIndex}')">Verdadeiro</span>
            </label>
            <label class="option-label">
                <input type="radio" name="answer" value="false" id="false${currentQuestionIndex}">
                <span onclick="selectOption('false${currentQuestionIndex}')">Falso</span>
            </label>
        `;
    } else {
		alert('Você completou todas as perguntas!Acertou 7');
        //questionContainer.innerHTML = '<p>Você completou todas as perguntas!Acertou 7</p>';
        document.getElementById('next-btn').style.display = 'none';
    }
}

function selectOption(id) {
    // Marca o input de rádio como checked e desabilita o botão "Próxima Pergunta"
    document.getElementById(id).checked = true;
    document.getElementById('next-btn').disabled = false;

    // Atualiza as cores dos botões
    const trueBtn = document.getElementById('true' + currentQuestionIndex);
    const falseBtn = document.getElementById('false' + currentQuestionIndex);
    if (trueBtn.checked) {
        trueBtn.nextElementSibling.style.backgroundColor = '#27ae60'; // Verde
        falseBtn.nextElementSibling.style.backgroundColor = '#8e44ad'; // Roxo
    } else {
        trueBtn.nextElementSibling.style.backgroundColor = '#8e44ad'; // Roxo
        falseBtn.nextElementSibling.style.backgroundColor = '#c0392b'; // Vermelho
    }
}

document.getElementById('next-btn').addEventListener('click', function() {
    if (currentQuestionIndex < questions.length - 1) {
        currentQuestionIndex++;
        displayQuestion();
        this.disabled = true;
    }
});




