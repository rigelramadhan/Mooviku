# Mooviku
### Project Description
**Mooviku** is a movie app that allows users to browse and discover movies using TheMovieDB API. This app is still under development and aims to provide a seamless experience for movie enthusiasts.

### Screenshots
| Home Screen | Detail Screen 1 | Detail Screen 2 | Detail Screen 3 | Search Screen |
|---------|---------|---------|---------|---------|
| ![Image 1](images/screenshot1.jpg) | ![Image 2](images/screenshot2.jpg) | ![Image 3](images/screenshot3.jpg) | ![Image 4](images/screenshot4.jpg) | ![Image 5](images/screenshot5.jpg) |
| Containing list of top rated and popular movies with access to search feature | Movie detail screen with expanding top app bar containing the title and bookmark functionality | Movie detail screen with the detailed information on the movie | Movie detail screen with reviews and movie recommendations | The UI of the search feature |

### Tools and Practices Used

- **Clean Architecture**
- **Reactive Programming** (Kotlin Flow)
- **StateFlow** instead of LiveData
- **Modularization** (core and main)
- **Dynamic Feature** (favorite feature)
- **Dagger2 / Hilt**
- **Retrofit**
- **Room**

#### Why?
To ensure the app's scalability and maintainability.

Clean Architecture:
- Promotes separation of concerns
- Allows for easier testing and maintenance
- Provides a clear and organized architecture for the app

Reactive Programming (Kotlin Flow):
- Enables the app to react to data changes efficiently
- Provides a more efficient and streamlined approach to handling asynchronous events

Modularization:
- The app is divided into Core and Main modules
- Provides a more organized and scalable approach to the app's development
- Allows for easy feature addition or removal

Dynamic Feature:
- A Dynamic Feature is being implemented for the favorite feature
- Provides a more efficient way to add features without affecting the app's overall performance

Dependency Injection:
- Dagger2 / Hilt is being used for dependency injection
- Provides a more organized and efficient way of handling dependencies

Networking:
- Retrofit is being used for network requests
- Provides a more efficient and streamlined approach to handling network requests

Local Storage:
- Room is being used for local data storage
- Provides a more efficient and streamlined approach to handling local data storage

### What's Currently Being Developed
- The implementation of Continuous Integration using Circle CI.

### What Will be Developed Ahead
- Paging
- Animations
- Jetpack Compose migration
