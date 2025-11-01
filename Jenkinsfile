pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'Java17'
    }

    environment {
        AWS_DEFAULT_REGION = 'ap-south-1'
        S3_BUCKET = 'my-war-artifacts-bucket'
        DOCKER_IMAGE = 'colorful-webapp'
        DOCKER_TAG = "latest"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/your-username/colorful-webapp.git'
            }
        }

        stage('Build WAR') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .'
            }
        }

        stage('Push to Docker Hub') {
            environment {
                DOCKER_HUB_CREDENTIALS = credentials('dockerhub-creds') // Jenkins creds ID
            }
            steps {
                sh '''
                echo "${DOCKER_HUB_CREDENTIALS_PSW}" | docker login -u "${DOCKER_HUB_CREDENTIALS_USR}" --password-stdin
                docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} yourdockerhubusername/${DOCKER_IMAGE}:${DOCKER_TAG}
                docker push yourdockerhubusername/${DOCKER_IMAGE}:${DOCKER_TAG}
                '''
            }
        }

        stage('Upload WAR to S3') {
            steps {
                withAWS(credentials: 'aws-s3-credentials', region: "${AWS_DEFAULT_REGION}") {
                    s3Upload(
                        bucket: "${S3_BUCKET}",
                        includePathPattern: 'target/*.war',
                        workingDir: '.'
                    )
                }
            }
        }

        stage('Run Container (Optional)') {
            steps {
                sh '''
                docker stop colorful-webapp || true
                docker rm colorful-webapp || true
                docker run -d -p 8083:8083 --name colorful-webapp ${DOCKER_IMAGE}:${DOCKER_TAG}
                '''
            }
        }
    }
}
