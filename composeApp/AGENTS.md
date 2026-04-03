# composeApp/ — Compose Multiplatform UI Module

UI layer for Android and Desktop (JVM). Renders shared components as Compose screens with Material 3.

## Structure

```
src/commonMain/kotlin/de/sh3n/kmp_starter_project/
├── App.kt                  # Entry point: Koin init check, AppTheme wrapper
├── ui/
│   ├── RootContent.kt      # ChildPages composable + NavigationBar (tab switching)
│   ├── HomeTabContent.kt   # Home tab's inner ChildStack rendering (Home ↔ Detail)
│   ├── screens/
│   │   ├── home/           # HomeScreen composable
│   │   ├── detail/         # DetailScreen composable
│   │   └── settings/       # SettingsScreen composable
│   └── theme/
│       ├── Color.kt        # Light/dark color schemes
│       ├── Type.kt         # Typography definitions
│       ├── Theme.kt        # AppTheme composable (MaterialTheme wrapper)
│       └── Tokens.kt       # AppTokens: Spacing, Sizing, Radius constants
```

## Platform Entry Points

| Platform | File | Responsibility |
|----------|------|----------------|
| Android | `androidMain/.../MainActivity.kt` | `setContent { App(rootComponent) }` |
| Android | `androidMain/.../StarterApp.kt` | `Application` class, calls `startKoin` |
| Desktop | `jvmMain/.../main.kt` | `startKoin` + `DefaultComponentContext` + `Window { App }` |

## Conventions (This Module)

- Screens are composable functions, not classes: `@Composable fun HomeScreen(component: HomeComponent)`.
- Screens receive their component as a parameter — never access Koin directly from UI.
- All spacing/sizing/radius via `AppTokens.*` — never hardcode `dp` values.
- All colors via `MaterialTheme.colorScheme` — never hardcode `Color(0x...)`.
- Tab rendering: `RootContent` reads `ChildPages` state, renders selected tab + `NavigationBar`.
- Inner navigation: `HomeTabContent` subscribes to `HomeTabComponent.childStack` for Home ↔ Detail.

## Anti-Patterns

- No hardcoded colors, spacing, sizing, or font sizes in screen files.
- No business logic in composables — delegate to shared components.
- No direct Koin `get()` calls in composables — pass dependencies through component interfaces.
- No `@Suppress` annotations to bypass type safety.
