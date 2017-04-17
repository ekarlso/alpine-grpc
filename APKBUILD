pkgname=grpc
pkgver="1.3.0"
giturl=https://github.com/grpc/grpc
pkgdesc="gRPC"
pkgrel=1
arch="all"
depends=openssl
depends_dev="openssl-dev c-ares-dev protobuf-dev zlib-dev gcc g++ make libtool"
makedepends="$depends_dev"
install=
subpackages="$pkgname-dev"
url=https://github.com/grpc/grpc/
license=Apache
#source=$pkgname-$pkgver.tar.gz::https://github.com/grpc/grpc/archive/v1.2.5.tar.gz
#source=$pkgname-$pkgver.tar.gz::https://github.com/grpc/grpc/archive/master.tar.gz
#source=""

#_builddir="$srcdir"/$pkgname-$pkgver
_builddir="$srcdir"/$pkgname

prepare() {
    cd "$srcdir"
    wget https://build.svcs.io/artifacts/grpc.tar.gz
    mkdir grpc
    tar -xf grpc.tar.gz -C grpc

    #git clone https://github.com/grpc/grpc
    #git fetch origin pull/10800/head:fixed
    #git checkout -b fix FETCH_HEAD

	return 0
}

snapshot() {
    abuild clean
    abuild deps

    mkdir -p "$srcdir"
    cd $srcdir

}

build() {
	# parallel build issue:
	# https://github.com/protobuf-c/protobuf-c/issues/156
  cd "$_builddir"
  make -j4
}

package() {
	cd "$_builddir"
	make prefix="$pkgdir" install
    #make list
}

##sha256sums="e07ac5a2c657c25d5628529ec051f2ae3fa69a1d8802125810cba0c35fed9adf grpc-1.2.3.tar.gz"
##sha256sums="59ade1c93f2507d2804f47ca9497b3390f0b0f49e331504740df433fb636a33d grpc-1.2.5.tar.gz"
