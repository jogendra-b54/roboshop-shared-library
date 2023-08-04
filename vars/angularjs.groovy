def lintchecks()
{   
    sh '''
             echo Installing JSLint for ${COMPONENT}
             echo lint checks completed for ${COMPONENT}
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
                      //sh "npm install"
                }
             }
         }                                                      // End of the stages      
    } 
}
