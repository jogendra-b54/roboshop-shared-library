def call() {
    properties([
            parameters([
                choice(choices: 'dev\nprod' ,description: "select your environment" ,name: "ENV"),
                choice(choices: 'apply\ndestroy' ,description: "Choose an action" ,name: "ACTION"),
                string(choices: 'APP_VERSION' ,description: "Enter your backend version" ,name: "APP_VERSION")
            ]),
    ])
    node {
        ansiColor('xterm') {
          git branch: 'main', url: "https://github.com/jogendra-b54/${REPONAME}.git"

          stage('terraform init') {
            sh '''
                cd ${TFDIR}
                terrafile -f env-${ENV}/Terrafile
                terraform init -backend-config=env-${ENV}/${ENV}-backend.tfvars
            '''
            
             }
        stage('terraform plan') {
            sh '''
                 cd ${TFDIR}
                 terraform plan -var-file=env-${ENV}/${ENV}.tfvars -var APP_VERSION=${APP_VERSION}
            '''

        }
        stage('terraform Action') {
            sh '''
                 cd ${TFDIR}
                 terraform ${ACTION} -auto-approve -var-file=env-${ENV}/${ENV}.tfvars -var APP_VERSION=${APP_VERSION}
            '''

             }
        }
    }
}

