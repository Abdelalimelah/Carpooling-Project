pipeline {
    agent any
    tools {
        maven 'Maven-3.9.12' // Assurez-vous que Maven est configuré dans Jenkins
        jdk 'JDK-17'
    }
    stages {
        stage('Clone') { // [cite: 23]
            steps {
                git branch: 'master', url: 'https://github.com/Abdelalimelah/Carpooling-Project.git'
            }
        }
        stage('Build & Test') { // [cite: 24, 25]
            dir('CarpoolingProject') {
                steps {
                    bat 'mvn clean package' // Compile et lance les tests unitaires
                }
            }
        }
        stage('SonarQube Analysis') { // [cite: 27]
            steps {
                dir('CarpoolingProject') {
                    withSonarQubeEnv('SonarQube-Server') { // Nom configuré dans Jenkins
                        bat 'mvn sonar:sonar'
                    }
                }
            }
        }
    }
}