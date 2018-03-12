# Gitlab-Runner Setup for iOS and Android

This document will provide instructions on how to setup a gitlab-runner that can run Unit and UI Test on actual Android and iOS devices. 

**Attention**: Since the Apple Developer Tools are only available on macOS you can exclusively do this on an Apple Computer (A Mac Mini is recommended).

This guide assumes, that you are setting up a gitlab-runner on a fresh instance of macOS.

## Required Software

- Xcode
- Homebrew
- Java 8
- Gradle
- Android Studio
- rbenv
- bundler
- gitlab-runner

## Step by Step Guide

Going through the macOS setup is pretty straight forward and there are only a few things to keep in mind. It is not necessary to sign into an iCloud account during the setup process. When it comes to creating a user account choose a suiting account name such as "gitlab-runner". Later down the line this name can be used for remote login and screen sharing if maintenance is needed.

Once in the operating system follow these steps:

1. Open the App Store and check if there are any updates. If there are install them.

2. Mac System Preferences

   1. Sharing: Change the computer name to something like "ci-runner-1", check the boxes for "Screen Sharing" and "Remote Login".
   2. Energy Saver: Turn on "Prevent computer from sleeping automatically…" and "Start up automatically...".
   3. Users&Groups -> Login Option: Turn on automatic login.

   **Reason**: Some of these settings are for convenience, some of them are necessary. 1) Allows you to log in via ssh or use the screen sharing feature of macOS from another computer. This is done so you can maintain the system without connection a screen, mouse and keyboard to it. 2) and 3) Makes it so you don't have to manually turn on the computer and login.

3. Download Xcode from the App Store and open it. After accepting the licence terms it will download the Apple Developer Tools. (This will take some)

   **Reason**: A full Xcode installation is needed to build and run iOS Apps.

4. Point xcode-select to your Xcode installation:

   ```
   sudo xcode-select --switch /Applications/Xcode.app/Contents/Developer
   ```
   **Reason**: The xcode-select defaults to a different location.

5. Install Homebrew by running the following command.

   ```
   /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
   ```

   Homebrew will also install the Apple Command-line Tools.

   **Reason**: Homebrew is a very convenient package manager. As you can see it is used a lot during this installation.

6. Tap into `caskroom/cask` and `caskroom/versions` .

   ```
   brew tap caskroom/cask
   brew tap caskroom/versions
   ```
   **Reason**: This step is needed to install Java 8 and Android Studio via Homebrew.

7. Install Java 8.

   ```
   brew cask install java8
   ```
   **Reason**: Java 8 gets installed because Gradle needs it. Gradle is not compatible with Java 9 yet.

8. Install Gradle.

   ```
   brew install gradle
   ```
   **Reason**: Gradle is necessary for building and running Android Apps.

9. Install Android Studio

   ```
   brew cask install android-studio
   ```

   When installed, open it and follow the setup wizard. Chose "custom" installation when asked. Uncheck the boxes for "Performance" and "Android Virtual Device". Once the launch screen is displayed press "Configure/SDK Manager", check the SDK's you need and press "Apply". After the installation is complete you can quit Android Studio.

   **Reason**: It would technically be possible to only install the SDK's but for continuity (Xcode is also installed) and convenience we install Android Studio. Managing SDK's and the licences for them is very easy this way.

10. Install rbenv

   ```
   brew install rbenv
   echo 'if which rbenv > /dev/null; then eval "$(rbenv init -)"; fi' >> ~/.bash_profile
   source ~/.bash_profile
   ```
   **Reason**: Rbenv allows us to install ruby gems with standard user permissions wich is necessary for automatic gem installation with bundler. (More details in 11.) Also it is generally a good idea to manage ruby versions. 

11. Install the latest ruby version.

   Make sure to check what the latest version of ruby is!

   ```
   rbenv install 2.5.0
   rbenv global 2.5.0
   ```

   After the installation is complete restart the terminal.

12. Install bundler

    ```
    gem install bundler
    ```
    **Reason**: Bundler is mainly used to automatically install the fastlane gem. Fastlane makes it very easy and efficient to run tests, grab screenshots and many other things. It is highly recommended to include it in the CI process.

13. Install gitlab-runner

    ```
    sudo curl --output /usr/local/bin/gitlab-runner https://gitlab-runner-downloads.s3.amazonaws.com/latest/binaries/gitlab-runner-darwin-amd64

    sudo chmod +x /usr/local/bin/gitlab-runner

    gitlab-runner register
    gitlab-runner install
    ```

    To register a gitlab-runner you will need a few things. First of all you will need the URL of your Gitlab Server. Then you will need to provide the Gitlab CI Token. This can be found on the `admin/runners` page of Gitlab.  Recommended configuration:

    - Tags: ios, android
    - Run untagged jobs: false
    - Lock runner to current project: false
    - Executor: shell

    After the gitlab runner is installed add the following line to `~/.gitlab-runner/config.toml`.

    ```
    environment = ["ANDROID_HOME=/Users/ciguy/Library/Android/sdk", "GRADLE_OPTS=-Dorg.gradle.daemon=false"]
    ```

    Finally start the gitlab-runner.

    ```
    gitlab-runner start
    ```


    **Reason**: Gitlab-runner is the software that connects to the Gitlab Server. Reasons for the chosen settings:

    - Tags: This way Gitlab-CI jobs with the tags: iOS, Android can be run on this gitlab-runner. If all iOS and Android related Gitlab-CI jobs are tagged accordingly, it is guaranteed that the gitlab-runner will be able to run the job.
    - Run untagged jobs - false: This way it is guaranteed that the gitlab-runner won't get chosen for a job it cant run.
    - Lock runner to current project - false: This gitlab-runner should not be restricted to one particular project.

## Links


- Homebrew: https://brew.sh/index_de.html
- Rbenv installation: https://gorails.com/setup/osx/10.13-high-sierra
- Bundler: http://bundler.io/
- Gitlab-runner documentation: https://docs.gitlab.com/runner/

