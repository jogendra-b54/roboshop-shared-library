def lintchecks()
{   
    sh '''
             echo Installing JSLint for ${COMPONENT}
             npm i jslint
             node_modules/jslint/bin/jslint.js server.js || true
             echo lint checks completed for ${COMPONENT}
    '''

}

def sonarchecks()
{   
    sh '''
             echo Sonar Checks In Progress
             sonar-scanner -Dsonar.sources=.  -Dsonar.login=0137a6f92286deae048a73f8f632c18530ae98ca -Dsonar.host.url=http://172.31.21.58:9000 -Dsonar.projectKey=${COMPONENT}
             echo Sonar Checks are completed
    '''

}

def call(COMPONENT) {

    pipeline{
         agent { label 'WS' }
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
                            sonarChecks()
                        }
                }
             }
         }                                                      // End of the stages      
    } 
}
