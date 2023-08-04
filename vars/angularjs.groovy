def lintchecks()
{   
    sh '''
             echo Installing AngularLint for ${COMPONENT}
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
             stage('Code Quality Analysis'){
                   steps{
                      sh "echo Code Quality Analysis is in place"
                }
             }
         }                                                      // End of the stages      
    } 
}
