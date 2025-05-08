pipeline {
    agent any
    stages {
        stage('code-pull') {
            steps {
                git branch: 'dev', url: 'https://github.com/avigupta63/project-frontnd.git'
            }
        }
        stage('code-build') {
            steps {
                sh '''
                     npm insall
                     ng build
                   '''
            }
        }
      
        stage('code-deploy') {
            steps {
                sh '''
                     aws s3 cp --recursive dist/angular-frontend s3://mywasbucket321/
                   '''
            }
        }
    }
}
