pipeline {
    agent any
    tools {
        maven 'Maven-3.9.12'
        jdk 'JDK-17'
    }
    stages {
        stage('Clone') {
            steps {
                git branch: 'master', url: 'https://github.com/Abdelalimelah/Carpooling-Project.git'
            }
        }
        stage('Build & Test') {
            steps {
                bat 'mvn clean verify'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube-Server') {
                    bat 'mvn sonar:sonar'
                }
            }
        }
    }
}
