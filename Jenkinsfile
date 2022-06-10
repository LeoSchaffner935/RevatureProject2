node {
    stage('Clone Repo') {
        git 'https://github.com/LeoSchaffner935/RevatureProject2.git'
    }

    stage('Build Project') {
        bat "mvn package -DskipTests"
    }

    stage('Build Image') {
        dockerImage = docker.build("leoschaffner935/revatureproject2:latest")
    }

    stage('Deploy') {
        docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
            dockerImage.push('latest')
        }
    }
}