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
                    dir('deployment-app-back') {
                        sh 'mvn sonar:sonar -DskipTests'
                    }
                }
            }
        }

        stage('Quality Gate') {
        steps {
            script {
                // Give SonarQube a little time to mark the task as done
                sleep(time: 20, unit: 'SECONDS')
            
                timeout(time: 15, unit: 'MINUTES') {
                    def qg = waitForQualityGate()
                    if (qg.status != 'OK' && qg.status != 'WARN') {
                        echo "Quality Gate failed: ${qg.status}"
                        error "Pipeline aborted due to quality gate failure: ${qg.status}"
                    } else {
                        echo "Quality Gate passed: ${qg.status}"
                }
            }
        }
    }
}
    }
}
