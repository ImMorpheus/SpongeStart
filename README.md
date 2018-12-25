# SpongeStart  
[![GitHub 
stars](https://img.shields.io/github/stars/ImMorpheus/SpongeStart.svg?style=flat-square)](https://github.com/ImMorpheus/SpongeStart/stargazers) [![GitHub 
issues](https://img.shields.io/github/issues/ImMorpheus/SpongeStart.svg?style=flat-square)](https://github.com/ImMorpheus/SpongeStart/issues) [![Latest Version](https://img.shields.io/badge/SpongeStart-v4.0.0-green.svg?style=flat-square)](https://plugins.gradle.org/plugin/com.qixalite.spongestart2)

Gradle plugin to run sponge inside your workspace. Based on the SpongeStart by thomas15v.
I’ve fixed the SpongeVanilla issue and added a few things.

### Major differences

* SpongeVanilla issues should be solved (thanks to RandomByte).
* Removed acceptEula task (Eula is now accepted by default upon server setup, avoiding the creation of an eula.txt in the forge folder while you’re setting up a vanilla server).
* Mercurius is deleted during forge server setup. 
* downloadSpongeVanilla is a separate task now.
* New properties in build.gradle (see below).
* Tweaked configs after server setup (see below).
* Minor fixes

### List of tweaks

server.properties:
```
max-tick-time=-1
snooper-enabled=false
allow-flight=true
```

forge.cfg:
```
general {
    B:disableVersionCheck=true
}
version_checking {
    B:Global=false
}
```


## Example build.gradle for your project
```groovy
plugins {
    id 'com.qixalite.spongestart2' version '4.0.0'
}

spongestart {
    minecraft '1.12.2'
}

```

Note: you have to define either minecraft or the sponge specific version for the platform you plan to setup.

## SpongeStart tasks

----------------- 

cleanForgeServer - Clean Forge server folder 

cleanSpongeStartCache - Clean SpongeStart cache folder 

cleanVanillaServer - Clean Vanilla server folder 

downloadForge - Download Forge jar 

downloadSpongeForge - Download SpongeForge jar 

downloadSpongeVanilla - Download SpongeVanilla jar 

GenerateForgeRun - Generate Forge run configuration to start a SpongeForge server 

GenerateVanillaRun - Generate Vanilla run configuration to start a SpongeVanilla server 

setupForgeServer - Setup a SpongeForge server 

setupVanillaServer - Setup a SpongeVanilla server 



## Advanced

Not really _advanced_, but here's the full list of build.gradle properties.

```groovy
spongestart {
    minecraft ''            //minecraft version
    spongeForge ''          //SpongeForge version
    spongeVanilla ''        //SpongeVanilla version
    online ''               //online-mode (true/false). Default to true
    forgeServerFolder ''    //absolute path to the forge server folder. Default to run/forge
    vanillaServerFolder ''  //absolute path to the vanilla server folder. Default to run/vanilla
    cacheFolder ''          //absolute path to the SpongeStart cache folder for downloads. Default to .gradle/caches/SpongeStart
}
```
