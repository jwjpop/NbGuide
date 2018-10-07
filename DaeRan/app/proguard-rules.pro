-keep class org.xmlpull.v1.** { *; }

# Firebase 사용을 위한 설정
-keepattributes Signature, *Annotation*, InnerClasses, EnclosingMethod

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

-dontwarn org.ejml.**
-dontwarn org.xmlpull.**
-dontwarn io.reactivex.**
-dontwarn com.squareup.okhttp.**
-dontwarn com.thoughtworks.xstream.**
-dontwarn boofcv.**
-dontwarn java.awt.**
