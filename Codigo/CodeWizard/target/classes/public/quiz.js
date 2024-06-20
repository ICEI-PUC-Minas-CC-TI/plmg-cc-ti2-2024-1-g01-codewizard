window.onload = function loadQuestions() {
    axios.get('/questions')
        .then(function(response) {
            const questions = response.data;
            const form = document.getElementById('quiz-form');
            questions.forEach(question => {
                const qElement = document.createElement('div');
                qElement.innerHTML = `
                    <p>${question.content} (Level: ${question.level})</p>
                    <label><input type="radio" name="answer_${question.questionID}" value="true"> Verdadeiro</label>
                    <label><input type="radio" name="answer_${question.questionID}" value="false"> Falso</label>
                `;
                form.appendChild(qElement);
            });
        })
        .catch(function(error) {
            console.log('Error loading questions:', error);
        });
};

function submitAnswers() {
    const form = document.getElementById('quiz-form');
    const formData = new FormData(form);
    const answers = {};
    formData.forEach((value, key) => {
        const questionId = key.split('_')[1];
        answers[questionId] = value;
    });

    axios.post('/submit-answers', answers)
        .then(function(response) {
            alert('Answers submitted successfully!');
        })
        .catch(function(error) {
            console.error('Failed to submit answers:', error);
        });
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

