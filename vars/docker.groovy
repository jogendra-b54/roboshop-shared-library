def  call(){
    node('WS') {
        sh "rm -rf *"
        git branch: 'main', url: "https://github.com/jogendra-b54/${COMPONENT}.git"
        env.APP_TYPE="nodejs"
        common.lintchecks() 
        if(env.TAG_NAME != null){
              stage('Prepare Artifacts'){
                    if(env.APP_TYPE == "nodejs"){
                    sh '''
                         echo preparing the Artifacts for ${COMPONENT}
                         npm install
                    '''
                }
                else if(env.APP_TYPE == "python"){
                    sh '''
                            echo Preparing Artifacts for ${COMPONENT}
                            zip -r ${COMPONENT}-${TAG_NAME}.zip *.py *.ini requirements.txt
                    '''
                }
                 else if(env.APP_TYPE == "java"){
                    sh '''
                            echo Preparing Artifacts for ${COMPONENT}
                            mvn clean package
                            ls -ltr
                    '''
                }
                 else {
                    sh '''
                            echo Preparing Artifacts for ${COMPONENT}
                            cd static
                            zip -r ../${COMPONENT}-${TAG_NAME}.zip * 
                    '''
                }
                sh "env"
                sh "ls -ltr"
                sh "https://truststore.pki.rds.amazonaws.com/global/global-bundle.pem"
                sh "docker build -t 164910480407.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:latest ."
                sh "docker tag 164910480407.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:latest  164910480407.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:${TAG_NAME}"
                sh "aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 164910480407.dkr.ecr.us-east-1.amazonaws.com"
                sh "docker push 164910480407.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:${TAG_NAME}"
                

            }
        }
    }
}
 