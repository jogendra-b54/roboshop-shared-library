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
                   steps{
                            sh "echo Testing in Progress"
                        }
                }
            }
                                                              // End of the stages      
      }      
}

