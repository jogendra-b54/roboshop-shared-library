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
             sonar-scanner -Dsonar.sources=.  -Dsonar.login=0137a6f92286deae048a73f8f632c18530ae98ca -Dsonar.host.url=http://54.90.171.239:9000 -Dsonar.projectKey=${COMPONENT}
             curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > sonar-quality-gate.sh
             sonar-quality-gate.sh admin password 172.31.21.58 ${COMPONENT}
             echo Sonar Checks are completed
    '''

}
def call(COMPONENT){
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
                            sonarchecks()
                        }
                }
             }
             stage('Testing'){
                   steps{
                            sh "echo Testing in Progress"
                        }
                }
             }
                                                              // End of the stages      
    } 

