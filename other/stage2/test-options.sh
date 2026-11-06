#!/usr/bin/env bash

echo 'CLI'
./gradlew run '--args=--vfs other/stage3/vfs/minimal.json --script other/stage2/success.shell'

echo 'INI'
./gradlew run '--args=--config other/stage2/config.ini'

echo 'CLI priority'
./gradlew run '--args=--config other/stage2/config.ini --vfs other/stage3/vfs/deep.json --script other/stage2/exit.shell'
