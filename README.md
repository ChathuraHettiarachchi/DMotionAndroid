# DMotionAndroid - Android application with Dailymotion API
![AppBack](https://user-images.githubusercontent.com/13764097/188435125-c016e8bf-d066-4d95-87cc-d432c0d5ae5d.png)
DMotion is an Android application to showcase Dailymotion API with XML as the UI builder. The goal of the project is to demonstrate best practices by using up to date tech-stack and presenting modern Android application Architecture that is scalable, maintainable, and testable. This application may look quite simple, but it has all of these small details that will set the rock-solid foundation for the larger app suitable for bigger teams and long application lifecycle.

## Project content

The DMotion brings some of the best areas of Android development to one place.

* 100% Kotlin
* Clean architecture
* MVVM
* Kotlin Flows, coroutins
* Retrofit
* RoomDb
* Testing
* Dependency Injection with Dagger Hilt
* Material design

## Tech-stack
Because the minimum API level is set to 24, the provided approach is suitable for over 75% of Android devices. This project makes use of a number of well-known Android libraries and technologies. Unless there is a compelling need to use a non-stable dependency, the most of the libraries are in the stable version.

* Tech-stack
    * Koting + Coroutines
    * Dagger Hilt - dependency injection
    * Retrofit - networking
    * Coil - image loading library with memory caching
    * Lottie - animation library
    * Shimmer - animation library from Facbook for Shimmer effect
    * Jetpack StateFlow, ViewModel, Repository
* Architecture
    * Clean Architecture
    * MVVM
* Tests
    * Unit test with JUnit4
    * Espresso test for UI testing
    
## Architecture of the app

The DMotion follows Clean Architecture with separating domain, data, and presentation as layers. The application use the data models and API implementations from the domains to populate the UI. The domain contains the useCase functions with Kotlin Flows to emit data to the viewModels when needed.

The Data layer contains the functionality to call APIs, map data to DTOs, DAOs to access database. All DTOs support extension functions to convert DTO to model from domain layer for data population. This works as removing unwanted values before mapping.

The benefits of the approch:
- better separation of concerns.
- can build use-case wise
- each use-case is issolate from other implementations

#### Presentation layer
This layer is closest to what the user sees on the screen. The `presentation` layer is made of `MVVM` (`ViewModel` used to manage state of the data and support flows). All UIs are build with the `XML` files. 
`state` (for each main screen) approach has used to maintain state change from the APIs.

#### Domain layer
This is the application's main layer. It's worth noting that the 'domain' layer is separate from all other layers. This enables domain models and business logic to be separated from other levels.
In other words, modifications in other levels should have no impact on the 'domain' layer. For example, updating the database ('data' layer) or the screen UI ('presentation' layer) should not result in any code changes in the 'domain' layer.

#### Data layer
Manages application data and exposes these data sources to the 'domain' layer as repositories. This layer's typical tasks include retrieving data from the internet and, if desired, caching it locally. We have local database to store resource videos as well.

Components:
- **Repository** is exposing data to the `domain` layer. Depending on application structure and quality of the external APIs repository can also merge, filter, and transform the data. The intention of
these operations is to create high-quality data source for the `domain` layer, not to perform any business logic (`domain` layer `use case` responsibility).
- **RetrofitService** - defines a set of API endpoints.
- **DataModel** - defines the structure of the data retrieved from the network and contains annotations, so Retrofit (GSON) understands how to parse this network data (JSON) this data into objects. This also inclues extension funstion to convert data to Domain-Models.


## Important view logics
#### Channel Image
Well, the Dailymotion API is not returing channel thumbnails or and banner image. To have more clear implementation and also make UX good, I have to use another API from Pixabay to get the image related to each channel type. There was an issue from the begining on load time since it get triggered on each channel item. But if we go for sequencial way it will take even more time. It's like the multiplication of time with channel item count. To avaoid it, I had to go asynchronous and job joins, so each async will have it's own thread and time get reduced to 1/itemCount, which is fast.
</br>
<p align="center">
    <img src="https://user-images.githubusercontent.com/13764097/188431630-14c7f74a-59bb-4f09-abf4-8a9db85676b9.png" alt="Image" height="500" />
</p>


```kotlin

private suspend fun getImages(page: ChannelPage) {
        viewModelScope.launch(Dispatchers.IO) {
            val _list = mutableListOf<Channel>()
            val job = page.list.map {
                async {
                    val id = if (it.id == "shortfilms") "movie" else it.id
                    imagesUseCase(id, id).onEach { response ->
                        when (response) {
                            is Resource.Error -> {}
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                _list.add(it.apply {
                                    if (response.data?.list!!.isNotEmpty())
                                        image =
                                            response.data.list[(0 until response.data.list.size).random()].webformatURL.resolve()
                                })
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }.awaitAll()

            job.joinAll()
            _channelState.value = ChannelDataState(isLoading = false, data = page.apply { list = _list })
        }
    }
    
```


## Demo of the application

<p align="center">
    <img src="https://user-images.githubusercontent.com/13764097/188434279-1fbfbd73-9eef-483a-817f-da3ba06aad26.gif" alt="Image" height="500" />
</p>

## Tablet Support (UI changes)

<p align="center">
    <img src="https://user-images.githubusercontent.com/13764097/188433223-a5d34413-21e1-4519-9545-52765badf05e.png" alt="Image" />
</p>


## Dependancies of the application

```grable
    implementation 'androidx.core:core-ktx:1.7.0'
    implementation 'androidx.appcompat:appcompat:1.5.0'
    implementation 'com.google.android.material:material:1.6.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation "androidx.activity:activity-ktx:1.5.1"
    implementation 'com.google.android.flexbox:flexbox:3.0.0'
    implementation 'androidx.core:core-splashscreen:1.0.0'
    implementation 'com.google.ar:core:1.33.0'
    implementation 'androidx.test.espresso:espresso-idling-resource:3.4.0'
    implementation 'androidx.test.espresso:espresso-contrib:3.4.0'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'

    // test
    testImplementation "com.google.truth:truth:1.1.3"
    testImplementation 'junit:junit:4.13.2'
    testImplementation 'org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1'
    testImplementation "android.arch.core:core-testing:1.1.1"
    testImplementation 'app.cash.turbine:turbine:0.7.0'
    androidTestImplementation 'org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1'
    androidTestImplementation 'app.cash.turbine:turbine:0.7.0'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
    androidTestImplementation "com.google.truth:truth:1.1.3"
    androidTestImplementation project(path: ':app')
    androidTestImplementation  'com.google.dagger:hilt-android-testing:2.38.1'
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.38.1")
    debugImplementation "androidx.test:core:1.4.0"

    // Coroutines
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0'

    // Coroutine Lifecycle Scopes
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1"
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1"

    //Dagger - Hilt
    implementation 'com.google.dagger:hilt-android:2.38.1'
    kapt 'com.google.dagger:hilt-compiler:2.38.1'

    // Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation "com.squareup.okhttp3:okhttp:4.9.3"
    implementation "com.squareup.okhttp3:logging-interceptor:4.9.3"

    // coil image loader
    implementation("io.coil-kt:coil:2.2.0")

    // lottie animation
    implementation "com.airbnb.android:lottie-compose:5.0.3"

    // shimmer view
    implementation 'com.facebook.shimmer:shimmer:0.5.0'

    // circle image view
    implementation 'de.hdodenhof:circleimageview:3.1.0'

    // chip layoutManager
    implementation 'com.beloo.widget:ChipsLayoutManager:0.3.7@aar'

    // loading
    implementation 'com.github.ybq:Android-SpinKit:1.4.0'

    // room
    implementation("androidx.room:room-runtime:2.4.3")
    implementation("androidx.room:room-ktx:2.4.3")
    kapt("androidx.room:room-compiler:2.4.3")

    // network x - connectivity listener
    implementation 'com.github.rommansabbir:NetworkX:4.1.0'
```
