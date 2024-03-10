# Drag to select items in lazy Compose [![](https://jitpack.io/v/Amirroid/LazySelectable.svg)](https://jitpack.io/#Amirroid/LazySelectable)
if you want to use drag to select items in `LazyColumn`, `LazyRow`, `LazyVerticalGrid`, `LazyHorizontalGrid`, `LazyVerticalStaggeredGrid`, `LazyHorizontalStaggeredGrid`, you should use this library

## How to use?
**Step 1**. Add the JitPack repository to your build file
```kotlin
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		mavenCentral()
		maven("https://jitpack.io")
	}
}
```
**Step 2**. Add the dependency
```kotlin
dependencies {
	implementation("com.github.Amirroid:LazySelectable:${latest_version}")
}
```
