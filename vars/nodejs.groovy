def  call(){
    node{
        common.lintchecks() 
        env.ARGS="-Dsonar.sources=."
        common.sonarchecks()
    }
}


/*  uncomment this to use the   declarative approach
def call(COMPONENT){
    pipeline{
         agent { label 'WS' }
         environment {
             SONARCRED =  credentials('SONARCRED')
             NEXUS =  credentials('NEXUS')
             SONAR_URL = "172.31.21.58"
             NEXUSURL= "172.31.95.24"
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
            stage('Validate Artifact Version'){
                when { expression { env.TAG_NAME !=null } }
                steps{
                    script{
                        env.UPLOAD_STATUS=sh(returnStdout: true , script: 'curl -s -L http://${NEXUSURL}:8081/service/rest/repository/browse/${COMPONENT}/ | grep ${COMPONENT}-${TAG_NAME}.zip || true' )
                        print UPLOAD_STATUS
                    }
                    // sh '''
                    // echo Upload status is  ${UPLOAD_STATUS}
                    // '''
                }
            }
            stage('Prepare Artifacts'){
                when {
                 expression { env.TAG_NAME !=null } 
                 expression { env.UPLOAD_STATUS == "" } 
                }
                steps{
                    sh '''
                        echo preparing the Artifacts for ${COMPONENT}
                        npm install
                        zip ${COMPONENT}-${TAG_NAME}.zip node_modules server.js
                    '''
                }
            }
            stage('Upload Artifacts'){
                 when { 
                    expression { env.TAG_NAME != null } 
                    expression { env.UPLOAD_STATUS == "" }
                }
                
                steps{
                    sh '''
                        echo uploading ${COMPONENT} Artifacts to Nexus
                        curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://172.31.95.24:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip
                        echo Uploading ${COMPONENT} Artifacts to Nexus is Completed
                     '''
                }
            }
        }
             
      }      
}

*/