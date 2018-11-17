# FoodInfoAndroid

### Technologies:
* Dependency Injection - Dagger2
* Concurrency - RxJava / RxAndroid
* Local Database - Room
* Architecture - MVVM
  * Android data binding library
  * ViewModel + LiveData + ...
* Backend - Firebase functions (Javascript)
* ML - Firebase ML Kit
  * Custom NN models - Tensorflow Lite
  * ML Kit default models
* Authentication - FirebaseUI
* Analytics - Firebase Analytics
* Ads - Firebase Ads
* Image loading - Glide
* Billing - [In app billing library](https://github.com/anjlab/android-inapp-billing-v3)


### Architecture:
```
app
|
|--> models (POJO classes)
|
|--> services (repository pattern)
|    |--> `specific service`
|          |--> `service implementation (repository)`
|
|
|--> view
|    |--> `specific screen` (Activity, Fragment, etc)
|
|--> viewmodels (the same structure as `view`)
|    |--> ViewModel for the specific screen
|
|
|--> util
```
