pipeline {
    agent any
    
    tools {
        jdk 'jdk17'
        maven 'maven'
        nodejs 'nodejs'
    }

    stages {
        stage('Debug Java Version') {
            steps {
                sh '''
                    echo "=== JAVA_HOME ==="
                    echo $JAVA_HOME
                    echo "=== Java Version ==="
                    java -version
                    echo "=== Maven Version ==="
                    mvn -version
                '''
            }
        }

        stage('Build Backend') {
            steps {
                dir('deployment-app-back') {
                    sh 'mvn clean package'
                }
            }
        }

        stage('Build Frontend') {
            steps {
                dir('deployment-app-front') {
                    sh 'npm ci'
                    sh 'npm run build'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo 'Analyse qualité...'
                withSonarQubeEnv('sonarqube') {
                    dir('deployment-app-back') {         
                        sh '''
                            mvn sonar:sonar \
                                -Dsonar.projectKey=com.socialnetwork:app-deploy-project \
                                -Dsonar.projectName=app-deploy-project \
                                -Dsonar.qualitygate.wait=false
                        '''
                    }
                }
            }
        }
    }
}
