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
                    npm install
                    npx ng build --configuration production
                '''
            }
        }

        stage('code-deploy') {
            steps {
                withCredentials([[
                    $class: 'AmazonWebServicesCredentialsBinding',
                    credentialsId: 'aws_creds',
                    accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                    secretKeyVariable: 'AWS_SECRET_ACCESS_KEY'
                ]]) {
                    sh '''
                        aws s3 cp --recursive dist/angular-frontend s3://767828727604/
                    '''
                }
            }
        }
    }
}
