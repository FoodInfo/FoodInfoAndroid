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

#-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod
-keepattributes Signature, Exceptions, Annotation, SourceFile, LineNumberTable

-dontwarn java.nio.file.Files
-dontwarn java.nio.file.Path
-dontwarn java.nio.file.OpenOption
-dontwarn com.caverock.androidsvg.**
-dontwarn org.codehaus.mojo.**
-dontwarn retrofit2.**
-dontwarn com.google.appengine.**
-dontwarn rx.**
-dontwarn org.apache.**
-dontwarn android.net.http.**
-dontwarn okhttp3.**
-dontwarn com.xpn.spellnote.ui.**
-dontwarn org.conscrypt.**
-dontwarn libcore.io.**
-dontwarn javax.**
-dontwarn io.realm.**

-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class *

-keep class retrofit2.** { *; }
-keep public class com.google.** { *; }
-keep class com.google.android.gms.** { *; }
-keep class com.xpn.spellnote.** { *; }
-keep class android.view.MenuItem { *; }
-keep class com.tooltip.** { *; }
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-keep class * extends java.util.ListResourceBundle {
    protected java.lang.Object[][] getContents();
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# Enable optimizations
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification
-dontpreverify

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose