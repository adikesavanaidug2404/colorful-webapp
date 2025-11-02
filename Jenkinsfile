pipeline {
    agent any
    
    tools {
        maven 'maven'
        jdk 'java'
    }
    
    environment {
        // Optional if you want to refer to your bucket/profile easily
        S3_BUCKET = 'my-war-artifacts-bucket'
        AWS_PROFILE = 'aws-s3'
    }

    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/adikesavanaidug2404/colorful-webapp.git'
            }
        }
        stage('Build War') {
            steps {
                sh "mvn clean install"
            }
        }
        stage('Upload WAR to S3') {
            steps {
                script {
                    s3Upload(
                        consoleLogLevel: 'INFO',
                        dontSetBuildResultOnFailure: false,
                        dontWaitForConcurrentBuildCompletion: false,
                        entries: [[
                            bucket: "${S3_BUCKET}",
                            excludedFile: '',
                            flatten: false,
                            gzipFiles: false,
                            keepForever: false,
                            managedArtifacts: false,
                            noUploadOnFailure: false,
                            selectedRegion: 'us-east-1',
                            showDirectlyInBrowser: false,
                            sourceFile: 'target/*.war',
                            storageClass: 'STANDARD',
                            uploadFromSlave: false,
                            useServerSideEncryption: false
                        ]],
                        pluginFailureResultConstraint: 'SUCCESS',
                        profileName: "${AWS_PROFILE}",
                        userMetadata: []
                    )
                }
            }
        }
    }

    post {
        success {
            echo "✅ WAR file successfully uploaded to S3 bucket: ${S3_BUCKET}"
        }
        failure {
            echo "❌ Build or upload failed!"
        }
    }
}
