# Uphaar-kotlin

Uphaar is an Android application built with Kotlin that connects donors with donation centers, making it easy to give and track charitable contributions. The app provides secure user authentication, an intuitive interface to explore donation locations, and tools to manage your own donations.

## Features

- **User Authentication**: Secure login and registration.
- **Browse Donation Places**: Explore locations where you can donate.
- **Track Donations**: View and manage your own donations.
- **Add New Locations**: Request to add new donation places.
- **Modern Android UI**: Clean interface using RecyclerView and Fragments.

## Tech Stack

- **Language:** Kotlin
- **Platform:** Android SDK
- **Architecture:** MVVM pattern (implied)
- **UI:** RecyclerView, Fragments, Activities
- **Authentication:** Custom (Firebase/Auth-ready)
- **Data Models:** Kotlin data classes

## Directory Structure

```
app/src/main/java/com/digipod/uphaar/
├── AuthActivity.kt           # Handles user authentication
├── MainActivity.kt           # Main activity & navigation
├── adapters/                 # RecyclerView adapters
├── fragments/                # UI fragments (screen modules)
├── listeners/                # Custom interaction listeners
├── models/                   # Data models for donations/places
```

### Key Files

- `AuthActivity.kt`: Manages login/sign-up.
- `MainActivity.kt`: Main app entry and navigation.
- `models/DonateModel.kt`: Donation data structure.
- `models/DonationPlace.kt`: Donation location data structure.
- `adapters/DonateAdapter.kt`: Adapter for donation items.
- `adapters/MyDonateAdapter.kt`: Adapter for user-specific donations.
- `adapters/PlaceAdapter.kt`: Adapter for locations.
- `fragments/AddLocationRequest.kt`: Fragment to add/request new donation locations.

## Getting Started

1. **Clone the repository:**
   ```sh
   git clone https://github.com/zaid-kamil/Uphaar-kotlin.git
   ```
2. **Open in Android Studio**
3. **Sync Gradle and build the project**
4. **Run on an emulator or device**

## Contribution

Pull requests are welcome. For significant changes, please open an issue to discuss your ideas first.

## License

[MIT](LICENSE)
