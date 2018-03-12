# Project Setup Android

1. Open a Terminal and navigate to the project and run:

   ```
   fastlane init
   ```

2. Open fastlane/Fastfile and add the following code:

   ```
     desc "Runs all the tests"
     lane :test do
       gradle(task: "test")
       gradle(task: "connectedAndroidTest")
     end
   ```

3. Add a filed called `.gitlab-ci.` to the root of the project and insert the following code:

   ```
   variables:
     LC_ALL: "en_US.UTF-8"
     LANG: "en_US.UTF-8"

   before_script:
     - bundle install

   stages:
     - test

   tests:
     stage: test
     tags:
       - android
     script:
       - bundle exec fastlane test
   ```

4. The project is now ready to be pushed.
