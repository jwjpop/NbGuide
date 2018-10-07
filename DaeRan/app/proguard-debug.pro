# Begin: Debug Proguard rules

-dontobfuscate                              #난독화를 수행하지 않도록 함
-keepattributes SoureFile,LineNumberTable,Signature   #소스파일, 라인 전보 유지
-keepattributes *Annotation*, InnerClasses, EnclosingMethod

-keep class com.google.firebase.database.obfuscated.**{ *;}
# End: Debug ProGuard rules