#!/usr/bin/env bash

./gradlew clean :app:bundleDebug

bundletool build-apks --local-testing --bundle app/build/outputs/bundle/debug/app-debug.aab --output app/build/outputs/apks/app-debug.apks

bundletool install-apks --apks app/build/outputs/apks/app-debug.apks