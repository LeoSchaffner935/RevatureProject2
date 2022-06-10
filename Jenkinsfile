pipeline {
    agent any

    stages {
        stage('Clone Repo') {
            steps{
                git 'https://github.com/LeoSchaffner935/RevatureProject2.git'
            }
        }

        stage('Build App') {
            steps {
                bat 'mvn package -DskipTests'
            }
        }

        stage('Build Image') {
            script {
                dockerImage = docker.build('leoschaffner935/revatureproject2:latest')
            }
        }

        stage('Deploy') {
            script {
                docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
                    dockerImage.push('latest')
                }
            }
        }
    }
}