
def imgName(name, arch, tag) {
  return "${name}-${arch}:${tag}"
}

def buildArch(name, arch, tag) {
    return docker.build(imgName(name, arch, tag), "-f Dockerfile.${arch} .")
}

def pushArchImage(img) {
    docker.withRegistry("https://harbor.svcs.io", "registry-harbor.svcs.io") {
        img.push()
        // We also set the last built to the latest
        img.push('latest')
    }
}

node {
  def abuilderName = "harbor.svcs.io/library/abuilder"

  def apk_key = "build@svcs.io-5901ae68.rsa"

  def arches = [
      "armhf",
      "x86_64"
  ]

  def workspace = pwd()

  stage("Setup") {
    git 'https://github.com/ekarlso/alpine-grpc'
    sh 'wget http://build.svcs.io/artifacts/qemu-arm-static -O qemu-arm-staic && chmod +x qemu-arm-static'
  }

  stage("Build abuilder") {
    def containers = [:]

    for (int i = 0; i < arches.size(); i++) {
      def arch = arches[i]

      containers[arch] = {
        def img = buildArch(abuilderName, arch, env.BUILD_TAG)

        pushArchImage(img)
      }
    }

    parallel containers
  }

  stage("Build grpc") {
    def containers = [:]

    for (int i = 0; i < arches.size(); i++) {
      def arch = arches[i]
      def archBuildDir = "${workspace}/${arch}"

      def img = imgName(abuilderName, arch, env.BUILD_TAG)

      sh "mkdir -p ${archBuildDir}"
      sh "cp ${workspace}/APKBUILD ${workspace}/*.patch ${archBuildDir}"

      def rsaKey = readFile "${env.HOME}/.abuild/${apk_key}"

      containers[i] = {
        def args = [
          "-e RSA_PRIVATE_KEY=\'" + rsaKey.trim() + "\'",
          "-e RSA_PRIVATE_KEY_NAME=\"${apk_key}\"",
          "-v ${archBuildDir}:/packages",
          "-v ${env.HOME}/.abuild/${apk_key}.pub:/etc/apk/keys/${apk_key}.pub",
          "-v ${archBuildDir}:/home/builder/package"
        ].join(" ")

        sh "docker run ${args} ${img}"

      }
      parallel containers
    }
  }

  stage("Publish packages") {
    for (int i = 0; i < arches.size(); i++) {
      def arch = arches[i]
      def archBuildDir = "${workspace}/${arch}"

      def dst = "${env.HOME}/artifacts/alpine/${arch}"
      sh "mkdir -p ${dst} && rsync -avP ${archBuildDir}/builder/${arch}/*apk ${dst}"
    }
  }
}
