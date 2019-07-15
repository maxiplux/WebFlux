pipeline {
  agent {
    docker {
      image 'mongo'
      args '-p  27017:27017'
    }

  }
  stages {
    stage('') {
      steps {
        sh 'mvn test'
      }
    }
  }
  environment {
    MONGO_INITDB_ROOT_USERNAME = 'mongo'
    MONGO_INITDB_ROOT_PASSWORD = 'mongo'
  }
}