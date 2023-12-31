pipeline {
    agent any

    environment{
        harborHost = '47.251.53.131:8090'
        harborRepo = 'repo'
        harborUser = 'admin'
        harborPasswd = 'Harbor12345'
    }

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
                 sh '''docker login -u ${harborUser} -p ${harborPasswd} ${harborHost}
                 docker tag ${JOB_NAME}:${tag} ${harborHost}/${harborRepo}/${JOB_NAME}:${tag}
                 docker push ${harborHost}/${harborRepo}/${JOB_NAME}:${tag}'''
                 echo '${harborRepo}----success'
             }
        }

        stage('通过Pushlish Over SSH通知测试服务器部署项目') {
            steps {
                sshPublisher(publishers: [sshPublisherDesc(configName: 'Jenkins_Test_On_Aliyun_server', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: "deploy.sh $harborHost  $harborRepo  $JOB_NAME $tag 8080 9000", execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '', remoteDirectorySDF: false, removePrefix: '', sourceFiles: '')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
                echo '通过Pushlish Over SSH通知测试服务器部署项目----success'
            }
        }
    }
}
