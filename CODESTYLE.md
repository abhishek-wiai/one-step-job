# Project guidelines
The project's architecture is based on the basic MVVM pattern described in the jetpack 
guidelines [here](https://developer.android.com/jetpack/docs/guide). We will use DataBinding and will use a scheme for
BaseViewModel, BaseFragment, BaseActivity inspired from
[here](https://github.com/MindorksOpenSource/android-mvvm-architecture)


## Project structure

The project structure and code organization for this project:  

### The package structure

| package   | Details                                                                                         |
| --------- |-------------------------------------------------------------------------------------------------|
|__api/__   |All the Network Call related(Retrofit) classes/interfaces/objects go here                        |
|__db/__    |All the DB(Room) related classes/daos/interfaces go here                                         |
|__di/__    |The dependency injection(Dagger) components and modules go here                                  |
|__model/__ |All the data classes/ DTOs go here                                                               |
|__repo/__  |All the repository classes go here                                                               |
|__ui/__    |All the UI related components (Activity, Fragment, ViewModel, Custom Views, Adapters) go here. The package is further split into module level sub packages(agent, customer, login, base)                                                              |
|__util/__  |All Util classes/objects, Constants, Extensions go here                                          |                                                         |
|__workers/__|All Worker/Job related(like Sync, CleanUp etc) classes go here                                  |

### The resources

For easy management the resources are split into two directores:
 - __res/__: All the resources which are common across app and are not specific to an activity/module go here
	
 - __res-features/__: All the module/activity specific resources go here, for each module there should be a subdirectory
  and then for each activity there should be a resource directory. For eg. For the login module, there
   are two subdirectories splash and login for SplashActivity and LoginActivity respectively.
    So the resources for Login activity should be in `res-features/login/login/` directory.
	
	__Please Note__: All the resource directories added here have to added in the __resourceFeatures__ array
	 in `resourcessystem/resources.gradle` file

### Versioning
All the version management for the project is abstracted out in the `versions.gradle` file present in the root folder.
 The version code/name of the app as well as the versions of the libraries being used are configured in this file.
  The parameters defined in this file can be used in build.gradle files as `versions.parameter_name`
 

## File naming

### Class files
Class names are written in [UpperCamelCase](http://en.wikipedia.org/wiki/CamelCase).

For classes that extend an Android component, the name of the class should end with the name of the component;
 for example: `SignInActivity`, `SignInFragment`, `ImageUploaderService`, `ChangePasswordDialog`

### Interface files
For interfaces the name should end with Interface; for example: `LamsApiInterface`.

__Please note__- For Room Dao interfaces do not append Interface at the end of the file name and let the interfaces be
 like `TableDao`

### Resources files

Resources file names are written in __lowercase_underscore__.

#### Drawable files

Naming conventions for drawables:


| Asset Type   | Prefix            |		Example               |
|--------------| ------------------|-----------------------------|
| Action bar   | `ab_`             | `ab_stacked.9.png`          |
| Button       | `btn_`	            | `btn_send_pressed.9.png`    |
| Dialog       | `dialog_`         | `dialog_top.9.png`          |
| Divider      | `divider_`        | `divider_horizontal.9.png`  |
| Icon         | `ic_`	            | `ic_star.png`               |
| Menu         | `menu_	`           | `menu_submenu_bg.9.png`     |
| Notification | `notification_`	| `notification_bg.9.png`     |
| Tabs         | `tab_`            | `tab_pressed.9.png`         |

Naming conventions for icons (taken from
 [Android iconography guidelines](http://developer.android.com/design/style/iconography.html)):

| Asset Type                      | Prefix             | Example                      |
| --------------------------------| ----------------   | ---------------------------- |
| Icons                           | `ic_`              | `ic_star.png`                |
| Launcher icons                  | `ic_launcher`      | `ic_launcher_calendar.png`   |
| Menu icons and Action Bar icons | `ic_menu`          | `ic_menu_archive.png`        |
| Status bar icons                | `ic_stat_notify`   | `ic_stat_notify_msg.png`     |
| Tab icons                       | `ic_tab`           | `ic_tab_recent.png`          |
| Dialog icons                    | `ic_dialog`        | `ic_dialog_info.png`         |

Naming conventions for selector states:

| State	       | Suffix          | Example                     |
|--------------|-----------------|-----------------------------|
| Normal       | `_normal`       | `btn_order_normal.9.png`    |
| Pressed      | `_pressed`      | `btn_order_pressed.9.png`   |
| Focused      | `_focused`      | `btn_order_focused.9.png`   |
| Disabled     | `_disabled`     | `btn_order_disabled.9.png`  |
| Selected     | `_selected`     | `btn_order_selected.9.png`  |




#### Layout files

Layout files should match the name of the Android components that they are intended for but moving the top level
 component name to the beginning. For example, if we are creating a layout for the `SignInActivity`,
  the name of the layout file should be `activity_sign_in.xml`.

| Component        | Class Name             | Layout Name                   |
| ---------------- | ---------------------- | ----------------------------- |
| Activity         | `UserProfileActivity`  | `activity_user_profile.xml`   |
| Fragment         | `SignUpFragment`       | `fragment_sign_up.xml`        |
| Dialog           | `ChangePasswordDialog` | `dialog_change_password.xml`  |
| AdapterView item | ---                    | `item_person.xml`             |
| Partial layout   | ---                    | `partial_stats_bar.xml`       |

A slightly different case is when we are creating a layout that is going to be inflated by an `Adapter`,
 e.g to populate a `ListView`. In this case, the name of the layout should start with `item_`.

Note that there are cases where these rules will not be possible to apply.
 For example, when creating layout files that are intended to be part of other layouts.
  In this case you should use the prefix `partial_`.

#### Menu files

Similar to layout files, menu files should match the name of the component.
 For example, if we are defining a menu file that is going to be used in the `UserActivity`,
  then the name of the file should be `activity_user.xml`

A good practice is to not include the word `menu` as part of the name because these files are already located in the
 `menu` directory.

#### Values files

Resource files in the values folder should be __plural__, e.g.
 `strings.xml`, `styles.xml`, `colors.xml`, `dimens.xml`, `attrs.xml`

## Asset Guidelines

### Drawable Guidelines
Whereever possible try to use Android Vector drawables instead of PNGs. For SVGs get __24X24__ size `#FF000000`
 colored vectors. Import them using Vector Asset Studio and then use `android:tint` to give color to it.
  Read more [here](https://developer.android.com/studio/write/vector-asset-studio#when)


## Code guidelines

### Kotlin style rules


### Naming

#### Package Name
Names of packages are always lower case and do not use underscores `(org.example.myproject)`

#### Class/Interface Name
See File naming for Class and Interface above

#### Function Name
Names of functions with a lowerCamelCase and no underscores:
```kotlin
fun processDeclarations() { ... }
```

#### Constant Name
Constant names use UPPER_SNAKE_CASE. Constants which are scalar values must use the __const__ modifier.
```kotlin
const val NUMBER = 5
val NAMES = listOf("Alice", "Bob")
val AGES = mapOf("Alice" to 35, "Bob" to 32)
val COMMA_JOINER = Joiner.on(',') // Joiner is immutable
val EMPTY_ARRAY = arrayOf()
```

#### Non Constant Names
Use lowerCamelCase. These apply to instance properties, local properties, and parameter names.


__View Variable Names__

While giving names to variables referring to your views follow __viewTypeVariableName__ 

```kotlin
lateinit var textViewTitle: TextView
lateinit var imageViewProfilePicture: ImageView
```

__Treat acronyms as words__

| Good           | Bad            |
| -------------- | -------------- |
| `XmlHttpRequest` | `XMLHTTPRequest` |
| `getCustomerId`  | `getCustomerID`  |
| `String url`     | `String URL`     |
| `long id`        | `long ID`        |

### Use spaces for indentation

Use __4 space__ indents for blocks (Don't use tab):

```kotlin
if (elements != null) {
    for (element in elements) {
        // ...
    }
}
```

Use __8 space__ indents for line wraps:

```kotlin
var i =
        someLongExpression(that, wouldNotFit, on, one, line);
```

### Use Java like standard brace style

Only trailing closing-braces are awarded their own line. All others appear the same line as preceding code:.

```kotlin
class MyClass {
  fun doSomething() {
    if (someTest) {
      // ...
    } else {
      // ...
    }
  }
}
```

Braces around the statements are required unless the condition and the body fit on one line and have no else/if
 else branches

```kotlin
if (condition) body()
```

An if/else conditional that is used as an expression may omit braces only if the entire expression fits on one line.
```kotlin
val value = if (string.isEmpty()) 0 else 1  // Okay
```

#### Annotations style

__Classes, Methods and Constructors__

Member or type annotations are placed on separate lines immediately prior to the annotated construct
 and after documentation

```kotlin
/* This is the documentation block about the class */
@Retention(SOURCE)
@Target(FUNCTION, PROPERTY_SETTER, FIELD)
annotation class Global
```

Annotations without arguments can be placed on a single line.
```kotlin
@JvmField @Volatile
var disposable: Disposable? = null
```

When only a single annotation without arguments is present, it may be placed on the same line as the declaration.
```kotlin
@Volatile var disposable: Disposable? = null

@Test fun selectAll() {
    // …
}
```
#### Limit variable scope

_The scope of local variables should be kept to a minimum (Effective Java Item 29).
 By doing so, you increase the readability and maintainability of your code and reduce the likelihood of error.
 Each variable should be declared in the innermost block that encloses all uses of the variable._

_Local variables should be declared at the point they are first used.
 Nearly every local variable declaration should contain an initializer.
  If you don't yet have enough information to initialize a variable sensibly,
 you should postpone the declaration until you do.
 - ([Android code style guidelines](https://source.android.com/source/code-style.html#limit-variable-scope))

#### Order import statements

If you are using an IDE such as Android Studio, you don't have to worry about this because your IDE is already obeying
 these rules. If not, have a look below.

The ordering of import statements is:

1. Android imports
2. Imports from third parties (com, junit, net, org)
3. java and javax
4. Same project imports

To exactly match the IDE settings, the imports should be:

* Alphabetically ordered within each grouping, with capital letters before lower case letters (e.g. Z before a).
* There should be a blank line between each major grouping (android, com, junit, net, org, java, javax).

More info [here](https://source.android.com/source/code-style.html#limit-variable-scope)

#### Logging guidelines


For logging we use [Timber](https://github.com/JakeWharton/timber). Use Debug Tree for debug logs.

#### Class member ordering

There is no single correct solution for this but using a __logical__ and __consistent__ order will improve code
 learnability and readability. It is recommendable to use the following order:
1. Companion Objects
2. Constants
3. Fields
4. Init Block/ Constructors
5. Override methods and callbacks (public or private)
6. Public methods
7. Private methods
8. Inner classes or interfaces

Example:

```kotlin
class MainActivity : Activity {

    val TAG = MainActivity::class.simpleName

    lateinit var title: String
    lateinit var textViewTitle: TextView

    override fun onCreate() {
        ...
    }

    public fun setTitle(title: String) {
    	mTitle = title;
    }

    private fun setUpView() {
        ...
    }

    interface RandomInterface {

    }

}
```

If your class is extending an __Android component__ such as an Activity or a Fragment,
 it is a good practice to order the override methods so that they __match the component's lifecycle__.
  For example, if you have an Activity that implements `onCreate()`, `onDestroy()`, `onPause()` and `onResume()`,
   then the correct order is:

```kotlin
class MainActivity : Activity {
    //Order matches Activity lifecycle
    override fun onCreate() {}
    
    override fun onResume() {}

    override fun onPause() {}

    override fun onDestroy() {}

}
```

#### Parameter ordering in methods

When programming for Android, it is quite common to define methods that take a `Context`. If you are writing a method like this,
 then the __Context__ must be the __first__ parameter.

The opposite case are __callback__ interfaces that should always be the __last__ parameter.

Examples:

```kotlin
// Context always goes first
fun loadUser(context: Context, userId: Int)

// Callbacks always go last
fun loadUserAsync(context: Context, userId: Int, callback: Callback)
```

#### String constants, naming, and values

Many elements of the Android SDK such as `SharedPreferences`, `Bundle`, or `Intent` use a key-value pair approach
 so it's very likely that even for a small app you end up having to write a lot of String constants.

When using one of these components, you __must__ define the keys as a `static final` fields and
 they should be prefixed as indicated below.

| Element            | Field Name Prefix |
| -----------------  | ----------------- |
| SharedPreferences  | `PREF_`             |
| Bundle             | `BUNDLE_`           |
| Fragment Arguments | `ARGUMENT_`         |
| Intent Extra       | `EXTRA_`            |
| Intent Action      | `ACTION_`           |

Note that the arguments of a Fragment - `Fragment.getArguments()` - are also a Bundle.
 However, because this is a quite common use of Bundles, we define a different prefix for them.

Example:

```kotlin
// Note the value of the field is the same as the name to avoid duplication issues
val PREF_EMAIL = "PREF_EMAIL";
val BUNDLE_AGE = "BUNDLE_AGE";
val ARGUMENT_USER_ID = "ARGUMENT_USER_ID";

// Intent-related items use full package name as value
val EXTRA_SURNAME = "com.myapp.extras.EXTRA_SURNAME";
val ACTION_OPEN_USER = "com.myapp.action.ACTION_OPEN_USER";
```

#### Arguments in Fragments and Activities

When data is passed into an `Activity` or `Fragment` via an `Intent` or a `Bundle`,
 the keys for the different values __must__ follow the rules described in the section above.

When an `Activity` or `Fragment` expects arguments, it should provide a `public static` method that facilitates
 the creation of the relevant `Intent` or `Fragment`.

In the case of Activities the method is usually called `getStartIntent()`:

```kotlin
companion object {
    fun getStartIntent(Context context, User user): Intent {
        val intent = Intent(context, DestinationActivity::class.java)
	intent.putParcelableExtra(EXTRA_USER, user)
	return intent
    }
}
```

For Fragments it is named `newInstance()` and handles the creation of the Fragment with the right arguments:

```kotlin
companion object {
    fun newInstance(User user): UserFragment  {
        val fragment = UserFragment()
	val args = Bundle()
	args.putParcelable(ARGUMENT_USER, user)
	fragment.arguments = args
	return fragment
}
```

__Note 1__: These methods should go at the top of the class before `onCreate()`.

__Note 2__: If we provide the methods described above, the keys for extras and arguments should be `private`
 because there is not need for them to be exposed outside the class.

#### Line length limit

Code lines should not exceed __120 characters__.
 If the line is longer than this limit there are usually two options to reduce its length:

* Extract a local variable or method (preferable).
* Apply line-wrapping to divide a single line into multiple ones.

There are two __exceptions__ where it is possible to have lines longer than 120:

* Lines that are not possible to split, e.g. long URLs in comments.
* `package` and `import` statements.

##### Line-wrapping strategies

There isn't an exact formula that explains how to line-wrap and quite often different solutions are valid.
 However there are a few rules that can be applied to common cases.

__Break at operators__

When the line is broken at an operator, the break comes __before__ the operator. For example:

```kotlin
var longName = anotherVeryLongVariable + anEvenLongerOne - thisRidiculousLongOne
        + theFinalOne;
```

__Assignment Operator Exception__

An exception to the `break at operators` rule is the assignment operator `=`, where the line break should happen
 __after__ the operator.

```kotlin
var longName =
        anotherVeryLongVariable + anEvenLongerOne - thisRidiculousLongOne + theFinalOne;
```

__Method chain case__

When multiple methods are chained in the same line - for example when using Builders - every call to a method should go
 in its own line, breaking the line before the `.`

```kotlin
Picasso.with(context).load("http://ribot.co.uk/images/sexyjoe.jpg").into(imageView);
```

```kotlin
Picasso.with(context)
        .load("http://ribot.co.uk/images/sexyjoe.jpg")
        .into(imageView);
```

__Long parameters case__

When a method has many parameters or its parameters are very long, we should break the line after every comma `,`

```kotlin
loadPicture(context, "http://ribot.co.uk/images/sexyjoe.jpg", mImageViewProfilePicture, clickListener, "Title of the picture");
```

```kotlin
loadPicture(context,
        "http://ribot.co.uk/images/sexyjoe.jpg",
        mImageViewProfilePicture,
        clickListener,
        "Title of the picture");
```


### XML style rules

#### Use self closing tags

When an XML element doesn't have any contents, you __must__ use self closing tags.

This is good:

```xml
<TextView
	android:id="@+id/text_view_profile"
	android:layout_width="wrap_content"
	android:layout_height="wrap_content" />
```

This is __bad__ :

```xml
<!-- Don\'t do this! -->
<TextView
    android:id="@+id/text_view_profile"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" >
</TextView>
```


#### Resources naming

Resource IDs and names are written in __lowercase_underscore__.

##### ID naming

IDs should be prefixed with the name of the element in lowercase underscore. For example:


| Element            | Prefix            |
| -----------------  | ----------------- |
| `TextView`           | `text_`             |
| `ImageView`          | `image_`            |
| `Button`             | `button_`           |
| `Menu`               | `menu_`             |

Image view example:

```xml
<ImageView
    android:id="@+id/image_profile"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />
```

Menu example:

```xml
<menu>
	<item
        android:id="@+id/menu_done"
        android:title="Done" />
</menu>
```

##### Strings

String names start with a prefix that identifies the section they belong to.
 For example `registration_email_hint` or `registration_name_hint`. If a string __doesn't belong__ to any section,
  then you should follow the rules below:


| Prefix             | Description                           |
| -----------------  | --------------------------------------|
| `error_`             | An error message                      |
| `msg_`               | A regular information message         |
| `title_`             | A title, i.e. a dialog title          |
| `action_`            | An action such as "Save" or "Create"  |



##### Styles and Themes

Unlike the rest of resources, style names are written in __UpperCamelCase__.

#### Attributes ordering

As a general rule you should try to group similar attributes together.
 A good way of ordering the most common attributes is:

1. View Id
2. Style
3. Layout width and layout height
4. Other layout attributes, sorted alphabetically
5. Remaining attributes, sorted alphabetically

### Tests style rules

#### Unit tests

Test classes should match the name of the class the tests are targeting, followed by `Test`.
 For example, if we create a test class that contains tests for the `DatabaseHelper`,
  we should name it `DatabaseHelperTest`.

Test methods are annotated with `@Test` and should generally start with the name of the method that is being tested,
 followed by a precondition and/or expected behaviour.

* Template: `@Test fun methodNamePreconditionExpectedBehaviour()`
* Example: `@Test fun signInWithEmptyEmailFails()`

Precondition and/or expected behaviour may not always be required if the test is clear enough without them.

Sometimes a class may contain a large amount of methods, that at the same time require several tests for each method.
 In this case, it's recommendable to split up the test class into multiple ones.
  For example, if the `DataManager` contains a lot of methods we may want to divide it into `DataManagerSignInTest`,
   `DataManagerLoadUsersTest`, etc. Generally you will be able to see what tests belong together because they
    have common [test fixtures](https://en.wikipedia.org/wiki/Test_fixture).

#### Espresso tests

Every Espresso test class usually targets an Activity, therefore the name should match the name of the targeted Activity
 followed by `Test`, e.g. `SignInActivityTest`

When using the Espresso API it is a common practice to place chained methods in new lines.

```
onView(withId(R.id.view))
        .perform(scrollTo())
        .check(matches(isDisplayed()))
```

## Credit
Most of the sections picked from [Ribot Android Guidelines](https://github.com/ribot/android-guidelines)
 and modified for kotlin.
