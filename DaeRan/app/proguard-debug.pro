# Begin: Debug Proguard rules

-dontobfuscate                              #난독화를 수행하지 않도록 함
-keepattributes SoureFile,LineNumberTable,Signature   #소스파일, 라인 전보 유지
-keepattributes *Annotation*, InnerClasses, EnclosingMethod


-keepclassmembers class com.cowooding.nbguide.DB.**{ *;}
-keepclassmembers class com.cowooding.nbguide.TapPage.Chat.**{ *;}
-keepclassmembers class com.cowooding.nbguide.TapPage.MainPage.**{ *;}
-keepclassmembers class com.cowooding.nbguide.TapPage.MyPage.**{ *;}
-keepclassmembers class com.cowooding.nbguide.TapPage.PlayListPage.**{ *;}

# AdMob 사용을 위한 설정
-keep public class com.google.android.gms.ads.**{
    public *;
}
-keep public class com.google.ads.**{
    public *;
}

# Cauly 사용을 위한 설정
-keep public class com.fsn.cauly.** {
	public protected *;
}
-keep public class com.trid.tridad.** {
	public protected *;
}
-dontwarn android.webkit.**
# End: Debug ProGuard rules