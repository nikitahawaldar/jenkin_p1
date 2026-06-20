pipeline {
    agent { label 'Jenkins-Agent' }

    tools {
        maven 'Maven3'
        jdk 'JAVA21'
    }
 environment {
    APP_NAME = "register-app-pipeline"
    RELEASE = "1.0.0"
    DOCKER_USER = "nikitahawaldar"
    DOCKER_PASS = "dockerhub"
    IMAGE_NAME = "${DOCKER_USER}/${APP_NAME}"
    IMAGE_TAG = "${RELEASE}-${BUILD_NUMBER}"
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
                withSonarQubeEnv('SonarQube-service') {
                    sh '''
                        mvn sonar:sonar \
                        -Dsonar.projectKey=jenkin_p1 \
                        -Dsonar.projectName=jenkin_p1
                    '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
         stage('Build & Push Docker Image') {
    steps {
        script {

            docker.withRegistry('https://index.docker.io/v1/', 'dockerhub') {

                def dockerImage = docker.build("${IMAGE_NAME}:${IMAGE_TAG}")

                dockerImage.push("${IMAGE_TAG}")
                dockerImage.push("latest")
            }
        }
    }
}
          stage("Trivy Scan") {
              steps {
                  script {
                      sh ('docker run -v /var/run/docker.sock:/var/run/docker.sock aquasec/trivy image nikitahawaldar/register-app-pipeline:latest --no-progress --scanners vuln  --exit-code 0 --severity HIGH,CRITICAL --format table')
               }
           }
       }
       stage ('Cleanup Artifacts') {
           steps {
               script {
                    sh "docker rmi ${IMAGE_NAME}:${IMAGE_TAG}"
                    sh "docker rmi ${IMAGE_NAME}:latest"
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
