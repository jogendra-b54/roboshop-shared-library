def call() {

    node {
        ansiColor('xterm') {
          git branch: 'main', url: 'https://github.com/jogendra-b54/${REPONAME}.git' 

          stage('terraform init') {
                sh "cd ${TFDIR}"
                 sh "terrafile -f env-${ENV}/Terrafile"
                 sh "terraform init -backend-config=env-${ENV}/${ENV}-backend.tfvars"
             }
        stage('terraform plan') {
                 sh "cd ${TFDIR}"
                 sh "terraform plan -var-file=env-${ENV}/${ENV}.tfvars"

        }
        stage('terraform Action') {
                 sh "cd ${TFDIR}"
                 sh "terraform ${ACTION} -auto-approve -var-file=env-${ENV}/${ENV}.tfvars"

             }
        }
    }
}

//  pipeline {
//    agent { label 'WS' }
     

//     options {
//         ansiColor('xterm')
//     }
//      parameters {
//         choice(name: 'ENV', choices: ['dev', 'prod'], description: 'Select the Environment')
//         choice(name: 'ACTION', choices: ['apply', 'destroy'], description: 'Select Create or Destroy')
//     }
  
  
//  stages {
                 
//         stage('terraform init'){
//              steps {
//                 sh "terrafile -f env-${ENV}/Terrafile"
//                 sh "terraform init -backend-config=env-${ENV}/${ENV}-backend.tfvars"
//              }
//         }
//         stage('terraform plan'){
//              steps {
//                 sh "terraform plan -var-file=env-${ENV}/${ENV}.tfvars"
//              }
//         }
//         stage('terraform apply'){
//              steps {
//                 sh "terraform ${ACTION} -auto-approve -var-file=env-${ENV}/${ENV}.tfvars"
//              }
//         }
//     }
//  }