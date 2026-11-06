#!/usr/bin/env bash

echo 'Minimal'
./gradlew run '--args=--vfs other/stage3/vfs/minimal.json --script other/stage3/startup.shell'

echo 'Files'
./gradlew run '--args=--vfs other/stage3/vfs/files.json --script other/stage3/startup.shell'

echo 'Deep'
./gradlew run '--args=--vfs other/stage3/vfs/deep.json --script other/stage3/startup.shell'
