title: Android Studio 2.0 and RetroLambda
tags: android,android-studio

If you want to use lambdas in Android Development, you're out of luck natively, at the moment, since Android uses Java 7.

However, you can use the RetroLambda library--I'm using it in Android Studio 2.0 beta 5---to achieve the same results. Here's the `build.gradle` file you'll need in your main app dir.

    buildscript {
        repositories {
            ...
            mavenCentral()
        }
        dependencies {
            ...
            classpath 'me.tatarka:gradle-retrolambda:3.3.0-beta4'
            ...
        }
    }
    
    allprojects {
        repositories {
            ...
            mavenCentral()
        }
    }
    ...

Note we're including the retrolambda classpath in your buildscript `dependencies` and including the `mavenCentral()` repo in the `repositories`. The version of RetroLambda is advised for anyone using AS 2.0 beta 5. This will change.

Now here's your project's build.gradle:

    apply plugin: 'com.android.application'
    apply plugin: 'me.tatarka.retrolambda'
    
    android {
        ...
        compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
        }
        ...
    }
    ...
    
We're using the apply-plugin line to use retrolambda, ensure the project uses Java 8's lambda syntax.

Now--praise the Lord--we can now use syntax like this, in my case using RxJava:

    Observable.create(subscriber -> {
        // Stuff
    });
