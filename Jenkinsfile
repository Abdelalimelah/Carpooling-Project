pipeline {
    agent any
    tools {
        maven 'Maven-3.9.12' // Asurez-vous que Maven est configure dans Jenkins
        jdk 'JDK-17'
    }
    environment {
        DOCKER_IMAGE = 'tl4trxsh/carpooling-project'
        DOCKER_TAG = 'V2'
        DOCKER_CREDENTIALS_ID = 'dockerhub-creds'
        KUBE_CONFIG_CREDENTIALS_ID = 'kubeconfig'
    }
    stages {
        stage('Clone') { // [cite: 23]
            steps {
                git branch: 'devops_k8s', url: 'https://github.com/Abdelalimelah/Carpooling-Project.git'
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
        stage('Deploy to K8s') {
            steps {
                withCredentials([file(credentialsId: "${KUBE_CONFIG_CREDENTIALS_ID}", variable: 'KUBECONFIG_FILE')]) {
                    bat "kubectl --kubeconfig %KUBECONFIG_FILE% apply -f CarpoolingProject/k8s/"
                    bat "kubectl --kubeconfig %KUBECONFIG_FILE% set image deployment/carpooling-app carpooling=%DOCKER_IMAGE%:%DOCKER_TAG%"
                    bat "kubectl --kubeconfig %KUBECONFIG_FILE% rollout status deployment/carpooling-app"
                }
            }
        }
    }
}
