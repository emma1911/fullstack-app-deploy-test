pipeline {
    agent any

    tools {
        nodejs 'nodejs'
        maven 'maven'
    }

    stages {
        stage('Declarative: Checkout SCM') {
            steps {
                checkout scm 
            }
        }

        stage('Clone Project') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/emma1911/fullstack-app-deploy-test.git'
            }
        }

        stage('Build Backend') {
            steps {
                dir('deployment-app-back') {
                    sh 'mvn clean package -DskipTests'
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
                withSonarQubeEnv('sonarqube') {
                    dir('backend') {
                        sh 'mvn sonar:sonar -DskipTests'
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                waitForQualityGate abortPipeline: true
            }
        }

        stage('Upload to Nexus') {
            steps {
                echo 'Uploading to Nexus...'
            }
        }
    }
}
