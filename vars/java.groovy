def call()
{
   node {
      env.APP_TYPE="java"
      common.lintchecks()
      env.ARGS="-Dsonar.java.binaries=target/"
      common.sonarchecks()
      common.testCases()
      common.artifacts()
   }
}
// def lintchecks()
// {   
//     sh '''
//              echo Performing lintchecks for ${COMPONENT}
//              mvn checkstyle:check || true
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
//                    }    
              
//                  }
//             }
              
//              stage('Code Compile'){
//                    steps{
//                       sh "mvn clean compile"
//                 }
//              }
//              stage('Sonar Checks'){
//                   steps{
//                      script {
//                         env.ARGS="-Dsonar.java.binaries=target/"
//                         common.sonarchecks()
//                    }    
              
//                 }
//             }
//             stage('Testing'){
//                    steps{
//                             sh "echo Testing in Progress"
//                         }
//                 }
//             }
//          }                                                      // End of the stages      
//     } 

