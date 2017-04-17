# Howto
https://github.com/andyshinn/docker-alpine-abuild/blob/master/edge/Dockerfile

# Building for armhf...
docker build -f Dockerfile.armhf . -t armhf-abuilder
docker run -it -e RSA_PRIVATE_KEY="$(cat ~/.abuild/$APK_KEY.pub)" -e RSA_PRIVATE_KEY_NAME="$APK_KEY" -v "$HOME/abuild/packages:/packages" -v "$HOME/.abuild/$APK_KEY.pub:/etc/apk/keys/$APK_KEY.pub" -v $PWD:/home/builder/package armhf-abuilder

# Building for x86_64
docker build -f Dockerfile . -t abuilder
docker run -it -e RSA_PRIVATE_KEY="$(cat ~/.abuild/$APK_KEY.pub)" -e RSA_PRIVATE_KEY_NAME="$APK_KEY" -v "$HOME/abuild/packages:/packages" -v "$HOME/.abuild/$APK_KEY.pub:/etc/apk/keys/$APK_KEY.pub" -v $PWD:/home/builder/package abuilder
