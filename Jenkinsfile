pipeline {
    agent any

    environment {
        GRADLE_HOME = tool 'gradle-8.5'
        PATH = "$GRADLE_HOME/bin:$PATH"
    }

    stages {
        stage('Build') {
            steps {
                script {
                    // Compilar el proyecto con Gradle y generar el archivo JAR
                    sh 'gradle clean build'
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    // Ejecutar las pruebas con Gradle
                    sh 'gradle test'
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    // Desplegar la aplicación Spring Boot
                    sh 'java -jar build/libs/demo-0.0.1-SNAPSHOT.jar'
                }
            }
        }
    }
}
