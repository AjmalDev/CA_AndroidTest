# CareAxiom-Test

Android application written for the initial CareAxiom Test. 

## Problem Statement: 

A single view application that should load data from the given [API](https://jsonplaceholder.typicode.com/photos) everytime the app starts. Data should be saved offline. All the data should be displayed in tabular form and should grouped by the album id.  
###### Time Allowed : 2 Hours  



## Architecture
 
#### Language: 
- Kotlin

#### Design pattern:
- MVVM

#### Caching: 
- Glide for images 
- OkHttp Interceptor for saving API response

#### Networking:

- Retrofit + RXAndroid
- OKHttp
- Glide
- Gson

#### Dependency Injection:
- Dagger2

#### Pagination:
- Paging library (Jetpack) 

 
     
## Solution: 

Most of the application's dependencies are provided using Dagger2 to follow no initialisation inside the class rule. 
For Pagination, Paging library from android jetpack is used. Group by album id is done inside the Data Source class of paging library. 

For reloading data in case of failure, RXJava's completable is used inside paging by saving afterLoad response inside an action and later executed when reload is pressed. 


For saving the API response,[OKHttp's](https://github.com/square/okhttp) interceptor is used. Images are cached using Glide with CacheStrategy.DISK .

#### Caveats of OKHttp Cache: 
This approach is not a silver bullet for different type of APIs. For example , if there's an issue with the response, it can cache something problematic  which may causes crashes in the app.
Server may remove the cache-control from backend or add Pragma: no-cache. 
This approach was the simplest one as we have a time constraint of around two hours. 

A better approach would be using [BoundaryCallback](https://developer.android.com/reference/android/arch/paging/PagedList.BoundaryCallback) with paging library and Room. 



#### Devices: 
- Nexus 6P (8.0) 


#### Time Spent:  
- 3 hours approx.




## NOTES: 

- I have used physical device Nexus 6P for testing the app. Please consider running on a physical device.  


- Please feel free to contact me in case of any query or if another task is needed to be done in the initial assessment phase. 


    Email: MuhammadAjmal.zia1@gmail.com 
    Phone: 0321-6726236
 
Thank you :) 
