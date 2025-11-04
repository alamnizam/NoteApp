# NotesApp 📝

A modern Android note-taking application built with Jetpack Compose and Clean Architecture principles. This app provides a seamless experience for creating, editing, searching, and managing personal notes.

## 🌟 Features

- **User Authentication**: Secure login and registration system
- **Create Notes**: Add new notes with rich content
- **Edit Notes**: Modify existing notes anytime
- **Note Details**: View complete note information
- **Search Notes**: Quickly find notes using search functionality
- **Delete Notes**: Remove unwanted notes
- **Material Design 3**: Modern UI with Material Design 3 components
- **Offline First**: Local database storage with Room

## 🏗️ Architecture

This project follows **Clean Architecture** principles with a modular approach, separating concerns into different layers:

### Project Structure

```
NotesApp/
├── app/                          # Main application module
├── authentication/               # Authentication feature
│   ├── login/                   # Login feature module
│   │   ├── data/               # Data layer
│   │   ├── domain/             # Domain/Business logic layer
│   │   └── presentation/       # UI layer
│   ├── registration/           # Registration feature module
│   │   ├── data/
│   │   ├── domain/
│   │   └── presentation/
│   └── navigation/             # Authentication navigation
├── notes/                       # Notes feature
│   ├── add_note/               # Add note functionality
│   ├── edit_note/              # Edit note functionality
│   ├── note_details/           # Note details view
│   ├── note_list/              # Notes list view
│   ├── note_search/            # Search notes
│   ├── delete_notes/           # Delete notes functionality
│   └── navigation/             # Notes navigation
├── common/                      # Shared utilities and components
├── theme/                       # App theme and styling
└── navigation/                  # Core navigation
```

### Clean Architecture Layers

- **Presentation Layer**: Jetpack Compose UI, ViewModels, UI State Management
- **Domain Layer**: Use Cases, Business Logic, Domain Models
- **Data Layer**: Repositories, Data Sources, API/Database Implementation

## 🛠️ Tech Stack

### Core
- **Kotlin** - Programming language
- **Jetpack Compose** - Modern Android UI toolkit
- **Material Design 3** - Design system

### Architecture & DI
- **Dagger Hilt** - Dependency Injection
- **Clean Architecture** - Architectural pattern
- **Multi-module** - Modular project structure

### Jetpack Components
- **Room Database** - Local data persistence
- **Navigation Compose** - In-app navigation
- **ViewModel** - UI state management
- **DataStore Preferences** - Key-value storage
- **Lifecycle Components** - Lifecycle-aware components

### Networking
- **Retrofit** - REST API client
- **OkHttp** - HTTP client
- **Gson** - JSON serialization

### Asynchronous
- **Kotlin Coroutines** - Asynchronous programming
- **Kotlin Flow** - Reactive streams

### UI/UX
- **Coil** - Image loading
- **Lottie** - Animations
- **Material Icons Extended** - Icon library
- **Splash Screen API** - App startup experience

### Testing
- **JUnit** - Unit testing framework
- **Mockito** - Mocking framework
- **Turbine** - Flow testing
- **Espresso** - UI testing
- **Robolectric** - Android unit testing
- **MockWebServer** - API testing
- **Coroutines Test** - Coroutine testing utilities

## 📋 Requirements

- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 36
- **Compile SDK**: 36
- **Java Version**: 17
- **Kotlin Version**: 2.0.21
- **Gradle**: 8.13.0

## 🚀 Getting Started

### Prerequisites

- Android Studio Ladybug or later
- JDK 17 or higher
- Android SDK with API Level 36

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/NotesApp.git
   cd NotesApp
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned repository
   - Wait for Gradle sync to complete

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click "Run" in Android Studio or use:
   ```bash
   ./gradlew installDebug
   ```

## 🧪 Testing

### Run Unit Tests
```bash
./gradlew test
```

### Run Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

### Run All Tests
```bash
./gradlew testDebug connectedAndroidTest
```

## 📦 Build Variants

- **Debug**: Development build with debugging enabled
- **Release**: Production-ready build with optimization

### Generate Release APK
```bash
./gradlew assembleRelease
```

## 🔧 Configuration

### Local Properties
Create a `local.properties` file in the root directory:
```properties
sdk.dir=YOUR_ANDROID_SDK_PATH
```

## 📱 Features in Detail

### Authentication
- User registration with validation
- Secure login mechanism
- Session management

### Notes Management
- Create notes with title and content
- Edit existing notes
- View note details
- Delete single or multiple notes
- Search through notes efficiently

### User Experience
- Smooth animations
- Material Design 3 theming
- Responsive UI
- Offline-first approach

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 🙏 Acknowledgments

- Android Jetpack team for amazing libraries
- Material Design team for design guidelines
- Open source community

---

**Made with ❤️ using Jetpack Compose**

