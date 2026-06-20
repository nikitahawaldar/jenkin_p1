pipeline {
    agent { label 'Jenkins-Agent' }

    tools {
        maven 'Maven3'
        jdk 'Java17'
    }

    stages {

        stage('Build') {
            steps {
                echo 'Building Application'
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Running Tests'
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging Application'
                sh 'mvn package'
            }
        }

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 
                    mvn sonar:sonar \
                    -Dsonar.projectKey=jenkin_p1 \
                    -Dsonar.projectName=jenkin_p1
                    
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline Executed Successfully'
        }

        failure {
            echo 'Pipeline Failed'
        }
    }
}
