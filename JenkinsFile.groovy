pipeline {
    agent any

    environment {
        PATH = "${env.PATH};C:\\Users\\Lenovo\\AppData\\Roaming\\npm"
    }

    stages {
        stage('Cloner le projet depuis GitHub') {
            steps {
                echo 'Clonage du projet depuis GitHub...'
                git branch: 'main',
                    credentialsId: 'github-credentials',
                    url: 'https://github.com/Nassima97/API.git'
            }
        }

        stage('Installer Newman') {
            steps {
                echo 'Installation de Newman...'
                bat 'npm install -g newman'
            }
        }

        stage('Exécuter la collection Postman') {
            steps {
                echo 'Exécution de la collection Postman...'
                bat 'newman run Exo2.postman_collection.json -d create_json.json --disable-unicode --reporter-cli-no-banner'
            }
        }
    }

    post {
        success {
            echo 'Pipeline exécuté avec succès.'
        }
        failure {
            echo 'Le pipeline a échoué.'
        }
    }
}
