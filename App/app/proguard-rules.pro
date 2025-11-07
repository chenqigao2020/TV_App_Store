# 代码混淆压缩比，在0~7之间，默认为5，一般不做修改
-optimizationpasses 7

# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames

# 指定外部模糊字典
-obfuscationdictionary proguard-dict-o0O.txt

# 指定class模糊字典
-classobfuscationdictionary proguard-dict-o0O.txt

# 指定package模糊字典
-packageobfuscationdictionary proguard-dict-o0O.txt

#gson
# Gson specific classes
-dontwarn sun.misc.**
-keep class com.google.gson.stream.** { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { <fields>; }
# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

#eventbus
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# And if you use AsyncExecutor:
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

-keep class com.app.util.HawkConfig { *; }