def lintchecks()
{   
    sh '''
             echo Performing lintchecks for ${COMPONENT}
             mvn checkstyle:check || true
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
                      sh "mvn clean compile"
                }
             }
         }                                                      // End of the stages      
    } 
}
