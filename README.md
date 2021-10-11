# test_android-skin-support
### 最新在线文档请看
https://note.youdao.com/ynoteshare/index.html?id=087b54287a6f2978c0763e32ce2546f3&type=note&_time=1632893660948

# Android-skin-support使用及多模块切换方案
**Github**:https://github.com/ximsfei/Android-skin-support

**常见问题**：https://github.com/ximsfei/Android-skin-support/blob/master/docs/FAQ.md

**AlertDialog 换肤支持**：https://github.com/ximsfei/Android-skin-support/blob/master/docs/AlertDialog.md

若出现了`ClassNotFoundException: Didn't find class "androidx.legacy.widget.Space" `问题，请添加依赖：`implementation 'androidx.legacy:legacy-support-core-ui:1.0.0'`


**目录：**
1. [导入依赖、初始化](#1)
2. [换肤方式](#2)
3. [资源替换适用范围](#3)
4. [一般情况下与传统设置资源区别](#4)
5. [获取当前主题包及其资源](#5)
6. [自定义View使用注意](#6)
7. [多模块主题切换方案](#7)
8. [使用多渠道配置皮肤包](#8)
9. [动态设置资源：参考github](https://github.com/ximsfei/Android-skin-support#%E5%8A%A8%E6%80%81%E8%AE%BE%E7%BD%AE%E8%B5%84%E6%BA%90)



**延伸的特别用法**：参考[获取当前主题包及其资源](#5)
1. textColor代码设置方法
2. 代码使用color、drawable而非官方指定的使用id




## <div id="1">一、导入依赖、初始化</div>
support library
如果项目中还在使用support库，添加以下依赖
```
implementation 'skin.support:skin-support:3.1.4'                   // skin-support 基础控件支持
implementation 'skin.support:skin-support-design:3.1.4'            // skin-support-design material design 控件支持[可选]
implementation 'skin.support:skin-support-cardview:3.1.4'          // skin-support-cardview CardView 控件支持[可选]
implementation 'skin.support:skin-support-constraint-layout:3.1.4' // skin-support-constraint-layout Constrain
```
如果项目中使用了AndroidX, 添加以下依赖
```
implementation 'skin.support:skin-support:4.0.5'                   // skin-support
implementation 'skin.support:skin-support-appcompat:4.0.5'         // skin-support 基础控件支持
implementation 'skin.support:skin-support-design:4.0.5'            // skin-support-design material design 控件支持[可选]
implementation 'skin.support:skin-support-cardview:4.0.5'          // skin-support-cardview CardView 控件支持[可选]
implementation 'skin.support:skin-support-constraint-layout:4.0.5' // skin-support-constraint-layout ConstraintLayout 控件支持[可选]

```
插件式/自定义加载需要权限
```xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```
在Application的onCreate中初始化(如果是多模块公用一个Lib，继承其中的Application即可，详情请看**多模块主题切换方案**)
```java
@Override
public void onCreate() {
    super.onCreate();
    SkinCompatManager.withoutActivity(this)
            .addInflater(new SkinAppCompatViewInflater())           // 基础控件换肤初始化
            .addInflater(new SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
            .addInflater(new SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
            .addInflater(new SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
            .setSkinStatusBarColorEnable(false)                     // 关闭状态栏换肤，默认打开[可选]
            .setSkinWindowBackgroundEnable(false)                   // 关闭windowBackground换肤，默认打开[可选]
            .loadSkin();
}
```
如果项目中使用的Activity继承自AppCompatActivity，需要重载getDelegate()方法。可以定义一个BaseActivity重载该方法，其他activity继承。
```java
@NonNull
@Override
public AppCompatDelegate getDelegate() {
    return SkinAppCompatDelegateImpl.get(this, this);
}
```
## <div id="2">二、换肤方式</div>
1. **应用内**（==不推荐使用，修改资源名过于繁琐==）
2. **插件式(assets)**：缺点：不可热更新升级皮肤资源(并非完全不行，assets也是会被复制到路径：下再使用的)
3. **自定义加载策略（SD卡、zip）**

==资源加载优先级: 动态设置资源-加载策略中的资源-插件式换肤/应用内换肤-应用资源。==

==**注**：需要多个皮肤的情况下：可以只建一个皮肤module,不同的皮肤使用多渠道配置==

#### 1.应用内：
新建res-XXX目录，在gradle配置资源目录，每个id或名称都需重命名，如：btn_bg.xml、btn_bg_overlay.xml，R.string.text、R.string.text_overlay

**加载应用内皮肤:**

例：应用内换肤，皮肤名为: night; 新增需要换肤的资源添加后缀或者前缀。
需要换肤的资源为R.color.windowBackgroundColor, 添加对应资源R.color.windowBackgroundColor_night。
```java
SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN); // 后缀加载
SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_PREFIX_BUILD_IN); // 前缀加载
```
#### 2.插件式(assets)：
* 新建一个无activity的module，包名不可与主模块包名相同。可以删除依赖。
    * 例如：
    ```
    宿主包名: com.ximsfei.skindemo
    夜间模式: com.ximsfei.skindemo.night
    ```
* 资源放在与主模块相同路径下，资源名相同
* 打包skin模块，官方推荐将资源包从.apk改为.skin，assemble后，将资源包放在主包的assets/skins路径下
    * 可以修改skin模块gradle打包文件名称方便直接复制使用，例：
    ```
    android {
        。。。
        android.applicationVariants.all { variant ->
            variant.outputs.all {
                outputFileName = "overlay2.skin"
            }
        }
    }
    ```
* 加载插件皮肤库
    ```java
    //加载策略，指定assets方式
    strategy = SkinCompatManager.SKIN_LOADER_STRATEGY_ASSETS
    // 指定皮肤插件（插件需要放在assets/skins/下）,Listener参数可选（恢复默认皮肤不会触发）
    SkinCompatManager.getInstance().loadSkin("XXX.skin"[, SkinLoaderListener], int strategy);
    // 恢复应用默认皮肤
    SkinCompatManager.getInstance().restoreDefaultTheme();
    ```
#### 3.自定义加载策略（SD卡、zip）：
##### SD卡：
自定义加载器，在getSkinPath返回即将自定义的目录
```java
public class CustomSDCardLoader extends SkinSDCardLoader {
    public static final String TAG = "换肤CustomSDCardLoader";
    public static final int SKIN_LOADER_STRATEGY_SDCARD = Integer.MAX_VALUE;

    @Override
    protected String getSkinPath(Context context, String skinName) {
//        File file = new File(SkinFileUtils.getSkinDir(context), skinName);    //默认:/storage/emulated/0/Android/data/com.XXX.XXX/cache/skins/
        //将skin放在apk同目录
        File packageDir = new File(context.getPackageResourcePath());
        File file = new File(packageDir.getParent(), skinName);
        String path = file.getAbsolutePath();
        Log.e(TAG,context.getPackageName()+":path = "+path);
        return path;
    }

    @Override
    public int getType() {
        return SKIN_LOADER_STRATEGY_SDCARD;
    }
}
```
注: 自定义加载器type 值最好从整数最大值开始递减，框架的type值从小数开始递增，以免将来框架升级造成type 值冲突

在Application中，添加自定义加载策略:
```java
SkinCompatManager.withoutActivity(this)
        .addStrategy(new CustomSDCardLoader());          // 自定义加载策略，指定SDCard路径

```
注: 自定义加载器必须在Application中注册，皮肤切换后，重启应用需要根据当前策略加载皮肤

使用自定义加载器加载皮肤:
```java
SkinCompatManager.getInstance().loadSkin("night.skin", null, CustomSDCardLoader.SKIN_LOADER_STRATEGY_SDCARD);
```
##### zip包中加载资源:==待补充==




## <div id="3">三、资源替换适用范围</div>
1. Color
2. Drawable：图片、style、selector、shape等
3. mitmap
4. （可能有遗漏，请查看github或自己尝试）




## <div id="4">四、一般情况下与传统设置资源区别</div>
1. **XML**：无区别
2. **代码中**：对于color、drawable等的使用，==设置ID的可以，直接给资源的都不行==。例： tv.setBackgroundColor(getColor())不行。tv.setBackgroundResource(R.color.colorAccent)可以。==注：非要直接使用资源的，可看下小节：[获取当前主题包及其资源](#5)==
3. **通过代码new出来的对象**：不能直接new Vew();需要先定义好layout.xml，在代码中使用`imageView = (ImageView) View.inflate(this,R.layout.add_img,null);`(即使用inflate方式新建才可以)

==**例外**：TextColor，不能直接使用setTextColor,（xml设置是可以的），切换主题时并不会切换。可以使用TextAppearance代替（即style），在xml中设置或者代码中setTextAppearance()。要使用代码设置setTextColor请看[获取当前主题包及其资源](#5)。==




##  <div id="5">五、获取当前主题包及其资源</div>
默认主题下拿到的是空值。
1. 获取当前皮肤名称：`SkinPreference.getInstance().getSkinName();`
2. 获取当前皮肤包名：`SkinCompatResources.getInstance().getSkinPkgName()`

#### 获取当前主题下的资源：
```java
SkinCompatResources.getInstance().getSkinResources();   //resource
SkinCompatResources.getColor(context,resId);    // 有不同get：color/drawable/value/xml等
SkinCompatResources.getInstance().getColor(context,resId);   //同上
```

#### 特殊用法：
==**注1**：如果代码中是使用SkinCompatResources来给控件设置资源，当切主题时不会实时改变，当重启后会使用当前主题资源（可支持id、直接给资源如setBackground(Drawable)）==

==**注2**：为了弥补注1的缺陷，可以让activity或自定义view实现SkinCompatSupportable接口，在applySkin(切换主题时会调用)方法中给控件设置一个临时的资源，例：==
```java
    protected void onCreate(Bundle savedInstanceState) {
        tv = findViewById(R.id.tv);
        //无法实时切换，但是activity重启后会切换，可以在applySkin中弥补缺陷
        tv.setTextColor(SkinCompatResources.getColor(this,R.color.colorAccent));
        tv.setBackgroundColor(SkinCompatResources.getColor(this,R.color.colorAccent));
    }

    //每次切换主题时都会调用，包括切回默认
    @Override
    public void applySkin() {
        Log.e(TAG,"applySkin");
        //判空，该方法是会在activity启动前就执行的。Application会在其onCreate加载当前主题
        if(tv != null){
            //这里这样设置后是不会保存的
            tv.setTextColor(SkinCompatResources.getColor(this,R.color.colorAccent));
            tv.setBackgroundColor(SkinCompatResources.getColor(this,R.color.colorAccent));
        }

    }
```
## <div id="6">六、自定义View使用注意</div>
#### 官方例子：

自定义View换肤
要点:

实现SkinCompatSupportable接口

applySkin方法中实现换肤操作

在构造方法中解析出需要换肤的resId

自定义View可以直接继承自SkinCompatView, SkinCompatLinearLayout等已有控件

eg: [CustomTextView](https://github.com/ximsfei/Android-skin-support/blob/master/demo/skin-app/src/main/java/com/ximsfei/skindemo/widget/CustomTextView.java)

不想继承自已有控件

eg: [CustomTextView2](https://github.com/ximsfei/Android-skin-support/blob/master/demo/skin-app/src/main/java/com/ximsfei/skindemo/widget/CustomTextView2.java)

需要换肤自定义属性

// 需要换肤AutoCompleteTextView的R.attr.popupBackground属性

eg: [SkinCompatAutoCompleteTextView](https://github.com/ximsfei/Android-skin-support/blob/master/androidx/skin-support-appcompat/src/main/java/skin/support/widget/SkinCompatAutoCompleteTextView.java)


需要使用第三方库控件怎么办

// 需要使用https://github.com/hdodenhof/CircleImageView 控件, 并且要支持换肤

eg: [SkinCompatCircleImageView](https://github.com/ximsfei/Android-skin-support/blob/master/third-part-support/circleimageview/src/main/java/skin/support/circleimageview/widget/SkinCompatCircleImageView.java)

#### 其余可能需要说明的点
//如果自定义view中需要有代码setTextColor等需求，同样可以使用[获取当前主题包及其资源](#5)小节的方式
```java
    int id = typedArray.getResourceId(R.styleable.MyView_firstColor,0);
    int color = SkinCompatResources.getColor(context,id);
    tv.setTextColor(color);

     @Override
    public void applySkin() {
        if(tv != null){
           color = SkinCompatResources.getColor(context,id);
           tv.setTextColor(color);
        }
    }
```



## <div id="7">七、多模块主题切换方案</div>
在Setting模块创建contentProvider，其他模块的Application中监听该provider。

==为避免每个module都需要复制一遍Application、BaseActivity，以及后期修改的困难，抽离到一个公共的Library供其他模块引用。只需要继承这两个类即可。（到时看项目具体配置，后续更新）==

==**当前缺陷**：启动加载资源较多时，可能会先看到原皮肤，然后再切换==

#### provider
```java
public class ThemeProvider extends ContentProvider {
    public static final String Uri = "com.sv.theme.ThemeProvider";
    SharedPreferences skinPreference;

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        skinPreference = getContext().getSharedPreferences("skin", Context.MODE_PRIVATE);
        if(skinPreference == null) {
            Log.e("换肤","skinPreference == null");
            return null;
        }
        final String skinName = skinPreference.getString("skin","");
        if(skinName == null) {
            Log.e("换肤", "skinName == null");
            return null;
        }
        MatrixCursor cursor = new MatrixCursor(new String[]{"skin"});
        cursor.addRow(new String[]{skinName});
        return (Cursor) cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    //预留给其他可能要改变主题的模块
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        String skinName = contentValues.getAsString("skin");
        skinPreference = getContext().getSharedPreferences("skin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = skinPreference.edit();
        editor.putString("skin",skinName).commit();
        getContext().getContentResolver().notifyChange(uri,null);   //通知监听，内容改变
        return 0;
    }
}

```

#### manifest
```xml
<application
     android:name="com.example.skinlibrary.SkinApp"><!--直接使用或继承application-->

      <provider
    android:authorities="com.sv.theme.ThemeProvider"
    android:name=".activity.ThemeProvider"
    android:exported="true"
    /><!--exported="true"保证其他应用可以监听该provider-->
</application>
```


#### Application
```java
public class App extends Application {
     public static final String TAG = "换肤App";
    public static final int SKIN_LOADER_STRATEGY = CustomSDCardLoader.SKIN_LOADER_STRATEGY_SDCARD;  //加载策略，自定义sd
//    public static final int SKIN_LOADER_STRATEGY = SkinCompatManager.SKIN_LOADER_STRATEGY_ASSETS;  //加载策略，assets

    Uri uri;
    @Override
    public void onCreate() {
        Log.e(TAG,getPackageName()+"onCreate");
        super.onCreate();
        SkinCompatManager.withoutActivity(this)
                .addStrategy(new CustomSDCardLoader())
                .addInflater(new SkinAppCompatViewInflater())           // 基础控件换肤初始化
                .addInflater(new SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
                .addInflater(new SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
                .addInflater(new SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
                .setSkinStatusBarColorEnable(false)                     // 关闭状态栏换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(false)                   // 关闭windowBackground换肤，默认打开[可选]
                //                .loadSkin();  //不使用默认初始化加载，容易造成不一致时的闪屏切换
                ;

        //无关
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);    //Android5.0以下矢量图片兼容

        uri = Uri.parse("content://com.sv.theme.ThemeProvider/query");
        //启动先查询是否要更换
        changeSkin(uri);
        //监听变化
        getContentResolver().registerContentObserver(uri, true, new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                Log.i(TAG,getPackageName()+"收到provider变化");
                super.onChange(selfChange);
                changeSkin(uri);
            }
        });
    }


    private void changeSkin(final Uri uri) {
//                Log.e(TAG,getPackageName()+"查询");
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        while(cursor.moveToNext()){
            String skinName = cursor.getString(0);
            //如果一开始没有loadSkin，那么是不能进行这个判断的。因为虽然已保存，但还未初始化切换好
//                    if(skinName.equals(SkinPreference.getInstance().getSkinName())){
//                        Log.i(TAG,getPackageName()+"当前已是"+skinName+"主题，无需改变");
//                    }else
            if(skinName.equals("")){
                Log.i(TAG,getPackageName()+"准备切换默认主题："+skinName);
                SkinCompatManager.getInstance().restoreDefaultTheme();
            }else{
                Log.i(TAG,getPackageName()+"准备切换主题："+skinName);
                SkinCompatManager.getInstance().loadSkin(skinName,SKIN_LOADER_STRATEGY);
            }
            break;
        }
        cursor.close();
    }
}

```
#### SettingActivity改变主题（2种方式）
##### 1. 操纵sharedPreferences并通知provider变化
若provider在setting模块中：
可以用这种，也可以用下种方式：
```java
    //存主题值，改变主题
    SharedPreferences sharedPreferences = getSharedPreferences("skin",MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString("skin","overlay2.skin").commit();
    //通知provider变化
    getContentResolver().notifyChange(Uri.parse("content://"+ThemeProvider.Uri), null);


    //恢复默认
    SharedPreferences sharedPreferences1 = getSharedPreferences("skin",MODE_PRIVATE);
    SharedPreferences.Editor editor1 = sharedPreferences1.edit();
    editor1.putString("skin","").commit();
    //通知provider变化
    getContentResolver().notifyChange(Uri.parse("content://"+ThemeProvider.Uri), null);
```
##### 2. 直接使用getContentResolver().update();
若provider不在在setting模块中，由于获取不到sp，需要用update更新provider：

注：若provider的update没有通知更新，需要手动调用getContentResolver().notifyChange
```java
    //切换主题
    ContentValues contentValues = new ContentValues();
    contentValues.put("skin","overlay2.skin");
    getContentResolver().update(Uri.parse("content://"+ThemeProvider.Uri),contentValues,null,null);

    //恢复默认
    ContentValues contentValues1 = new ContentValues();
    contentValues1.put("skin","");
    getContentResolver().update(Uri.parse("content://"+ThemeProvider.Uri),contentValues1,null,null);

```


## <div id="8">八、使用多渠道配置皮肤包</div>
#### 1. 包名配置
第一种，多渠道中加后缀：
```gradle
//包名与宿主包名一致，采用加后缀方式

android{
    applicationId 'com.example.app'
    。。。

    flavorDimensions 'skin'
    productFlavors {
        overlay2 {
            applicationIdSuffix '.overlay2'   //往原来加后缀的方式
        }
        overlay3 {
            applicationIdSuffix '.overlay3'
        }
    }
}

```
第二种，直接在多渠道配置包名：
```gradle
android{
    applicationId '${applicationId}'

    flavorDimensions 'skin'
    productFlavors {
        overlay2 {
            applicationId 'com.example.app.overlay2'
        }
        overlay3 {
            applicationId 'com.example.app.overlay3'
        }
    }
}

```

#### 2. 输出文件名配置
最终输出文件名：渠道名.skin。例：overlay2.skin
```gradle
 android.applicationVariants.all { variant ->
        variant.outputs.all {
            outputFileName = productFlavors[0].name+".skin"
        }
 }
```
