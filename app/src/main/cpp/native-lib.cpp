#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_com_lv_ndk_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jintArray JNICALL
Java_com_lv_ndk_MainActivity_getImgToGray(JNIEnv *env, jobject instance, jintArray data_, jint w,
                                          jint h) {
    jint *data = env->GetIntArrayElements(data_, NULL);

    // TODO
    if (data == NULL) {
        return 0; /* exception occurred */
    }
    int alpha = 0xFF << 24;
    for (int i = 0; i < h; i++) {
        for (int j = 0; j < w; j++) {
            // 获得像素的颜色
            int color = data[w * i + j];
            int red = ((color & 0x00FF0000) >> 16);
            int green = ((color & 0x0000FF00) >> 8);
            int blue = color & 0x000000FF;

            color = (red + green + blue) / 3;

            color = alpha | (color << 16) | (color << 8) | color;

            data[w * i + j] = color;
        }
    }

    int size = w * h;
    jintArray result = env->NewIntArray(size);
    env->SetIntArrayRegion(result, 0, size, data);
    env->SetIntArrayRegion(result, 0, size, data);
    env->ReleaseIntArrayElements(data_, data, 0);
    return result;
}