# custom-image-selection

How to
To get a Git project into your build:

Step 1. Add the JitPack repository to your build file 

Add it in your root build.gradle at the end of repositories:

```
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
```

Step 2. Add the dependency

```
dependencies {
  implementation 'com.github.sz32:custom-image-selection:0.1.0'
}
```

How to use:

1. In your activity 
```
OpenGallery
.init(this)
.isSelectMultiple(true)
.build()
```
2. Get result :

```
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (data?.extras != null) {
        toast((data.extras?.getSerializable("DATA") as ArrayList<ImagePath>).size.toString())
    }
}
```

![](assets/preview1.jpg) ![](assets/preview2.jpg)
