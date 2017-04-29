# Contributor: Shiz <hi@shiz.me>
# Maintainer: Shiz <hi@shiz.me>
pkgname=grpc
pkgver=1.3.0
pkgrel=0
pkgdesc="high performance, open-source universal RPC framework"
url="http://www.grpc.io"
arch="all"
license="BSD-3"
depends_dev="protobuf-dev"
makedepends="$depends_dev libressl-dev c-ares-dev"
subpackages="$pkgname-dev"
source="grpc-$pkgver.tar.gz::https://github.com/grpc/grpc/archive/v$pkgver.tar.gz
	fix-soname-mismatch.patch"
builddir="$srcdir/grpc-$pkgver"
options="!check" # broken testing infrastructure

build() {
	cd "$builddir"
	make CONFIG=opt prefix=/usr
}

package() {
	cd "$builddir"
	# set prefix to $pkgdir/usr here because the build system doesn't
	# have a DESTDIR equivalent.
	make CONFIG=opt prefix="$pkgdir/usr" install
}

sha512sums="abf2ec49a63af24cdff6f4703805724288244f49f695d43a1f61fbe30922dfbca745f9b5026351735ffeee7d64156c687ec8f14ee3e9901185106a4c2227a41c  grpc-1.3.0.tar.gz
af8e7ea2c2935dc10ad3efdede359ec18fb6d7b8a6bdaf6add0796434e8f9882f02e2b51c040544169bded81590b24115123652a33d1a5a40f8e53169f6b4df9  fix-soname-mismatch.patch"
