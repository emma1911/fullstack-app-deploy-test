pipeline {

    agent any

    tools {
        nodejs 'nodejs'
    }

    stages {

        stage('Clone Project') {
            steps {
                git 'https://github.com/USERNAME/fullstack-app-deploy-test.git'
            }
        }

        stage('Build Backend') {
            steps {
                dir('backend') {
                    sh 'mvn clean package'
                }
            }
        }

        stage('Build Frontend') {
            steps {
                dir('frontend') {
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    dir('backend') {
                        sh 'mvn sonar:sonar'
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
                echo 'Uploading artifact to Nexus...'
            }
        }
    }
}
