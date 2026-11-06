#!/usr/bin/env bash

echo 'Minimal'
./gradlew run '--args=--vfs other/stage3/vfs/minimal.json'

echo 'Files'
./gradlew run '--args=--vfs other/stage3/vfs/files.json'

echo 'Deep'
./gradlew run '--args=--vfs other/stage3/vfs/deep.json'
