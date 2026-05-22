# SonarQube

## The Need

Code quality analysis is essential in a modern software development project.

In a CI/CD pipeline, developers need an automated solution capable of:

- Detecting code smells and duplicated code.
- Identifying security vulnerabilities.
- Enforcing coding standards.
- Providing quality reports after each build.
- Integrating with Jenkins for continuous inspection.

To solve this problem, we integrated SonarQube with Jenkins using Docker containers.

## The Concepts

SonarQube is a platform used for continuous inspection of source code quality.

It analyzes projects and provides reports about:

- Bugs: Errors in the code that can cause the application to crash or behave incorrectly.
- Vulnerabilities: Security weaknesses that can be exploited by attackers.
- Code smells: Bad coding practices that make the code harder to read or maintain.
- Duplicated code: Repeated code blocks that reduce maintainability and increase risk of errors.
- Test coverage: The percentage of code that is executed by automated tests.
- Maintainability: How easy it is to understand, modify, and improve the code over time.

**Key concept - Quality Gate**: A set of rules that the code must pass before it can be merged or deployed. 
For example, we can configure a Quality Gate to require a minimum test coverage of 80%.

- If the test coverage is 80% or higher, the Quality Gate passes.
- If the test coverage is below 80%, the Quality Gate fails, and the pipeline stops.

## SonarQube installation using Docker
To perform a clean installation of SonarQube, we followed these steps:

### Java and docker installation

before the sonarqube installation, we installed OpenJDK 17 and docker 
```bash
sudo apt update
sudo apt install -y openjdk-17-jdk
sudo apt install -y docker.io docker-compose-plugin
sudo systemctl enable --now docker 
sudo usermod -aG docker $USER
```
### Opening Firewall Port for SonarQube

To access SonarQube from the web browser, we need to allow traffic through the firewall on port 9000.

We added a new firewall rule using the following command:

```bash
sudo ufw allow 9000
```

Explanation: This command opens port 9000 so that we can connect to the SonarQube web interface from our browser.

### Pull SonarQube Image

```bash
sudo docker pull sonarqube:latest
```

### Run SonarQube Container
```bash
sudo docker run -d \
  --name sonarqube \
  -p 9000:9000 \
  -v ~/sonarqube/data:/opt/sonarqube/data \
  -v ~/sonarqube/logs:/opt/sonarqube/logs \
  sonarqube:latest
```

Explanation:
-d: Detached mode
-- name: name of the container
-p 9000:9000: Maps container port 9000 to host
-v ...: Persistent volumes (data won't be lost when container restarts)
sonarqube:Latest: Latest Edition

### Verify Container Status`
```bash
sudo docker ps
```

### Access SonarQube
Open browser:

http://192.168.56.22:9000

Default credentials:

Username: admin
Password: admin

and we can check logs with:
```bash
sudo docker logs -f sonarqube
```
Shows the last logs and continues to show new logs in real-time

## SonarQube analysis with jenkins

We have generated a new token which will be used later in the configuration with Jenkins.
so we navigate to my account -> Security -> tokens -> generate new token.

We created a new project in SonarQube with the following information:

- Project Key: app-deploy-project
- Project Name: app-deploy-project

This project is later used in the Jenkins pipeline so Jenkins can send the source code analysis results to SonarQube and monitor the code quality automatically during the CI/CD process.

This is the Jenkinsfile SonarQube stage :

```bash
        stage('SonarQube Analysis') {
            steps {
                echo 'Analyse qualité...'
                withSonarQubeEnv('sonarqube') {
                    dir('deployment-app-back') {         
                        sh '''
                            mvn sonar:sonar \
                                -Dsonar.projectKey=com.socialnetwork:app-deploy-project \
                                -Dsonar.projectName=app-deploy-project \
                                -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
                                -Dsonar.qualitygate.wait=false
                        '''
                    }
                }
            }
```

Explanation:
- withSonarQubeEnv('sonarqube'): This connects Jenkins to the configured SonarQube environment named "sonarqube"
- dir('deployment-app-back'): This tells Jenkins to move into the folder deployment-app-back, where the backend project source code is located.
- sh ''': This starts a shell script block, meaning Jenkins will execute Linux commands.
- mvn sonar:sonar: Runs the SonarQube analysis using Apache Maven.
- -Dsonar.projectKey=com.socialnetwork:app-deploy-project: Defines the unique identifier of the project in SonarQube.
- -Dsonar.projectName=app-deploy-project: Defines the displayed project name inside SonarQube.
- -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml: Sends the test coverage report generated by JaCoCo to SonarQube.
- -Dsonar.qualitygate.wait=false: Tells jenkins not to wait for the quality gate result before continuing the pipeline execution.