pipeline {
    agent any

    tools {
        nodejs 'NodeJS'
    }

    environment {
        REPO_URL = 'https://github.com/Nassima97/API.git'
        COLLECTION_PATH = 'Exo2.postman_collection.json' 
        DATAVAR_PATH = 'create_json.json'
        REPORT_DIR = 'newman-reports'
    }

    stages {
        stage('Checkout Code') {
            steps {
                echo 'Cloning the repository...'
                git branch: 'main', url: "${REPO_URL}"
            }
        }

        stage('Install Dependencies') {
            steps {
                echo 'Installing Newman and reporters...'
                bat 'npm install -g newman'
                bat 'npm install -g newman-reporter-htmlextra'
            }
        }

        stage('Run Newman Collection') {
            steps {
                script {
                    def newmanCommand = "newman run ${COLLECTION_PATH} --reporters=cli,htmlextra --reporter-htmlextra-export ${REPORT_DIR}/newman-report.html"
                    
                    if (fileExists(DATAVAR_PATH)) {
                        echo "Data file found. Including: ${DATAVAR_PATH}"
                        newmanCommand += " -d ${DATAVAR_PATH}"
                    } else {
                        echo "Data file not found. Skipping."
                    }
                    
                    echo "Executing Newman command..."
                    bat newmanCommand
                }
            }
        }

        stage('Publish Newman Report') {
            steps {
                publishHTML(target: [
                    reportName : 'Newman Test Report',
                    reportDir  : "${REPORT_DIR}",
                    reportFiles: 'newman-report.html',
                    keepAll    : true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: false
                ])
            }
        }
    }

    post {
        always {
            echo 'Pipeline terminé. Consultez la console pour les détails.'
        }
        success {
            echo 'Tests exécutés avec succès. Rapport généré.'
        }
        failure {
            echo 'Échec dans lexécution de la pipeline ou des tests Newman. Vérifiez les logs.'
        }
    }
}