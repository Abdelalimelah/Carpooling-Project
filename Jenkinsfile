pipeline {
    agent any
    tools {
        maven 'Maven-3.9.12' // Assurez-vous que Maven est configure dans Jenkins
        jdk 'JDK-17'
    }
    environment {
        DOCKER_IMAGE = 'tl4trxsh/carpooling-project'
        DOCKER_TAG = 'V1'
        DOCKER_CREDENTIALS_ID = 'dockerhub-creds'
    }
    stages {
        stage('Clone') { // [cite: 23]
            steps {
                git branch: 'devops', url: 'https://github.com/Abdelalimelah/Carpooling-Project.git'
            }
        }
        stage('Build & Test') { // [cite: 24, 25]
            steps {
                dir('CarpoolingProject') {
                    bat 'mvn clean verify' // Compile et lance les tests unitaires
                }
            }
        }
        stage('SonarQube Analysis') { // [cite: 27]
            steps {
                dir('CarpoolingProject') {
                    withSonarQubeEnv('SonarQube-Server') { // Nom configure dans Jenkins
                        bat 'mvn sonar:sonar'
                    }
                }
            }
        }
        stage('Docker Build') {
            steps {
                dir('CarpoolingProject') {
                    bat "docker build -t %DOCKER_IMAGE%:%DOCKER_TAG% ."
                }
            }
        }
        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS_ID}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    bat "echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin"
                    bat "docker push %DOCKER_IMAGE%:%DOCKER_TAG%"
                    bat "docker logout"
                }
            }
        }
    }
}
