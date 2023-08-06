def lintchecks()
{   
    sh '''
             echo Installing PyLint for ${COMPONENT}
             #pip3 install pylint
             #pylint *.py
             echo lint checks completed for ${COMPONENT}
    '''

}

def call(COMPONENT) {

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
             stage('Sonar Checks'){
                   steps{
                        script{
                            common.sonarchecks()
                        }
                }
             }
         }                                                      // End of the stages      
    } 
}
