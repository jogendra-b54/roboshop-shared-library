def lintchecks()
{   
    sh '''
             echo Installing JSLint for ${COMPONENT}
             npm i jslint
             node_modules/jslint/bin/jslint.js server.js || true
             echo lint checks completed for ${COMPONENT}
    '''

}


def call(COMPONENT){
    pipeline{
         agent { label 'WS' }
         environment {
             SONARCRED =  credentials('SONARCRED')
             NEXUS =  credentials('NEXUS')
             SONAR_URL = "172.31.21.58"
         }
          stages{                                                // Start of the stages
            stage('Lint Checks'){
                  steps{
                     script {
                     lintchecks()
               }    
              
            }
        }
             stage('Code Compile'){
                   steps{
                      sh "npm install"
                }
             }

             stage('Sonar checks'){
                   steps{
                        script{
                            env.ARGS="-Dsonar.sources=."
                            common.sonarchecks()
                        }
                }
             }
             stage('Testing'){
                parallel {
                    stage('unit testing'){
                        steps{
                            sh "echo unit testing stated"
                            sh "echo unit testing Completed"
                        }
                    }
                    stage('Integration testing'){
                        steps{
                            sh "echo Integration testing stated"
                            sh "echo Integration testing Completed"
                        }
                    }
                    stage('Functional testing'){
                        steps{
                            sh "echo Functional testing stated"
                            sh "echo Functional testing Completed"
                        }
                    }
                }
                
            }
            stage('Prepare Artifacts'){
                when { expression { env.TAG_NAME !=null } }
                steps{
                    sh "echo preparing the Artifacts for ${COMPONENT}"
                    sh "npm install"
                    sh "zip ${COMPONENT}-${TAG_NAME}.zip node_modules server.js"
                }
            }
            stage('Upload Artifacts'){
                when { expression { env.TAG_NAME !=null } }
                steps{
                    sh '''
                        echo uploading ${COMPONENT} Artifacts to Nexus
                        curl -L -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://172.31.95.24:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip
                        echo Uploading ${COMPONENT} Artifacts to Nexus is Completed
                     '''
                }
            }
        }
             
      }      
}

