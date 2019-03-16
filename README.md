### Android的练习项目
    01：搭建项目，写了补间动画，看了部分补间动画的源码，测试补间动画和属性动画对点击事件响应区域的影响，后面再补一遍文章。
    02：写一个自定义控件，实现右滑解锁的效果。一开始使用的是ViewDragHelper，但效果不是很友好，用户必须手指按在对应的拖拽View上，才能滑动。
        改动之后，手指不再限制具体的点击位置，只要触摸到，就可以滑动。
    03：使用NestedScrollingParent和NestedScrollingChild实现了一个嵌套滑动的demo，写了两个接口的主要方法的注释，浅浅的介绍了嵌套滑动的实现
    04：配置h5启动Android应用的intent-filter
        ```Android
          <!--浏览器启动app的配置-->
                    <intent-filter
                        android:autoVerify="true"
                        tools:targetApi="m">
                        <action android:name="android.intent.action.VIEW" />
        
                        <category android:name="android.intent.category.DEFAULT" />
                        <category android:name="android.intent.category.BROWSABLE" />
        
                        <data
                            android:host="www.wxz.com"
                            android:pathPrefix="/home/page"
                            android:scheme="http" />
                    </intent-filter>
        ```
        
    05：配置jni（JAVA Native Interface）环境。主要修改CMakeLists.txt文件和编写java文件中native方法对应的cpp文件
    '''
        add_library( # Sets the name of the library.
                jniPack #这个lib的名字
                
        
                # Sets the library as a shared library.
                SHARED
        
                # Provides a relative path to your source file(s).
                jniPack.cpp #cpp源文件
                )
    '''
    cpp文件中的一些语法
    '''
    extern "C" JNIEXPORT jstring JNICALL /**jstring对应java中的String，jobject对应Java中的object*/
    Java_com_example_viewpractice_pack_JniOpts_stringFromJNI( //默认Java开头，中间是包名路径，以_替换了.，最后面是native方法
            JNIEnv *env,
            jobject /* this */) {
        std::string hello = "Hello from C++";
        return env->NewStringUTF(hello.c_str());
    }
    '''
    