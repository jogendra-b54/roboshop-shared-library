// def sonarchecks()
// {   
//     sh '''
//              echo Sonar Checks In Progress
//              sonar-scanner -Dsonar.login="${SONARCRED_USR}" -Dsonar.password=${SONARCRED_PSW} -Dsonar.host.url=http://172.31.21.58:9000 -Dsonar.projectKey=${COMPONENT} ${ARGS}
//              curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > sonar-quality-gate.sh
//              sonar-quality-gate.sh ${SONARCRED_USR} ${SONARCRED_PSW} ${SONAR_URL} ${COMPONENT}
//              echo Sonar Checks are completed
//     '''

// }
def lintchecks()
{   
    stage('Lint checks'){
        if(env.APP_TYPE == "nodejs" ) {
    sh '''
             echo Installing JSLint for ${COMPONENT}
             npm i jslint
             node_modules/jslint/bin/jslint.js server.js || true
             echo lint checks completed for ${COMPONENT}
     '''
        }
        else if(env.APP_TYPE == "python"){
            sh '''
            # pip3 install pylint
            # pip *.py
            echo lint checks started for ${COMPONENT} using pylint
            echo lint checks completed for ${COMPONENT}
            '''
        }
        else if(env.APP_TYPE == "java") {
            sh '''
            echo lint checks started for ${COMPONENT} using mvn
    
            # mvn checkstyle:check

            echo lint checks completed for ${COMPONENT}

            '''
        }
        else {
            sh '''
            echo lint checks started for ${COMPONENT} using AngualrJS
               echo lint checks completed for ${COMPONENT}

            '''
        }
    }

}
def sonarchecks()
{       
        stage('Sonar Checks'){
    
         sh  'echo Sonar Checks In Progress'
         //sh  'sonar-scanner -Dsonar.host.url=http://172.31.21.58:9000  ${ARGS} -Dsonar.projectKey=${COMPONENT} -Dsonar.login=admin  -Dsonar.password=password'
         //sh  'curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > sonar-quality-gate.sh'
             //sonar-quality-gate.sh ${SONARCRED_USR} ${SONARCRED_PSW} ${SONAR_URL} ${COMPONENT}
         sh  'echo Sonar Checks are completed'
        }    

}

// This is how we can handle scripted pipeline for parallel stages-
// Ref: https://stackoverflow.com/questions/57485965/how-can-i-create-parallel-stages-in-jenkins-scripted-pipeline
def testCases(){
    
       stage('Test Cases') {
        def stages = [:]

        stages["Unit Testing"] = {
            echo "Unit Testing started for ${COMPONENT}"
            echo "Unit Testing Completed for ${COMPONENT}"
        }
        stages["Integration Testing"] = {
             echo "Integration Testing started for ${COMPONENT}"
             echo "Integration Testing Completed for ${COMPONENT}"
        }
        stages["Functional Testing"] = {
             echo "Functional Testing started for ${COMPONENT}"
             echo "Functional Testing Completed for ${COMPONENT}"
        }

        parallel(stages)
       }
}

def artifacts(){
       stage('Validate Artifact Version'){
             env.UPLOAD_STATUS=sh(returnStdout: true , script: 'curl -s -L http://${NEXUSURL}:8081/service/rest/repository/browse/${COMPONENT}/ | grep ${COMPONENT}-${TAG_NAME}.zip || true' )
             print UPLOAD_STATUS
        }
                    
        if(env.UPLOAD_STATUS == ""){
            stage('Prepare Artifacts'){
                    if(env.APP_TYPE == "nodejs"){
                    sh '''
                         echo preparing the Artifacts for ${COMPONENT}
                         npm install
                         zip ${COMPONENT}-${TAG_NAME}.zip node_modules server.js
                    '''
                }
                else if(env.APP_TYPE == "python"){
                    sh '''
                            echo Preparing Artifacts for ${COMPONENT}
                    '''
                }
                 else if(env.APP_TYPE == "java"){
                    sh '''
                            echo Preparing Artifacts for ${COMPONENT}
                    '''
                }
                 else {
                    sh '''
                            echo Preparing Artifacts for ${COMPONENT}
                    '''
                }
            }
            stage('Upload Artifacts'){
                 if(env.APP_TYPE == "nodejs"){
                    sh '''
                         echo preparing the Artifacts for ${COMPONENT}
                         npm install
                         zip ${COMPONENT}-${TAG_NAME}.zip node_modules server.js
                    '''
                }
            }
        }
}         
            
            
         
          
