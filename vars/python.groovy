
def call()
{
   node {
      common.lintchecks() 
      env.ARGS="-Dsonar.sources=."
      common.sonarchecks()  
   }
}
// def lintchecks()
// {   
//     sh '''
//              echo Installing PyLint for ${COMPONENT}
//              #pip3 install pylint
//              #pylint *.py
//              echo lint checks completed for ${COMPONENT}
//     '''

// }

// def call(COMPONENT) {

//     pipeline{
//          agent { label 'WS' }
//            environment {
//              SONARCRED =  credentials('SONARCRED')
//              SONAR_URL = "172.31.21.58"
//          }
//          stages{                                                // Start of the stages
//             stage('Lint Checks'){
//                   steps{
//                      script {
//                      lintchecks()
//                }    
              
//             }
//         }
//              stage('Sonar Checks'){
//                    steps{
//                         script{
//                             env.ARGS="-Dsonar.sources=."
//                             common.sonarchecks()
//                         }
//                 }
//              }
//             stage('Testing'){
//                    steps{
//                           sh "echo Testing"
//                         }
//                 }
//              }
//          }                                                      // End of the stages      
//     } 

