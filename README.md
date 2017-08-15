# AndroidSiriWave
Android View which can act as iOS8 SiriWave

## Description
AndroidSiriWave extends LinearLayout that reproduces the waveform effect seen in Siri on iOS 7 and iOS 8. 

It is a Swift adaptation of the amazing [SCSiriWaveformView](https://github.com/stefanceriu/SCSiriWaveformView) by [Stefan Ceriu](https://github.com/stefanceriu).

Thanks for [Kevinzhow](https://github.com/kevinzhow) for his awesome implementation of this ios Wave form 

![Sample](http://i.imgur.com/e1KoYRY.gif)

## Gradle

The minimum API level supported by this library is API 15 

Add link to jitpack Repositories in your root level build.gradle

```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

In your module level build.gradle 

```gradle
dependencies {
	        compile 'com.github.ankitmhatre:AndroidSiriWave:0.1.0'
	}
```
## Usage
```Xml
  <com.am.siriview.DrawView
        .
        .
        app:wave_count="5"
        app:wave_color="#fff"
        app:waveRefreshInterval="30"/>
```
## Download

[![Dowload from Google Play](https://i.imgur.com/g9vve1f.png "Download from Google Play")](https://play.google.com/store/apps/details?id=com.am.siri_ios_8_waveform)

## License
License under GNU GPL v3.0
