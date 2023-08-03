def lintchecks()
{   
    sh '''
             echo Installing JSLint
             npm i jslint
             node_modules/jslint/bin/jslint.js server.js || true
    '''

}

def call() {

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
         }                                                      // End of the stages      
    } 
}

//   script {
//                         sample.info("CATALOGUE")
//                     }