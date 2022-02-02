#!/usr/bin/env groovy

pipeline {
    agent {
        label 'default-agent-us'
    }
    options {
        timestamps ()
        skipDefaultCheckout(true)
    }
    environment {
        GITLEAKS_IMAGE = "ghcr.io/zricethezav/gitleaks:latest"

    }
    stages {
        stage('Build') {
            steps {
                deleteDir()
                checkout scm
                echo "Building ${env.JOB_NAME}..."
            }
        }
        stage("Run Gitleaks"){
            steps {
                script {
                    try {
                        sh "docker pull ${env.GITLEAKS_IMAGE}"
                        sh "docker run --rm -v ${WORKSPACE}:/gitleaks \
                                ${env.GITLEAKS_IMAGE} detect \
                                --source=../../src  \
                                -v --no-git \
                                --config='../../src/.gitleaks.toml' \
                                --report-path=/gitleaks/output/gitleaks/gitleaks_${BUILD_NUMBER}_report.json"
                    }catch(Exception ex) {
                        echo "ERROR: Scan Failed"
                    }
                sh "docker rm -f gitleaks || true"
                }
            }
        }
        stage("Zip Artifacts") {
            steps {
                zip zipFile: "gitleaks_scan_build_${BUILD_NUMBER}.zip", dir: "${WORKSPACE}", archive: true
            }
        }
    }
}