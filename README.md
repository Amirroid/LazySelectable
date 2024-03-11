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
**Step 3**. Write states
```kotlin
// For Lazy Grids
val lazyGridState = rememberLazyGridState()

// For understand states
val lazyGridSelectableState = rememberLazyGridSelectableState(lazyGridState = lazyGridState)
```

**Step 4**. Use Lazy Components
``` kotlin
LazyVerticalGrid(
	state = lazyGridState,
	// For detect inputs
	modifier = Modifier`**.verticalSelectableHandler(lazyGridSelectableState)**`,
	columns = GridCells.Fixed(3),
	horizontalArrangement = Arrangement.spacedBy(12.dp),
	contentPadding = PaddingValues(12.dp),
	verticalArrangement = Arrangement.spacedBy(12.dp)
){
	items(100) { index ->
		val selected =
		lazyGridSelectableState.selected.any { info -> info.index == index }
		val color = if (selected) Color.Red else Color.Blue
		Box(
			modifier = Modifier
				.background(color)
				.fillMaxWidth()
				.aspectRatio(1f)
	)
}
```
