# android-monitor-traits

# Monitor Trait Module - Architecture

|Arch|
|--|
|![app-arch](https://user-images.githubusercontent.com/121907/166393822-a0524c1a-12ab-4bee-bd94-8854a89be9ec.jpg)|


## Overview

The entry point is the `AndroidRuntimeMonitor`.
This component implements the Runtime interface and provide the traits available. 

## Traits 

The case document states two types of traits ,`Visibility` and `NFC`.
These two are under the implementation of `LifeOwnerCycleRuntimeTraits` and `NfcRuntimeTraits`.

I have added two more traits, one for connectivity and other that binds all traits available.

The compound trait provides a single flow but instead of Boolean as the return type, I added a custom type, `TraitState`.

Basically, it reflects the `RuntimeTratis` as object.


