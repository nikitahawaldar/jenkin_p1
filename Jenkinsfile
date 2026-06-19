pipeline {
agent { label 'Jenkins-Agent' }

stages {
    stage('Build') {
        steps {
            echo 'Building Application'
        }
    }

    stage('Test') {
        steps {
            echo 'Testing Application'
        }
    }

    stage('Archive Artifacts') {
        steps {
            sh 'mkdir -p target'
            sh 'echo "Build Successful" > target/result.txt'
            archiveArtifacts artifacts: 'target/*', fingerprint: true
        }
    }
}

}
