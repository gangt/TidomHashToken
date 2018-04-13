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



 -ignorewarnings                     # 忽略警告，避免打包时某些警告出现
 -optimizationpasses 5               # 指定代码的压缩级别
 -dontusemixedcaseclassnames         # 是否使用大小写混合
 -dontskipnonpubliclibraryclasses    # 是否混淆第三方jar
 -dontpreverify                      # 混淆时是否做预校验
 -verbose                            # 混淆时是否记录日志
 -optimizations !code/simplification/arithmetic,!field/*,!class/merging/*        # 混淆时所采用的算法

  -keepclasseswithmembernames class * {       # 保持 native 方法不被混淆
      native <methods>;
  }

  -keepclasseswithmembers class * {            # 保持自定义控件类不被混淆
      public <init>(android.content.Context, android.util.AttributeSet);
  }

  -keepclasseswithmembers class * {            # 保持自定义控件类不被混淆
      public <init>(android.content.Context, android.util.AttributeSet, int);
  }

  -keepclassmembers class * extends android.app.Activity { #保持类成员
     public void *(android.view.View);
  }

  -keepclassmembers enum * {                  # 保持枚举 enum 类不被混淆
      public static **[] values();
      public static ** valueOf(java.lang.String);
  }

  -keep class * implements android.os.Parcelable {    # 保持 Parcelable 不被混淆
    public static final android.os.Parcelable$Creator *;
  }



-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}


 -keep class com.yunqukuailian.app.**{*;}

#glide混淆
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule


  # ProGuard configurations for Bugtags
  -keepattributes LineNumberTable,SourceFile

  -keep class com.bugtags.library.** {*;}
  -dontwarn com.bugtags.library.**
  -keep class io.bugtags.** {*;}
  -dontwarn io.bugtags.**
  -dontwarn org.apache.http.**
  -dontwarn android.net.http.AndroidHttpClient

  # End Bugtags


  #rxjava start
  -keep class rx.**{*;}
  -keep class org.greenrobot.**{*;}
  #rxjava end

  #LRecyclerview
  -dontwarn com.github.jdsjlzx.**
  -keep class com.github.jdsjlzx.progressindicator.indicators.** { *; }



