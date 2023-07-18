pipeline {
    agent any

    // 存放所有任务的合集
    stages {
        stage('拉取Git仓库代码') {
            steps {
                checkout scmGit(branches: [[name: '${tag}']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/stonebridgelin/jenkinstest.git']])
                echo '拉取代码----success'
            }
        }
        
        stage('通过Maven构建项目') {
            steps {
                sh '/var/jenkins_home/apache-maven-3.6.3/bin/mvn clean package -DskipTests'
                echo '构建项目----success'
            }
        }

        stage('通过SonarQube检测代码') {
            steps {
                echo '检测代码----success'
            }
        }


        stage('通过Docker制作自定义镜像') {
            steps {
                sh '''mv target/*.jar docker/
                docker build -t ${JOB_NAME}:$tag docker/'''
                echo '通过Docker制作自定义镜像----success'
            }
        }
        
        stage('将自定义镜像发布到Harbor') {
            steps {
                echo '将自定义镜像发布到Harbor----success'
            }
        }

        stage('通过Pushlish Over SSH通知测试服务器部署项目') {
            steps {
                echo '通过Pushlish Over SSH通知测试服务器部署项目----success'
            }
        }
    }
}
