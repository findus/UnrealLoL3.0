UnrealLoL2.0
============

So yeah, this is the Unreal Mod for League of Legends, basically a program that replaces soundfiles ... or more like replaced, Riot introduced a new Soundengine
and released it with patch 4,12. This mod does not support the new type of .wpk (WWise) archives they use now.

I made some research:

- The soundfiles are located in the .wpk archive and are stored in wave/riff format.
- The soundfiles are not compressed but headerless -> you cant play them without using special tools.
- I dont know how the .wpk header works

I dont really know if I can solve this problems alone, so if you have ideas feel free to fork this project, I will do some code refactoring and commenting 
to get at least some structure in the shitty code :P.
