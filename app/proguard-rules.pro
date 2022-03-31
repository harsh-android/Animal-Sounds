# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class io.plaidapp.data.api.dribbble.model.** { *; }
-keepnames class com.avstore.entertainment.animalsounds.SpleshActivity
-keepnames class com.avstore.entertainment.animalsounds.AllJsonAPI
-keepnames class com.avstore.entertainment.animalsounds.AnimalViewActivity
-keepnames class com.avstore.entertainment.animalsounds.MainActivity
-keepnames class com.avstore.entertainment.animalsounds.SubCategoryActivity



-keepattributes SourceFile, LineNumberTable

-keepattributes LocalVariableTable, LocalVariableTypeTable

-optimizations !method/removal/parameter

-repackageclasses com.avstore.entertainment.animalsounds.AnimalViewActivity



# #  ############### volley  ###############
# # -------------------------------------------
-keep class com.avstore.entertainment.animalsounds.volley.** {*;}
-keep class com.avstore.entertainment.animalsounds.toolbox.** {*;}
-keep class com.avstore.entertainment.animalsounds.volley.Response$* { *; }
-keep class com.avstore.entertainment.animalsounds.volley.Request$* { *; }
-keep class com.avstore.entertainment.animalsounds.RequestQueue$* { *; }
-keep class com.avstore.entertainment.animalsounds.volley.toolbox.HurlStack$* { *; }
-keep class com.avstore.entertainment.animalsounds.volley.toolbox.ImageLoader$* { *; }


-ignorewarnings